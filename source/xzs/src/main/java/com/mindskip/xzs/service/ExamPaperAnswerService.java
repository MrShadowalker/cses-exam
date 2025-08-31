package com.mindskip.xzs.service;

import com.mindskip.xzs.domain.dto.exam.ExamPaperAnswerSubmitDTO;
import com.mindskip.xzs.domain.entity.ExamPaperQuestionAnswer;

public interface ExamPaperAnswerService extends BaseService<ExamPaperQuestionAnswer> {

    /**
     * 提交试卷
     * @param examPaperAnswerSubmitDTO
     */
    void submit(ExamPaperAnswerSubmitDTO examPaperAnswerSubmitDTO);

}
