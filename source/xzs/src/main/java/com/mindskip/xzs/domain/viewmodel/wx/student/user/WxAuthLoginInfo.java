package com.mindskip.xzs.domain.viewmodel.wx.student.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 微信小程序授权登录请求参数
 */
@Data
public class WxAuthLoginInfo {

    /**
     * 微信小程序登录凭证code
     */
    @NotBlank
    private String code;

    /**
     * 微信用户昵称
     */
    private String nickName;

    /**
     * 微信用户性别 1-男 2-女 0-未知
     */
    private Integer gender;

    /**
     * 微信用户头像URL
     */
    private String avatarUrl;

    /**
     * 微信用户所在国家
     */
    private String country;

    /**
     * 微信用户所在省份
     */
    private String province;

    /**
     * 微信用户所在城市
     */
    private String city;

    /**
     * 分享者用户ID（通过分享链接进入时传入）
     */
    private Integer shareUserId;

    /**
     * 分享场景（如：群聊、好友等）
     */
    private String shareScene;
}
