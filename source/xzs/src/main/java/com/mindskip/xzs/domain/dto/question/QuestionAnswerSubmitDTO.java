package com.mindskip.xzs.domain.dto.question;

import lombok.Data;

import java.util.List;

/**
 * @author Shadowalker
 */
@Data
public class QuestionAnswerSubmitDTO {

    private Integer questionId;

    private List<Integer> selectedOptionIds;

    private Integer normalOptionId;

}
