package com.mindskip.xzs.domain.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试目标枚举
 */
@Getter
public enum TargetTypeEnum {

    ADULT("adult", "成人向", "认知能力模型测评成人版"),
    TEENAGER("teenager", "青少年向", "面向青少年"),
    CHILD("child", "儿童向", "面向儿童"),
    ENTREPRENEURSHIP("entrepreneurship", "是否适合创业", "给出明确结论，并提供合理的分析及原由"),
    EXECUTIVE("executive", "是否具备高管潜力", "给出明确结论，并提供合理的分析及原由"),
    ;

    String code;
    String name;
    String description;

    TargetTypeEnum(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }


    private static final Map<String, TargetTypeEnum> keyMap = new HashMap<>();

    static {
        for (TargetTypeEnum item : TargetTypeEnum.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    public static TargetTypeEnum fromCode(String code) {
        return keyMap.get(code);
    }


}
