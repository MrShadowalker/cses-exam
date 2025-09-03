package com.mindskip.xzs.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户测评记录实体类
 */
@Data
public class UserAssessment implements Serializable {

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
     * 测评类型 (1:体验版测评 2:标准版测评 3:目标能力测评 4:深度咨询)
     */
    private Integer assessmentType;

    /**
     * 测评状态 (1:已发放 2:进行中 3:已完成 4:已过期)
     */
    private Integer status;

    /**
     * 发放时间
     */
    private Date grantTime;

    /**
     * 开始测评时间
     */
    private Date startTime;

    /**
     * 完成测评时间
     */
    private Date completeTime;

    /**
     * 测评结果数据 (JSON格式存储)
     */
    private String resultData;

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

    public void setAssessmentType(Integer assessmentType) {
        this.assessmentType = assessmentType;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setGrantTime(Date grantTime) {
        this.grantTime = grantTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData == null ? null : resultData.trim();
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


