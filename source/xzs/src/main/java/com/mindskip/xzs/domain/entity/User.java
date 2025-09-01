package com.mindskip.xzs.domain.entity;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class User implements Serializable {

    private static final long serialVersionUID = -7797183521247423117L;

    private Integer id;

    private String userUuid;

    /**
     * 用户名
     */
    private String userName;

    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    private Integer age;

    /**
     * 1.男 2女
     */
    private Integer sex;

    private Date birthDay;

    /**
     * 等级
     */
    private Integer userLevel;

    /**
     * 称号
     */
    private String title;

    private String phone;

    /**
     * 1.学生 2.导师 3.管理员
     */
    private Integer role;

    /**
     * 1.启用 2.禁用
     */
    private Integer status;

    /**
     * 头像地址
     */
    private String imagePath;

    private Date createTime;

    private Date modifyTime;

    private Date lastActiveTime;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 微信openId
     */
    private String wxOpenId;

    /**
     * 邀请人用户ID
     */
    private Integer inviteUserId;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid == null ? null : userUuid.trim();
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath == null ? null : imagePath.trim();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId == null ? null : wxOpenId.trim();
    }

    public void setInviteUserId(Integer inviteUserId) {
        this.inviteUserId = inviteUserId;
    }
}
