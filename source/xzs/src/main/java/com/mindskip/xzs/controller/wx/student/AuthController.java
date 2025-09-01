package com.mindskip.xzs.controller.wx.student;

import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.configuration.property.SystemConfig;
import com.mindskip.xzs.controller.wx.BaseWXApiController;
import com.mindskip.xzs.domain.entity.UserToken;
import com.mindskip.xzs.domain.enums.UserStatusEnum;
import com.mindskip.xzs.service.AuthenticationService;
import com.mindskip.xzs.service.UserService;
import com.mindskip.xzs.service.UserTokenService;
import com.mindskip.xzs.utility.WxUtil;
import com.mindskip.xzs.utility.WxResponse;
import com.mindskip.xzs.domain.viewmodel.wx.student.user.BindInfo;
import com.mindskip.xzs.domain.viewmodel.wx.student.user.WxAuthLoginInfo;
import com.mindskip.xzs.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@Controller("WXStudentAuthController")
@RequestMapping(value = "/api/wx/student/auth")
@ResponseBody
public class AuthController extends BaseWXApiController {

    private final SystemConfig systemConfig;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final UserTokenService userTokenService;

    @Autowired
    public AuthController(SystemConfig systemConfig, AuthenticationService authenticationService, UserService userService, UserTokenService userTokenService) {
        this.systemConfig = systemConfig;
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.userTokenService = userTokenService;
    }

    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public RestResponse bind(@Valid BindInfo model) {
        User user = userService.getUserByUserName(model.getUserName());
        if (user == null) {
            return RestResponse.fail(2, "用户名或密码错误");
        }
        boolean result = authenticationService.authUser(user, model.getUserName(), model.getPassword());
        if (!result) {
            return RestResponse.fail(2, "用户名或密码错误");
        }
        UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(user.getStatus());
        if (UserStatusEnum.Disable == userStatusEnum) {
            return RestResponse.fail(3, "用户被禁用");
        }
        String code = model.getCode();
        String openid = WxUtil.getOpenId(systemConfig.getWx().getAppid(), systemConfig.getWx().getSecret(), code);
        if (null == openid) {
            return RestResponse.fail(4, "获取微信OpenId失败");
        }
        user.setWxOpenId(openid);
        UserToken userToken = userTokenService.bind(user);
        return RestResponse.ok(userToken.getToken());
    }


    @RequestMapping(value = "/checkBind", method = RequestMethod.POST)
    public RestResponse checkBind(@Valid @NotBlank String code) {
        String openid = WxUtil.getOpenId(systemConfig.getWx().getAppid(), systemConfig.getWx().getSecret(), code);
        if (null == openid) {
            return RestResponse.fail(3, "获取微信OpenId失败");
        }
        UserToken userToken = userTokenService.checkBind(openid);
        if (null != userToken) {
            return RestResponse.ok(userToken.getToken());
        }
        return RestResponse.fail(2, "用户未绑定");
    }


    @RequestMapping(value = "/unBind", method = RequestMethod.POST)
    public RestResponse unBind() {
        UserToken userToken = getUserToken();
        userTokenService.unBind(userToken);
        return RestResponse.ok();
    }

    /**
     * 微信小程序授权登录接口
     * 如果用户未注册则自动注册，如果已注册则直接登录
     *
     * @param model 微信授权登录信息
     * @return 登录结果，成功返回token
     */
    @RequestMapping(value = "/wxLogin", method = RequestMethod.POST)
    public RestResponse wxLogin(@Valid @RequestBody WxAuthLoginInfo model) {
        // 1. 通过code获取微信授权信息
        WxResponse wxResponse = WxUtil.getWxAuthInfo(systemConfig.getWx().getAppid(), systemConfig.getWx().getSecret(), model.getCode());
        if (wxResponse == null || wxResponse.getOpenid() == null) {
            return RestResponse.fail(4, "获取微信OpenId失败");
        }

        String openid = wxResponse.getOpenid();

        // 2. 检查用户是否已存在
        User existingUser = userService.selectByWxOpenId(openid);
        
        User user;
        if (existingUser != null) {
            // 用户已存在，检查用户状态
            UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(existingUser.getStatus());
            if (UserStatusEnum.Disable == userStatusEnum) {
                return RestResponse.fail(3, "用户被禁用");
            }
            
            // 更新用户信息（如果提供了新的信息）
            if (shouldUpdateUserInfo(existingUser, model)) {
                updateUserFromWxInfo(existingUser, model);
                userService.updateUser(existingUser);
            }
            
            user = existingUser;
        } else {
            // 用户不存在，自动注册
            user = createUserFromWxInfo(openid, model);
            userService.insertUser(user);
        }

        // 3. 生成token并返回
        UserToken userToken = userTokenService.bind(user);
        return RestResponse.ok(userToken.getToken());
    }

    /**
     * 判断是否需要更新用户信息
     */
    private boolean shouldUpdateUserInfo(User existingUser, WxAuthLoginInfo model) {
        // 如果昵称、性别或头像有变化，则需要更新
        if (model.getNickName() != null && !model.getNickName().equals(existingUser.getRealName())) {
            return true;
        }
        if (model.getGender() != null && !model.getGender().equals(existingUser.getSex())) {
            return true;
        }
        if (model.getAvatarUrl() != null && !model.getAvatarUrl().equals(existingUser.getImagePath())) {
            return true;
        }
        return false;
    }

    /**
     * 从微信信息更新用户
     */
    private void updateUserFromWxInfo(User user, WxAuthLoginInfo model) {
        if (model.getNickName() != null && !model.getNickName().trim().isEmpty()) {
            user.setRealName(model.getNickName().trim());
        }
        if (model.getGender() != null) {
            user.setSex(model.getGender());
        }
        if (model.getAvatarUrl() != null && !model.getAvatarUrl().trim().isEmpty()) {
            user.setImagePath(model.getAvatarUrl().trim());
        }
        user.setModifyTime(new java.util.Date());
        user.setLastActiveTime(new java.util.Date());
    }

    /**
     * 从微信信息创建新用户
     */
    private User createUserFromWxInfo(String openid, WxAuthLoginInfo model) {
        User user = new User();
        
        // 基础信息
        user.setWxOpenId(openid);
        user.setUserName("wx_" + openid.substring(openid.length() - 8)); // 使用openid后8位作为用户名
        user.setPassword(""); // 微信用户无需密码
        
        // 微信用户信息
        if (model.getNickName() != null && !model.getNickName().trim().isEmpty()) {
            user.setRealName(model.getNickName().trim());
        } else {
            user.setRealName("微信用户");
        }
        
        if (model.getGender() != null) {
            user.setSex(model.getGender());
        } else {
            user.setSex(0); // 未知
        }
        
        if (model.getAvatarUrl() != null && !model.getAvatarUrl().trim().isEmpty()) {
            user.setImagePath(model.getAvatarUrl().trim());
        }
        
        // 默认设置
        user.setRole(1); // 1.学生
        user.setStatus(1); // 1.启用
        user.setUserLevel(1); // 默认等级
        user.setDeleted(false);
        
        // 时间信息
        java.util.Date now = new java.util.Date();
        user.setCreateTime(now);
        user.setModifyTime(now);
        user.setLastActiveTime(now);
        
        return user;
    }
}
