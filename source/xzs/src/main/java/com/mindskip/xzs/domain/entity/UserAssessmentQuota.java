package com.mindskip.xzs.domain.entity;

import com.mindskip.xzs.domain.enums.VersionEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户测评次数实体类
 */
@Data
public class UserAssessmentQuota implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 测评类型
     * @see VersionEnum#getCode()
     */
    private String version;

    /**
     * 可用次数
     */
    private Integer availableCount;

    /**
     * 已使用次数
     */
    private Integer usedCount;

    /**
     * 总发放次数
     */
    private Integer totalCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Boolean deleted;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setAvailableCount(Integer availableCount) {
        this.availableCount = availableCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}


