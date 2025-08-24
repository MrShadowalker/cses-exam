package com.mindskip.xzs.domain.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shadowalker
 */
@Getter
public enum PhaseEnum {
    INFORMATION(1, "信息获取"),
    ANALYSIS(2, "分析处理"),
    DECISION(3, "决策选择"),
    ACTION(4, "行动执行"),
    REVIEW(5, "复盘感知"),
    ;

    Integer code;
    String name;

    PhaseEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PhaseEnum fromCode(Integer code) {
        for (PhaseEnum item : PhaseEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public static PhaseEnum fromName(String name) {
        for (PhaseEnum item : PhaseEnum.values()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    // 新增阶段场景数量常量定义
    public static final Map<PhaseEnum, Map<SceneEnum, Integer>> PHASE_SCENE_RULES;

    static {
        PHASE_SCENE_RULES = new HashMap<>();

        // 信息获取阶段规则
        Map<SceneEnum, Integer> infoRules = new HashMap<>();
        infoRules.put(SceneEnum.LIFE, 3);
        infoRules.put(SceneEnum.WORK, 2);
        infoRules.put(SceneEnum.SOCIAL, 1);
        infoRules.put(SceneEnum.STUDY, 1);
        infoRules.put(SceneEnum.HEALTH, 1);
        PHASE_SCENE_RULES.put(INFORMATION, infoRules);

        // 分析处理阶段规则
        Map<SceneEnum, Integer> analysisRules = new HashMap<>();
        analysisRules.put(SceneEnum.LIFE, 2);
        analysisRules.put(SceneEnum.WORK, 2);
        analysisRules.put(SceneEnum.SOCIAL, 2);
        analysisRules.put(SceneEnum.STUDY, 1);
        analysisRules.put(SceneEnum.HEALTH, 1);
        PHASE_SCENE_RULES.put(ANALYSIS, analysisRules);

        // 决策选择阶段规则
        Map<SceneEnum, Integer> decisionRules = new HashMap<>();
        decisionRules.put(SceneEnum.LIFE, 2);
        decisionRules.put(SceneEnum.WORK, 3);
        decisionRules.put(SceneEnum.SOCIAL, 1);
        decisionRules.put(SceneEnum.STUDY, 1);
        decisionRules.put(SceneEnum.HEALTH, 1);
        PHASE_SCENE_RULES.put(DECISION, decisionRules);

        // 行动执行阶段规则
        Map<SceneEnum, Integer> actionRules = new HashMap<>();
        actionRules.put(SceneEnum.LIFE, 2);
        actionRules.put(SceneEnum.WORK, 2);
        actionRules.put(SceneEnum.SOCIAL, 2);
        actionRules.put(SceneEnum.STUDY, 1);
        actionRules.put(SceneEnum.HEALTH, 1);
        PHASE_SCENE_RULES.put(ACTION, actionRules);

        // 复盘感知阶段规则
        Map<SceneEnum, Integer> reviewerRules = new HashMap<>();
        reviewerRules.put(SceneEnum.LIFE, 3);
        reviewerRules.put(SceneEnum.WORK, 1);
        reviewerRules.put(SceneEnum.SOCIAL, 2);
        reviewerRules.put(SceneEnum.STUDY, 1);
        reviewerRules.put(SceneEnum.HEALTH, 1);
        PHASE_SCENE_RULES.put(REVIEW, reviewerRules);

    }

}
