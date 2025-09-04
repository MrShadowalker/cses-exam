package com.mindskip.xzs.service.impl;

import com.mindskip.xzs.domain.converter.ExamPaperConverter;
import com.mindskip.xzs.domain.converter.QuestionConverter;
import com.mindskip.xzs.domain.dto.exam.*;
import com.mindskip.xzs.domain.dto.question.QuestionAnswerDTO;
import com.mindskip.xzs.domain.dto.question.QuestionAnswerSubmitDTO;
import com.mindskip.xzs.domain.dto.question.QuestionDTO;
import com.mindskip.xzs.domain.dto.request.ExamPaperReportRequest;
import com.mindskip.xzs.domain.entity.*;
import com.mindskip.xzs.domain.enums.SubjectEnum;
import com.mindskip.xzs.domain.param.ExamPaperReportParam;
import com.mindskip.xzs.repository.*;
import com.mindskip.xzs.service.ExamPaperAnswerService;
import com.mindskip.xzs.service.TextContentService;
import com.mindskip.xzs.utility.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Service
public class ExamPaperAnswerServiceImpl extends BaseServiceImpl<ExamPaperQuestionAnswer> implements ExamPaperAnswerService {

    private final ExamPaperMapper examPaperMapper;
    private final QuestionMapper questionMapper;
    private final QuestionOptionMapper questionOptionMapper;
    private final ExamPaperQuestionAnswerMapper examPaperQuestionAnswerMapper;
    private final ExamPaperReportMapper examPaperReportMapper;
    private final RankMapper rankMapper;
    private final PainPointMapper painPointMapper;
    private final TextContentService textContentService;

    @Autowired
    public ExamPaperAnswerServiceImpl(ExamPaperMapper examPaperMapper, QuestionMapper questionMapper, QuestionOptionMapper questionOptionMapper, ExamPaperQuestionAnswerMapper examPaperQuestionAnswerMapper, ExamPaperReportMapper examPaperReportMapper, RankMapper rankMapper, PainPointMapper painPointMapper, TextContentService textContentService) {
        super(examPaperQuestionAnswerMapper);
        this.examPaperMapper = examPaperMapper;
        this.questionMapper = questionMapper;
        this.questionOptionMapper = questionOptionMapper;
        this.examPaperQuestionAnswerMapper = examPaperQuestionAnswerMapper;
        this.examPaperReportMapper = examPaperReportMapper;
        this.rankMapper = rankMapper;
        this.painPointMapper = painPointMapper;
        this.textContentService = textContentService;
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

        // 5. 保存考试报告
        saveExamPaperReport(examPaperReportDTO);
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
        ExamPaperReportDTO examPaperReportDTO = new ExamPaperReportDTO();
        examPaperReportDTO.setExamPaperId(examPaperScoreDTO.getExamPaper().getId());
        examPaperReportDTO.setUserId(examPaperScoreDTO.getExamPaper().getUserId());
        examPaperReportDTO.setVersion(examPaperScoreDTO.getExamPaper().getVersion());

        // 排序
        int totalRank = rankMapper.selectTotalRank();
        examPaperReportDTO.setTotalRank(totalRank);
        examPaperReportDTO.setNormalRank(examPaperScoreDTO.getNormalRank());
        examPaperReportDTO.setMaxRank(examPaperScoreDTO.getMaxRank());

        // TODO 上限排名描述
        String maxRankDescription = "常态能力排名，代表您日常快速认知世界或处理问题时认知能力状态层级。\n" +
                "能力上限排名，代表您在面对重要事项，全力以赴时认知能力状态层级。当然也因未能锻炼成常态能力，故也比较耗费心神、耗费时间。\n" +
                "训练提升常态能力，在达到能力上限之前，将处于快速提升期。因为在这过程中不存在认知障碍，仅需刻意练习对应内容，让大脑里形成对应的拓扑结构即可（这也是判定是否真的内化为能力的最严谨的判定方式）。且过程中会让能力上限松动，便于后续提升。";
        examPaperReportDTO.setMaxRankDescription(maxRankDescription);

        // 常态能力
        Map<Integer, Integer> normalSubjectScoreMap = examPaperScoreDTO.getNormalSubjectScoreMap();
        // 反转为分数-环节
        Map<Integer, List<Integer>> normalScoreSubjectMap = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : normalSubjectScoreMap.entrySet()) {
            Integer subject = entry.getKey();
            Integer normalScore = entry.getValue();
            List<Integer> subjectList = normalScoreSubjectMap.getOrDefault(normalScore, new ArrayList<>());
            subjectList.add(subject);
            // subjectList 从小到大排序
            subjectList.sort(Comparator.naturalOrder());
            normalScoreSubjectMap.put(normalScore, subjectList);
        }
        // 分数从大到小排序
        List<Integer> normalScoreList = new ArrayList<>(normalScoreSubjectMap.keySet());
        normalScoreList.sort(Comparator.reverseOrder());

