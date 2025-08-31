package com.mindskip.xzs.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 试题选项实体类
 * @author Shadowalker
 */
@Data
public class QuestionOption implements Serializable {

    private static final long serialVersionUID = -693729786501020076L;

    private Integer id;

    /**
     * 题目id
     * @see Question#getId()
     */
    private Integer questionId;

    /**
     * 选项等级
     */
    private Integer level;

    /**
     * 选项得分
     */
    private Integer score;

    /**
     * 选项排序
     */
    private Integer order;

    /**
     * 选项内容
     */
    private String content;

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

    /**
     * 删除状态
     */
    private Byte deleted;

}
