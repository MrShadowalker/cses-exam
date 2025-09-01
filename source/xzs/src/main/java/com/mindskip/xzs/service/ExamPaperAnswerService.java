package com.mindskip.xzs.service;

import com.mindskip.xzs.domain.dto.exam.ExamPaperAnswerSubmitDTO;
import com.mindskip.xzs.domain.dto.request.ExamPaperReportRequest;
import com.mindskip.xzs.domain.entity.ExamPaperQuestionAnswer;
import com.mindskip.xzs.domain.viewmodel.student.exam.report.ExamPaperReportViewModel;

public interface ExamPaperAnswerService extends BaseService<ExamPaperQuestionAnswer> {

    /**
     * 提交试卷
     * @param examPaperAnswerSubmitDTO
     */
    void submit(ExamPaperAnswerSubmitDTO examPaperAnswerSubmitDTO);

    ExamPaperReportViewModel getExamPaperReport(ExamPaperReportRequest report);
}
