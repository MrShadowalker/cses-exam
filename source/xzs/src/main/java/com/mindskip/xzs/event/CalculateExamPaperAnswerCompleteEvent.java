package com.mindskip.xzs.event;

import com.mindskip.xzs.domain.entity.ExamPaperQuestionAnswer;
import org.springframework.context.ApplicationEvent;

/**
 * @version 3.3.0
 * @description: The type Calculate exam paper answer complete event.
 * Copyright (C), 2020-2025, 武汉思维跳跃科技有限公司
 * @date 2021/5/25 10:45
 */
public class CalculateExamPaperAnswerCompleteEvent extends ApplicationEvent {


    private final ExamPaperQuestionAnswer examPaperQuestionAnswer;


    /**
     * Instantiates a new Calculate exam paper answer complete event.
     *
     * @param examPaperQuestionAnswer the exam paper answer info
     */
    public CalculateExamPaperAnswerCompleteEvent(final ExamPaperQuestionAnswer examPaperQuestionAnswer) {
        super(examPaperQuestionAnswer);
        this.examPaperQuestionAnswer = examPaperQuestionAnswer;
    }

    /**
     * Gets exam paper answer info.
     *
     * @return the exam paper answer info
     */
    public ExamPaperQuestionAnswer getExamPaperAnswerInfo() {
        return examPaperQuestionAnswer;
    }

}
