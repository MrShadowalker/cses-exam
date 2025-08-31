package com.mindskip.xzs.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 试卷题目关联表实体类
 * @author Shadowalker
 */
@Data
public class ExamPaperQuestion implements Serializable {

    private static final long serialVersionUID = 812520002696427423L;

    private Integer id;

    /**
     * 试卷ID
     */
    private Integer examPaperId;

    /**
     * 题目ID
     */
    private Integer questionId;

    /**
     * 题目排序
     */
    private Integer order;

    /**
     * 选项排序
     */
    private String optionOrder;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Integer updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

}
