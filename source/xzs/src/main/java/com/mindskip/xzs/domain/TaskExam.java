package com.mindskip.xzs.domain;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class TaskExam implements Serializable {

    private static final long serialVersionUID = -7014704644631536195L;

    private Integer id;

    /**
     * 任务标题
     */
    private String title;

    /**
     * 科目
     */
    private Integer subjectId;

    /**
     * 等级
     */
    private Integer gradeLevel;

    /**
     * 任务框架 内容为JSON
     */
    private Integer frameTextContentId;

    /**
     * 创建者
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    private Boolean deleted;

    /**
     * 创建人用户名
     */
    private String createUserName;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public void setGradeLevel(Integer gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public void setFrameTextContentId(Integer frameTextContentId) {
        this.frameTextContentId = frameTextContentId;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }
}
