package com.mindskip.xzs.domain;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ExamPaperAnswerInfo {
    public ExamPaper examPaper;
    public ExamPaperAnswer examPaperAnswer;
    public List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers;

}
