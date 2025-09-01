package com.mindskip.xzs.controller.wx.student;

import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.controller.wx.BaseWXApiController;
import com.mindskip.xzs.domain.entity.User;
import com.mindskip.xzs.domain.entity.UserShareRelation;
import com.mindskip.xzs.service.UserShareRelationService;
import com.mindskip.xzs.viewmodel.wx.share.ShareStatisticsResponseVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("WXStudentShareController")
@RequestMapping(value = "/api/wx/student/share")
@ResponseBody
public class ShareController extends BaseWXApiController {

    private final UserShareRelationService userShareRelationService;

    @Autowired
    public ShareController(UserShareRelationService userShareRelationService) {
        this.userShareRelationService = userShareRelationService;
    }

    /**
     * 获取用户分享统计信息
     *
     * @return 分享统计信息
     */
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public RestResponse<ShareStatisticsResponseVM> getShareStatistics() {
        User user = getCurrentUser();
        if (user == null) {
            return RestResponse.fail(2, "用户未登录");
        }

        ShareStatisticsResponseVM statistics = new ShareStatisticsResponseVM();
        statistics.setTotalShares(userShareRelationService.countSharesByUserId(user.getId()));
        statistics.setNewUserShares(userShareRelationService.countNewUserSharesByUserId(user.getId()));
        statistics.setOldUserShares(userShareRelationService.countOldUserSharesByUserId(user.getId()));

        return RestResponse.ok(statistics);
    }

    /**
     * 获取用户的分享记录列表
     *
     * @return 分享记录列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RestResponse<List<UserShareRelation>> getShareList() {
        User user = getCurrentUser();
        if (user == null) {
            return RestResponse.fail(2, "用户未登录");
        }

        List<UserShareRelation> shareList = userShareRelationService.getShareRelationsByShareUserId(user.getId());
        return RestResponse.ok(shareList);
    }

    /**
     * 获取分享者的分享记录详情
     *
     * @param shareId 分享记录ID
     * @return 分享记录详情
     */
    @RequestMapping(value = "/detail/{shareId}", method = RequestMethod.GET)
    public RestResponse<UserShareRelation> getShareDetail(@PathVariable Integer shareId) {
        User user = getCurrentUser();
        if (user == null) {
            return RestResponse.fail(2, "用户未登录");
        }

        UserShareRelation shareRelation = userShareRelationService.getShareRelationBySharedUserId(shareId);
        if (shareRelation == null || !shareRelation.getShareUserId().equals(user.getId())) {
            return RestResponse.fail(3, "分享记录不存在或无权访问");
        }

        return RestResponse.ok(shareRelation);
    }
}
