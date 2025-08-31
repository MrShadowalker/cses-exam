package com.mindskip.xzs.domain.viewmodel.student.exam.answer;

import lombok.Data;

import java.util.List;

/**
 * 题目答案提交
 */
@Data
public class QuestionAnswerSubmitViewModel {

    private Integer questionId;

    private List<Integer> selectedOptionIds;

    private Integer normalOptionId;

}
