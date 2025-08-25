package com.mindskip.xzs.domain;

import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
public class ExamPaperQuestionCustomerAnswer implements Serializable {

    private static final long serialVersionUID = 3389482731220342366L;

    private Integer id;

    /**
     * 题目Id
     */
    private Integer questionId;

    /**
     * 试卷Id
     */
    private Integer examPaperId;

    /**
     * 答案Id
     */
    private Integer examPaperAnswerId;

    /**
     * 题型
     */
    private Integer questionType;

    /**
     * 学科
     */
    private Integer subjectId;

    /**
     * 得分
     */
    private BigDecimal customerScore;

    /**
     * 题目原始分数
     */
    private BigDecimal questionScore;

    /**
     * 问题内容
     */
    private Integer questionTextContentId;

    /**
     * 做题答案
     */
    private String answer;

    /**
     * 做题内容
     */
    private Integer textContentId;

    /**
     * 是否正确
     */
    private Boolean doRight;

    /**
     * 做题人
     */
    private Integer createUser;

    private Date createTime;

    private Integer itemOrder;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public void setExamPaperId(Integer examPaperId) {
        this.examPaperId = examPaperId;
    }

    public void setExamPaperAnswerId(Integer examPaperAnswerId) {
        this.examPaperAnswerId = examPaperAnswerId;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public void setCustomerScore(BigDecimal customerScore) {
        this.customerScore = customerScore;
    }

    public void setQuestionScore(BigDecimal questionScore) {
        this.questionScore = questionScore;
    }

    public void setQuestionTextContentId(Integer questionTextContentId) {
        this.questionTextContentId = questionTextContentId;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public void setTextContentId(Integer textContentId) {
        this.textContentId = textContentId;
    }

    public void setDoRight(Boolean doRight) {
        this.doRight = doRight;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }
}
