package com.mindskip.xzs.service.impl;

import com.mindskip.xzs.domain.converter.ExamPaperConverter;
import com.mindskip.xzs.domain.converter.QuestionConverter;
import com.mindskip.xzs.domain.dto.exam.ExamPaperAnswerSubmitDTO;
import com.mindskip.xzs.domain.dto.exam.ExamPaperDTO;
import com.mindskip.xzs.domain.dto.exam.ExamPaperScoreDTO;
import com.mindskip.xzs.domain.dto.exam.SubjectScoreDTO;
import com.mindskip.xzs.domain.dto.question.QuestionAnswerDTO;
import com.mindskip.xzs.domain.dto.question.QuestionAnswerSubmitDTO;
import com.mindskip.xzs.domain.dto.question.QuestionDTO;
import com.mindskip.xzs.domain.dto.report.ExamPaperReportDTO;
import com.mindskip.xzs.domain.entity.ExamPaper;
import com.mindskip.xzs.domain.entity.ExamPaperQuestionAnswer;
import com.mindskip.xzs.domain.entity.QuestionOption;
import com.mindskip.xzs.repository.*;
import com.mindskip.xzs.service.ExamPaperAnswerService;
import com.mindskip.xzs.service.TextContentService;
import com.mindskip.xzs.utility.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExamPaperAnswerServiceImpl extends BaseServiceImpl<ExamPaperQuestionAnswer> implements ExamPaperAnswerService {

    private final ExamPaperMapper examPaperMapper;
    private final QuestionMapper questionMapper;
    private final QuestionOptionMapper questionOptionMapper;
    private final ExamPaperQuestionAnswerMapper examPaperQuestionAnswerMapper;
    private final RankMapper rankMapper;
    private final TextContentService textContentService;

    @Autowired
    public ExamPaperAnswerServiceImpl(ExamPaperQuestionAnswerMapper examPaperQuestionAnswerMapper, QuestionOptionMapper questionOptionMapper, ExamPaperMapper examPaperMapper, RankMapper rankMapper, TextContentService textContentService, QuestionMapper questionMapper) {
        super(examPaperQuestionAnswerMapper);
        this.examPaperQuestionAnswerMapper = examPaperQuestionAnswerMapper;
        this.questionOptionMapper = questionOptionMapper;
        this.examPaperMapper = examPaperMapper;
        this.rankMapper = rankMapper;
        this.textContentService = textContentService;
        this.questionMapper = questionMapper;
    }

    @Override
    @Transactional
    public void submit(ExamPaperAnswerSubmitDTO examPaperAnswerSubmitDTO) {
        // 1. 保存试卷试题答案记录
        saveExamPaperAnswer(examPaperAnswerSubmitDTO);

        // 2. 判分
        ExamPaperScoreDTO examPaperScoreDTO = judge(examPaperAnswerSubmitDTO);

        // 3. 保存判分信息
        saveExamPaperScore(examPaperScoreDTO);

        // 4. 生成考试报告
        ExamPaperReportDTO examPaperReportDTO = generateExamPaperReport(examPaperScoreDTO);


    }

    private void saveExamPaperAnswer(ExamPaperAnswerSubmitDTO dto) {
        for (QuestionAnswerSubmitDTO submit : dto.getQuestionAnswers()) {
            ExamPaperQuestionAnswer answer = new ExamPaperQuestionAnswer();
            answer.setExamPaperId(dto.getExamPaperId());
            answer.setQuestionId(submit.getQuestionId());
            answer.setCreateTime(new Date());
            answer.setSelectedOptionIds(submit.getSelectedOptionIds().toString());
            answer.setNormalOptionId(submit.getNormalOptionId());
            // 获取选项信息，拿到对应选项的分数
            List<QuestionOption> option = questionOptionMapper.selectByIds(submit.getSelectedOptionIds());
            Map<Integer, Integer> optionScoreMap = option.stream()
                    .collect(Collectors.toMap(QuestionOption::getId, QuestionOption::getScore));
            // 取optionScoreMap中value的最大值
            answer.setMaxScore(optionScoreMap.values().stream().max(Integer::compare).orElse(0));
            answer.setNormalScore(optionScoreMap.get(submit.getNormalOptionId()));
            examPaperQuestionAnswerMapper.insert(answer);
        }
    }

    public ExamPaperScoreDTO judge(ExamPaperAnswerSubmitDTO submitDTO) {
        int examPaperId = submitDTO.getExamPaperId();
        List<ExamPaperQuestionAnswer> questionAnswers = examPaperQuestionAnswerMapper.selectByPaperId(examPaperId);
        Map<Integer, QuestionAnswerDTO> questionAnswerMap = questionAnswers.stream()
                .collect(Collectors.toMap(ExamPaperQuestionAnswer::getQuestionId, q -> {
                    QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO();
                    questionAnswerDTO.setExamPaper(ExamPaperConverter.entityToDtoWithoutQuestions(examPaperMapper.selectByPrimaryKey(examPaperId)));
                    questionAnswerDTO.setQuestion(QuestionConverter.entityToDtoWithoutOptions(questionMapper.selectByPrimaryKey(q.getQuestionId())));
                    List<Integer> selectedOptionIds = CollectionUtil.stringToIntList(q.getSelectedOptionIds());
                    questionAnswerDTO.setSelectedOptions(QuestionConverter.batchEntityToOptionDto(questionOptionMapper.selectByIds(selectedOptionIds)));
                    questionAnswerDTO.setNormalOption(QuestionConverter.entityToOptionDto(questionOptionMapper.selectByPrimaryKey(q.getNormalOptionId())));
                    questionAnswerDTO.setMaxScore(q.getMaxScore());
                    questionAnswerDTO.setNormalScore(q.getNormalScore());
                    return questionAnswerDTO;
                }));

        // 分别获取每个环节的题目对应的分数信息
        List<QuestionAnswerDTO> informationQuestions = questionAnswerMap.values().stream()
                .filter(q -> q.getQuestion().getSubject().getCode() == 1)
                .collect(Collectors.toList());

        List<QuestionAnswerDTO> analysisQuestions = questionAnswerMap.values().stream()
                .filter(q -> q.getQuestion().getSubject().getCode() == 2)
                .collect(Collectors.toList());

        List<QuestionAnswerDTO> decisionQuestions = questionAnswerMap.values().stream()
                .filter(q -> q.getQuestion().getSubject().getCode() == 3)
                .collect(Collectors.toList());

        List<QuestionAnswerDTO> actionQuestions = questionAnswerMap.values().stream()
                .filter(q -> q.getQuestion().getSubject().getCode() == 4)
                .collect(Collectors.toList());

        List<QuestionAnswerDTO> reviewQuestions = questionAnswerMap.values().stream()
                .filter(q -> q.getQuestion().getSubject().getCode() == 5)
                .collect(Collectors.toList());

        // 计算每个环节的分数
        SubjectScoreDTO informationScore = calculateScore(informationQuestions);
        SubjectScoreDTO analysisScore = calculateScore(analysisQuestions);
        SubjectScoreDTO decisionScore = calculateScore(decisionQuestions);
        SubjectScoreDTO actionScore = calculateScore(actionQuestions);
        SubjectScoreDTO reviewScore = calculateScore(reviewQuestions);

        // 组装试卷计分
        ExamPaperScoreDTO examPaperScoreDTO = new ExamPaperScoreDTO();
        ExamPaperDTO examPaper = ExamPaperConverter.entityToDtoWithoutQuestions(examPaperMapper.selectByPrimaryKey(examPaperId));
        examPaperScoreDTO.setExamPaper(examPaper);
        examPaperScoreDTO.setQuestionAnswers(new ArrayList<>(questionAnswerMap.values()));
        Map<Integer, BigDecimal> originMaxSubjectScoreMap = new HashMap<>();
        originMaxSubjectScoreMap.put(1, informationScore.getOriginMaxSubjectScore());
        originMaxSubjectScoreMap.put(2, analysisScore.getOriginMaxSubjectScore());
        originMaxSubjectScoreMap.put(3, decisionScore.getOriginMaxSubjectScore());
        originMaxSubjectScoreMap.put(4, actionScore.getOriginMaxSubjectScore());
        originMaxSubjectScoreMap.put(5, reviewScore.getOriginMaxSubjectScore());
        Map<Integer, Integer> maxSubjectScoreMap = new HashMap<>();
        maxSubjectScoreMap.put(1, informationScore.getMaxSubjectScore());
        maxSubjectScoreMap.put(2, analysisScore.getMaxSubjectScore());
        maxSubjectScoreMap.put(3, decisionScore.getMaxSubjectScore());
        maxSubjectScoreMap.put(4, actionScore.getMaxSubjectScore());
        maxSubjectScoreMap.put(5, reviewScore.getMaxSubjectScore());

        Map<Integer, BigDecimal> originNormalSubjectScoreMap = new HashMap<>();
        originNormalSubjectScoreMap.put(1, informationScore.getOriginNormalSubjectScore());
        originNormalSubjectScoreMap.put(2, analysisScore.getOriginNormalSubjectScore());
        originNormalSubjectScoreMap.put(3, decisionScore.getOriginNormalSubjectScore());
        originNormalSubjectScoreMap.put(4, actionScore.getOriginNormalSubjectScore());
        originNormalSubjectScoreMap.put(5, reviewScore.getOriginNormalSubjectScore());
        Map<Integer, Integer> normalSubjectScoreMap = new HashMap<>();
        normalSubjectScoreMap.put(1, informationScore.getNormalSubjectScore());
        normalSubjectScoreMap.put(2, analysisScore.getNormalSubjectScore());
        normalSubjectScoreMap.put(3, decisionScore.getNormalSubjectScore());
        normalSubjectScoreMap.put(4, actionScore.getNormalSubjectScore());
        normalSubjectScoreMap.put(5, reviewScore.getNormalSubjectScore());

        examPaperScoreDTO.setOriginMaxSubjectScoreMap(originMaxSubjectScoreMap);
        examPaperScoreDTO.setMaxSubjectScoreMap(maxSubjectScoreMap);
        examPaperScoreDTO.setOriginNormalSubjectScoreMap(originNormalSubjectScoreMap);
        examPaperScoreDTO.setNormalSubjectScoreMap(normalSubjectScoreMap);

        String originMaxScoreCombination = informationScore.getOriginMaxSubjectScore() + "," +
                analysisScore.getOriginMaxSubjectScore() + "," +
                decisionScore.getOriginMaxSubjectScore() + "," +
                actionScore.getOriginMaxSubjectScore() + "," +
                reviewScore.getOriginMaxSubjectScore();
        examPaperScoreDTO.setOriginMaxScoreCombination(originMaxScoreCombination);
        String maxScoreCombinationBuilder = informationScore.getMaxSubjectScore() + "," +
                analysisScore.getMaxSubjectScore() + "," +
                decisionScore.getMaxSubjectScore() + "," +
                actionScore.getMaxSubjectScore() + "," +
                reviewScore.getMaxSubjectScore();
        examPaperScoreDTO.setMaxScoreCombination(maxScoreCombinationBuilder);
        String maxScoreCombinationHash = String.valueOf(informationScore.getMaxSubjectScore()) +
                analysisScore.getMaxSubjectScore() +
                decisionScore.getMaxSubjectScore() +
                actionScore.getMaxSubjectScore() +
                reviewScore.getMaxSubjectScore();

        String originNormalScoreCombination = informationScore.getOriginNormalSubjectScore() + "," +
                analysisScore.getOriginNormalSubjectScore() + "," +
                decisionScore.getOriginNormalSubjectScore() + "," +
                actionScore.getOriginNormalSubjectScore() + "," +
                reviewScore.getOriginNormalSubjectScore();
        examPaperScoreDTO.setOriginNormalScoreCombination(originNormalScoreCombination);
        String normalScoreCombination = informationScore.getNormalSubjectScore() + "," +
                analysisScore.getNormalSubjectScore() + "," +
                decisionScore.getNormalSubjectScore() + "," +
                actionScore.getNormalSubjectScore() + "," +
                reviewScore.getNormalSubjectScore();
        examPaperScoreDTO.setNormalScoreCombination(normalScoreCombination);
        String normalScoreCombinationHash = String.valueOf(informationScore.getMaxSubjectScore()) +
                analysisScore.getMaxSubjectScore() +
                decisionScore.getMaxSubjectScore() +
                actionScore.getMaxSubjectScore() +
                reviewScore.getMaxSubjectScore();

        examPaperScoreDTO.setMaxRank(rankMapper.selectByCombinationHash(maxScoreCombinationHash).getRank());
        examPaperScoreDTO.setNormalRank(rankMapper.selectByCombinationHash(normalScoreCombinationHash).getRank());

        return examPaperScoreDTO;
    }

    /**
     * 数组每一行：	题ID、答案数组、单选ID
     * 将数据存储到该测试人员的题组里，就是客户多选了哪个答案，单选了哪个答案。
     * 根据 题ID取值	每题normal_score=单选对应的分值
     * 每题max_score=max（多选的答案对应的分值）空值不统计。
     * 每题weight=权重
     * INFORMATION_weight=sum(weight+……)
     * INFORMATION_max_subject=sum(max_score*weight+……)/INFORMATION_weight
     * INFORMATION_normal_subject=sum(normal_score*weight+……)/INFORMATION_weight
     *
     * @param questions
     * @return
     */
    private SubjectScoreDTO calculateScore(List<QuestionAnswerDTO> questions) {
        SubjectScoreDTO subjectScoreDTO = new SubjectScoreDTO();

        BigDecimal sumWeight = questions.stream()
                .map(QuestionAnswerDTO::getQuestion)
                .map(QuestionDTO::getWeight)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal sumMaxScore = BigDecimal.ZERO;
        BigDecimal sumNormalScore = BigDecimal.ZERO;

        for (QuestionAnswerDTO answer : questions) {

            BigDecimal weight = answer.getQuestion().getWeight();

            // 计算环节上线总分
            int maxScore = answer.getMaxScore();
            sumMaxScore = sumMaxScore.add(BigDecimal.valueOf(maxScore).multiply(weight));

            // 计算环节常态总分
            int normalScore = answer.getNormalScore();
            sumNormalScore = sumNormalScore.add(BigDecimal.valueOf(normalScore).multiply(weight));
        }

        // 计算加权平均数，保留2位小数，不要四舍五入
        BigDecimal originSubjectMaxScore = sumMaxScore.divide(sumWeight, 2, RoundingMode.DOWN);
        int subjectMaxScore = originSubjectMaxScore.intValue();

        BigDecimal originSubjectNormalScore = sumNormalScore.divide(sumWeight, 2, RoundingMode.DOWN);
        int subjectNormalScore = originSubjectNormalScore.intValue();

        subjectScoreDTO.setQuestionAnswers(questions);
        subjectScoreDTO.setOriginMaxSubjectScore(originSubjectMaxScore);
        subjectScoreDTO.setMaxSubjectScore(subjectMaxScore);
        subjectScoreDTO.setOriginNormalSubjectScore(originSubjectNormalScore);
        subjectScoreDTO.setNormalSubjectScore(subjectNormalScore);

        return subjectScoreDTO;
    }

    private void saveExamPaperScore(ExamPaperScoreDTO examPaperScoreDTO) {
        ExamPaper examPaperUpdater = new ExamPaper();
        examPaperUpdater.setId(examPaperScoreDTO.getExamPaper().getId());
        examPaperUpdater.setOriginMaxScoreCombination(examPaperScoreDTO.getOriginMaxScoreCombination());
        examPaperUpdater.setMaxScoreCombination(examPaperScoreDTO.getMaxScoreCombination());
        examPaperUpdater.setMaxRank(examPaperScoreDTO.getMaxRank());
        examPaperUpdater.setOriginNormalScoreCombination(examPaperScoreDTO.getOriginNormalScoreCombination());
        examPaperUpdater.setNormalScoreCombination(examPaperScoreDTO.getNormalScoreCombination());
        examPaperUpdater.setNormalRank(examPaperScoreDTO.getNormalRank());
        examPaperUpdater.setUpdateTime(new Date());
        examPaperMapper.updateByPrimaryKeySelective(examPaperUpdater);
    }

    private ExamPaperReportDTO generateExamPaperReport(ExamPaperScoreDTO examPaperScoreDTO) {
        return null;
    }

}
