package com.mindskip.xzs.domain.dto.exam;

import com.mindskip.xzs.domain.dto.question.QuestionAnswerSubmitDTO;
import lombok.Data;

import java.util.List;

/**
 * @author Shadowalker
 */
@Data
public class ExamPaperAnswerSubmitDTO {

    private Integer examPaperId;

    private Integer userId;

    private List<QuestionAnswerSubmitDTO> questionAnswers;

}
