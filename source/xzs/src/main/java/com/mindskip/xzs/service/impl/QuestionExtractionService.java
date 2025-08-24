package com.mindskip.xzs.service.impl;

import org.springframework.stereotype.Service;

/**
 * 抽题服务实现示例
 */
@Service
public class QuestionExtractionService {

//    private final QuestionRepository questionRepository;
//    private final QuestionSelectionRuleManager ruleManager;
//
//    @Autowired
//    public QuestionExtractionService(QuestionRepository questionRepository, QuestionSelectionRuleManager ruleManager) {
//        this.questionRepository = questionRepository;
//        this.ruleManager = ruleManager;
//    }
//
//    /**
//     * 根据阶段抽取题目
//     */
//    public List<Question> extractQuestionsByPhase(PhaseEnum phase) {
//        // 1. 验证规则
//        ruleManager.validateRules();
//
//        // 2. 获取当前阶段规则
//        Map<SceneEnum, Integer> rules = ruleManager.getRulesByPhase(phase);
//        if (rules == null || rules.isEmpty()) {
//            throw new IllegalArgumentException("未找到阶段[" + phase.getName() + "]的抽题规则");
//        }
//
//        // 3. 按规则抽取题目
//        List<Question> result = new ArrayList<>();
//        for (Map.Entry<SceneEnum, Integer> entry : rules.entrySet()) {
//            List<Question> questions = questionRepository.findBySceneAndRandom(
//                entry.getKey(),
//                entry.getValue()
//            );
//            result.addAll(questions);
//        }
//
//        return result;
//    }
}