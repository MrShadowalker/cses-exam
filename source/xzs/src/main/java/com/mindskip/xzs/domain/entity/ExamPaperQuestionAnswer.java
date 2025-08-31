package com.mindskip.xzs.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户试卷题目答案&得分
 * @author Shadowalker
 */
@Data
public class ExamPaperQuestionAnswer implements Serializable {

    private static final long serialVersionUID = -1194880236672182976L;

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
     * 选中的选项ID，逗号分隔
     */
    private String selectedOptionIds;

    /**
     * 常态选项ID
     */
    private Integer normalOptionId;

    /**
     * 上限得分
     */
    private Integer maxScore;

    /**
     * 常态得分
     */
    private Integer normalScore;


    private Date createTime;

}
