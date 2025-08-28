package com.mindskip.xzs.service.impl;

import com.mindskip.xzs.domain.enums.SubjectEnum;
import com.mindskip.xzs.domain.enums.SceneEnum;

import java.util.Map;

/**
 * 试题抽取规则管理器
 */
public class QuestionSelectionRuleManager {

    /**
     * 根据阶段获取抽题规则
     */
    public Map<SceneEnum, Integer> getRulesByPhase(SubjectEnum phase) {
        return SubjectEnum.STANDARD_SUBJECT_SCENE_RULES.get(phase);
    }

    /**
     * 验证规则有效性
     */
    public boolean validateRules() {
        for (Map.Entry<SubjectEnum, Map<SceneEnum, Integer>> entry : SubjectEnum.STANDARD_SUBJECT_SCENE_RULES.entrySet()) {
            for (Map.Entry<SceneEnum, Integer> sceneEntry : entry.getValue().entrySet()) {
                if (sceneEntry.getValue() < 0) {
                    throw new IllegalArgumentException(String.format(
                        "阶段[%s]场景[%s]的题目数量不能为负数",
                        entry.getKey().getName(),
                        sceneEntry.getKey().getName()
                    ));
                }
            }
        }
        return true;
    }

    /**
     * 计算阶段总题数
     */
    public int calculateTotalQuestions(SubjectEnum phase) {
        Map<SceneEnum, Integer> rules = getRulesByPhase(phase);
        if (rules == null) return 0;
        return rules.values().stream().mapToInt(Integer::intValue).sum();
    }
}