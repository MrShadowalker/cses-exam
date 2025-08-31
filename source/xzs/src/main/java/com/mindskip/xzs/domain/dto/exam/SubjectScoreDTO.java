package com.mindskip.xzs.domain.dto.exam;

import com.mindskip.xzs.domain.dto.question.QuestionAnswerDTO;
import com.mindskip.xzs.domain.enums.SubjectEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 环节分数
 * @author Shadowalker
 */
@Data
public class SubjectScoreDTO {

    private SubjectEnum subject;

    private List<QuestionAnswerDTO> questionAnswers;

    private BigDecimal originMaxSubjectScore;

    private Integer maxSubjectScore;

    private BigDecimal originNormalSubjectScore;

    private Integer normalSubjectScore;

}
