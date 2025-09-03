package com.mindskip.xzs.domain.enums;

/**
 * 测评分类枚举
 */
public enum AssessmentTypeEnum {

    /**
     * 体验版测评
     */
    TRIAL(1, "体验版测评"),

    /**
     * 标准版测评
     */
    STANDARD(2, "标准版测评"),

    /**
     * 目标能力测评
     */
    TARGET_ABILITY(3, "目标能力测评"),

    /**
     * 深度咨询
     */
    DEEP_CONSULTATION(4, "深度咨询");

    private final Integer code;
    private final String name;

    AssessmentTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据代码获取枚举
     */
    public static AssessmentTypeEnum fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (AssessmentTypeEnum type : AssessmentTypeEnum.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 根据代码获取名称
     */
    public static String getNameByCode(Integer code) {
        AssessmentTypeEnum type = fromCode(code);
        return type != null ? type.getName() : null;
    }
}


