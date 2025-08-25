package com.mindskip.xzs.domain;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class UserToken implements Serializable {

    private static final long serialVersionUID = -2414443061696200360L;

    private Integer id;

    /**
     * 用户token
     */
    private String token;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 微信小程序openId
     */
    private String wxOpenId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 用户名
     */
    private String userName;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId == null ? null : wxOpenId.trim();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
}
