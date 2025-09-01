package com.mindskip.xzs.domain.viewmodel.student.exam.report;

import com.mindskip.xzs.domain.viewmodel.student.exam.answer.QuestionAnswerSubmitViewModel;
import lombok.Data;

import java.util.List;

/**
 * 试卷答案提交
 */
@Data
public class ExamPaperReportViewModel {

    private Integer examPaperId;

    private Integer userId;

    private List<QuestionAnswerSubmitViewModel> questionAnswers;

}
