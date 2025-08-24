package com.mindskip.xzs.domain.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum TargetTypeEnum {

    TYB("tyb", "体验版", "认知能力模型测评体验版"),
    BZB("bzb", "标准版", "认知能力模型测评标准版"),
    CY("cy", "是否适合创业", "给出明确结论，并提供合理的分析及原由"),
    GG("gg", "是否具备高管潜力", "给出明确结论，并提供合理的分析及原由"),
    QSN("qsn", "青少年版", "面向青少年"),
    ET("et", "儿童版", "面向儿童"),
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