        // 上限能力
        Map<Integer, Integer> maxSubjectScoreMap = examPaperScoreDTO.getMaxSubjectScoreMap();
        // 反转为分数-环节
        Map<Integer, List<Integer>> maxScoreSubjectMap = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : maxSubjectScoreMap.entrySet()) {
            Integer subject = entry.getKey();
            Integer maxScore = entry.getValue();
            List<Integer> subjectList = maxScoreSubjectMap.getOrDefault(maxScore, new ArrayList<>());
            subjectList.add(subject);
            // subjectList 从小到大排序
            subjectList.sort(Comparator.naturalOrder());
            maxScoreSubjectMap.put(maxScore, subjectList);
        }
        // 分数从大到小排序
        List<Integer> maxScoreList = new ArrayList<>(maxScoreSubjectMap.keySet());
        maxScoreList.sort(Comparator.reverseOrder());


        /**
         * 优势
         *
         * 按常态分值：
         * 环节分数>4：您的$环节名称$“&（有几个拼接几个）认知能力在人类中也是具备绝对优势。
         * else if
         * 环节分数=4：您的$环节名称$“&（有几个拼接几个）认知能力在人类中也是具备很强的优势。
         * else if
         * 环节分数max：您的相对认知优势能力是$环节名称$“&（与max同分值有几个拼接几个）。
         */
        String advantage = "";
        // 按照不同分数区间生成优势描述
        for (Integer normalScore : normalScoreList) {
            if (!advantage.isEmpty()) {
                break;
            }
            List<Integer> subjectList = normalScoreSubjectMap.get(normalScore);
            // 从小到大排序
            subjectList.sort(Comparator.naturalOrder());
            // 最多取前三个
            List<Integer> subSubjectList = subjectList.subList(0, Math.min(3, subjectList.size()));
            if (subjectList.isEmpty()) {
                continue;
            }
            if (normalScore > 4) {
                // 拼接环节名称
                String subjectName = subSubjectList.stream()
                        .map(SubjectEnum::fromCode).filter(Objects::nonNull)
                        .map(SubjectEnum::getName)
                        .collect(joining("、"));
                advantage += "您的" + subjectName + "认知能力在人类中也是具备绝对优势。\n";
            } else if (normalScore == 4) {
                // 拼接环节名称
                String subjectName = subSubjectList.stream()
                        .map(SubjectEnum::fromCode).filter(Objects::nonNull)
                        .map(SubjectEnum::getName)
                        .collect(joining("、"));
                advantage += "您的" + subjectName + "认知能力在人类中也是具备很强的优势。\n";
            } else {
                // 拼接环节名称
                String subjectName = subSubjectList.stream()
                        .map(SubjectEnum::fromCode).filter(Objects::nonNull)
                        .map(SubjectEnum::getName)
                        .collect(joining("、"));
                advantage += "您的" + subjectName + "认知能力在人类中也是具备相对优势。\n";
            }
        }
        examPaperReportDTO.setAdvantage(advantage);

        /**
         * 待提升点
         *
         * 按常态分值：
         * 从测评结果来看，+
         * if min=常态取整分值的均值：
         * 目前您不存在影响您优势发挥的点，仅需按体系化逐一逐层提升即可。（这类后面提升策略就取第一环节的晋升策略）
         * else
         * 目前您的$ （列出第一个低于“常态取整的均值”的环节）$能力在影响着您的优势发挥。建议您进行体系化的提升，让您的优势进一步发挥。
         */
        String needImprovement = "";
        // 取常态取整分值的均值 normalSubjectScoreMap 的 values 计算
        double normalScoreMean = normalSubjectScoreMap.values().stream().mapToInt(Integer::intValue).average().orElse(0);
        // 取第一个低于“常态取整的均值”的环节
        int leastThanNormalScoreMean = normalScoreList.stream().filter(score -> score < normalScoreMean).findFirst().orElse(0);
        // 如果某环节的最小分数=全部环节的平均分数
        if ((double) leastThanNormalScoreMean == normalScoreMean) {
            needImprovement += "目前您不存在影响您优势发挥的点，仅需按体系化逐一逐层提升即可。";
        } else {
            // 取第一个低于“常态取整的均值”的环节的名称
            String minNormalScoreSubjectName = normalScoreSubjectMap.get(leastThanNormalScoreMean).stream()
                    .map(SubjectEnum::fromCode).filter(Objects::nonNull)
                    .map(SubjectEnum::getName)
                    .findFirst().orElse("");
            if (minNormalScoreSubjectName.isEmpty()) {
                needImprovement += "目前您的能力在影响着您的优势发挥。建议您进行体系化的提升，让您的优势进一步发挥。";
            } else {
                needImprovement += "目前您的" + minNormalScoreSubjectName + "能力在影响着您的优势发挥。建议您进行体系化的提升，让您的优势进一步发挥。";
            }
        }
        examPaperReportDTO.setNeedImprovement(needImprovement);

        /**
         * 瓶颈
         *
         * max-min>=2:您的$min环节名称$能力目前是阻碍您发挥优势的最大障碍。
         * else
         * ：您目前的认知能力，不存在明显的拖累环节。
         */
        String bottleneck = "";
        // 取第一个最小分数的环节
        int minNormalScore = normalScoreList.stream().min(Integer::compareTo).orElse(0);
        // 取最大分数
        int maxNormalScore = normalScoreList.stream().max(Integer::compareTo).orElse(0);
        // 如果最大分数与最小分数的差值大于等于2
        if (maxNormalScore - minNormalScore >= 2) {
            // 取第一个低于“常态取整的均值”的环节的名称
            String minNormalScoreSubjectName = normalScoreSubjectMap.get(minNormalScore).stream()
                    .map(SubjectEnum::fromCode).filter(Objects::nonNull)
                    .map(SubjectEnum::getName)
                    .findFirst().orElse("");
            bottleneck += "您的" + minNormalScoreSubjectName + "能力目前是阻碍您发挥优势的最大障碍。";
        } else {
            bottleneck += "您目前的认知能力，不存在明显的拖累环节。";
        }
        examPaperReportDTO.setBottleneck(bottleneck);

        /**
         * 整体能力协同性分析
         *
         * max-min=0:您的认知协调性非常好。在生活工作中很少内耗，偶尔发生也能快速协调。
         * else if
         * max-min=1 & (max-常态取整的均值)>(常态取整的均值-min)：您的认知协调性良好。如果将$第一个低于max的环节$提升一个层级会达到更好的协同性。
         * else if
         * max-min=1 & (max-常态取整的均值)<=(常态取整的均值-min)：您的认知协调性良好。如果将$第一个低环节$提升一个层级会达到更好的协同性。
         * else
         * 从数据来看，您在生活工作中存在比较难以解决的问题，或者让您比较内耗的事件。如果您目前还比较自洽，您可能是一位推崇玄学的学者。如果您能提升您瓶颈环节，将会大大改善您的认知协调性，且您会发现之前困扰的问题难度并没那么难以解决。
         */
        String synergyAnalysis = "";
        if (maxNormalScore - minNormalScore == 0) {
            synergyAnalysis += "您的认知协调性非常好。在生活工作中很少内耗，偶尔发生也能快速协调。";
        } else if (maxNormalScore - minNormalScore == 1) {
            // 第一个低于max的环节的名称, normalScoreSubjectMap.get(minNormalScore) 的结果按照从小到大排序，取第一个
            String minNormalScoreSubjectName = normalScoreSubjectMap.get(minNormalScore).stream()
                    .sorted()
                    .map(SubjectEnum::fromCode).filter(Objects::nonNull)
                    .map(SubjectEnum::getName)
                    .findFirst().orElse("");
            synergyAnalysis += "您的认知协调性良好。如果将" + minNormalScoreSubjectName + "提升一个层级会达到更好的协同性。";
        } else {
            synergyAnalysis += "从数据来看，您在生活工作中存在比较难以解决的问题，或者让您比较内耗的事件。如果您目前还比较自洽，您可能是一位推崇玄学的学者。如果您能提升您瓶颈环节，将会大大改善您的认知协调性，且您会发现之前困扰的问题难度并没那么难以解决。";
        }
        examPaperReportDTO.setSynergyAnalysis(synergyAnalysis);

        /**
         * 大概率存在的痛点
         *
         * 上限能力各分值匹配的痛点（不同于排名数据表）
         */
        List<Integer> probablePainpoints = new ArrayList<>();
        // 取上限能力各分值匹配的痛点（不同于排名数据表）
        for (Map.Entry<Integer, Integer> entry : maxSubjectScoreMap.entrySet()) {
            Integer subject = entry.getKey();
            Integer level = entry.getValue();
            List<PainPoint> painPointList = painPointMapper.selectBySubjectAndLevel(subject, level);
            if (!CollectionUtils.isEmpty(painPointList)) {
                for (PainPoint painPoint : painPointList) {
                    probablePainpoints.add(painPoint.getId());
                }
            }
        }
        examPaperReportDTO.setProbablePainpoints(probablePainpoints);
        if (!CollectionUtils.isEmpty(probablePainpoints)) {
            List<PainPoint> probablePainpointsText = painPointMapper.selectByIds(probablePainpoints);
            Map<String, String> probablePainpointsTextMap = probablePainpointsText.stream()
                    .collect(Collectors.toMap(PainPoint::getName, PainPoint::getDescription));
            examPaperReportDTO.setProbablePainpointsText(probablePainpointsTextMap);
        }

        /**
         * 可能存在的痛点
         *
         * 常态各分值匹配的痛点
         */
        List<Integer> possiblePainpoints = new ArrayList<>();
        // 取常态能力各分值匹配的痛点
        for (Map.Entry<Integer, Integer> entry : normalSubjectScoreMap.entrySet()) {
            Integer subject = entry.getKey();
            Integer level = entry.getValue();
            List<PainPoint> painPointList = painPointMapper.selectBySubjectAndLevel(subject, level);
            if (!CollectionUtils.isEmpty(painPointList)) {
                for (PainPoint painPoint : painPointList) {
                    possiblePainpoints.add(painPoint.getId());
                }
            }
        }
        examPaperReportDTO.setPossiblePainpoints(possiblePainpoints);
        if (!CollectionUtils.isEmpty(possiblePainpoints)) {
            List<PainPoint> possiblePainpointsText = painPointMapper.selectByIds(possiblePainpoints);
            Map<String, String> possiblePainpointsTextMap = possiblePainpointsText.stream()
                    .collect(Collectors.toMap(PainPoint::getName, PainPoint::getDescription));
            examPaperReportDTO.setPossiblePainpointsText(possiblePainpointsTextMap);
        }

        return examPaperReportDTO;
    }

    private void saveExamPaperReport(ExamPaperReportDTO examPaperReportDTO) {
        ExamPaperReport examPaperReport = ExamPaperConverter.reportDtoToEntity(examPaperReportDTO);
        examPaperReportMapper.insert(examPaperReport);
    }

    @Override
    public ExamPaperReportDTO getExamPaperReport(ExamPaperReportRequest report) {
        ExamPaperReportParam examPaperReportParam = new ExamPaperReportParam();
        examPaperReportParam.setExamPaperId(report.getExamPaperId());
        examPaperReportParam.setUserId(report.getUserId());
        ExamPaperReport examPaperReport = examPaperReportMapper.selectByParams(examPaperReportParam);
        return ExamPaperConverter.reportEntityToDto(examPaperReport);
    }

}
