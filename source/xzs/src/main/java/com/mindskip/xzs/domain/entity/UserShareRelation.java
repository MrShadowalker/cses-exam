package com.mindskip.xzs.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户分享关系实体类
 */
@Data
public class UserShareRelation implements Serializable {

    private static final long serialVersionUID = -1234567890123456789L;

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 分享者用户ID
     */
    private Integer shareUserId;

    /**
     * 被分享者用户ID
     */
    private Integer sharedUserId;

    /**
     * 被分享者是否新用户 (new_user: 新用户, old_user: 老用户)
     */
    private String sharedUserType;

    /**
     * 分享场景
     */
    private String shareScene;

    /**
     * 创建时间
     */
    private Date createTime;

    public void setShareUserId(Integer shareUserId) {
        this.shareUserId = shareUserId;
    }

    public void setSharedUserId(Integer sharedUserId) {
        this.sharedUserId = sharedUserId;
    }

    public void setSharedUserType(String sharedUserType) {
        this.sharedUserType = sharedUserType == null ? null : sharedUserType.trim();
    }

    public void setShareScene(String shareScene) {
        this.shareScene = shareScene == null ? null : shareScene.trim();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
