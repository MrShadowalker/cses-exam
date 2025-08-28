package com.mindskip.xzs.domain;

import com.mindskip.xzs.domain.enums.QuestionTypeEnum;
import com.mindskip.xzs.domain.enums.SceneEnum;
import com.mindskip.xzs.domain.enums.SubjectEnum;
import com.mindskip.xzs.domain.enums.TargetTypeEnum;
import com.mindskip.xzs.utility.ExamUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Question implements Serializable {

    private static final long serialVersionUID = 8826266720383164363L;

    private Integer id;

    /**
     * 	1.单选题 2.多选题 3.判断题 4.填空题 5.简答题 6.复合选择题
     */
    private Integer questionType;

    /**
     * 服务对象
     * @see TargetTypeEnum#getCode()
     */
    private String questionTargetType;

    /**
     * 场景
     * @see SceneEnum#getCode()
     */
    private String questionScene;

    /**
     * 科目，即对应的环节
     * @see SubjectEnum#getCode()
     */
    private Integer subjectId;

    /**
     * 题目算分权重
     */
    private BigDecimal weight;

    /**
     * 级别
     * 复合选择题中没有等级这个属性
     */
    private Integer gradeLevel;

    /**
     * 题目难度
     */
    private Integer difficult;

    /**
     * 正确答案
     */
    private String correct;

    /**
     * 题目分数
     */
    private BigDecimal score;

    /**
     * 题目 填空、 题干、解析、答案等信息
     */
    private Integer infoTextContentId;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    private Boolean deleted;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public void setQuestionTargetType(String questionTargetType) {
        this.questionTargetType = questionTargetType;
    }

    public void setQuestionScene(String questionScene) {
        this.questionScene = questionScene;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public void setGradeLevel(Integer gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public void setDifficult(Integer difficult) {
        this.difficult = difficult;
    }

    public void setCorrect(String correct) {
        this.correct = correct == null ? null : correct.trim();
    }

    public void setInfoTextContentId(Integer infoTextContentId) {
        this.infoTextContentId = infoTextContentId;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }


    public void setCorrectFromVM(String correct, List<String> correctArray) {
        int qType = this.getQuestionType();
        if (qType == QuestionTypeEnum.MultipleChoice.getCode()) {
            String correctJoin = ExamUtil.contentToString(correctArray);
            this.setCorrect(correctJoin);
        } else {
            this.setCorrect(correct);
        }
    }
}
