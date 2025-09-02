package com.mindskip.xzs.domain.dto.exam;

import com.mindskip.xzs.domain.dto.question.QuestionAnswerDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Shadowalker
 */
@Data
public class ExamPaperScoreDTO {

    private ExamPaperDTO examPaper;

    private List<QuestionAnswerDTO> questionAnswers;

    private Map<Integer, BigDecimal> originMaxSubjectScoreMap;

    /**
     * 各环节上限分数
     *
     */
    private Map<Integer, Integer> maxSubjectScoreMap;

    private Map<Integer, BigDecimal> originNormalSubjectScoreMap;

    /**
     * 各环节常态分数
     */
    private Map<Integer, Integer> normalSubjectScoreMap;

    private String originMaxScoreCombination;

    /**
     * 上限分数组合
     */
    private String maxScoreCombination;

    /**
     * 上限排名
     */
    private Integer maxRank;

    /**
     * 常态原始分数组合
     */
    private String originNormalScoreCombination;

    /**
     * 常态分数组合
     */
    private String normalScoreCombination;

    /**
     * 常态排名
     */
    private Integer normalRank;
}
