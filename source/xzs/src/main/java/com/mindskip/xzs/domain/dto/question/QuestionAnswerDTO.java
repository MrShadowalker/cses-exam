package com.mindskip.xzs.domain.dto.question;

import com.mindskip.xzs.domain.dto.exam.ExamPaperDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Shadowalker
 */
@Data
public class QuestionAnswerDTO {

    private ExamPaperDTO examPaper;

    private QuestionDTO question;

    private List<QuestionOptionDTO> selectedOptions;

    private QuestionOptionDTO normalOption;

    private Integer maxScore;

    private Integer normalScore;
}
