package com.mindskip.xzs.domain.viewmodel.student.exam.answer;

import lombok.Data;

import java.util.List;

/**
 * 试卷答案提交
 */
@Data
public class ExamPaperAnswerSubmitViewModel {

    private Integer examPaperId;

    private Integer userId;

    private List<QuestionAnswerSubmitViewModel> questionAnswers;

}
