package com.mindskip.xzs.domain.enums;

import lombok.Getter;

import java.util.*;

/**
 * 试卷版本
 */
@Getter
public enum VersionEnum {

    EXPERIENCE("experience", "体验版测评", "体验版测评介绍"),
    STANDARD("standard", "标准版测评", "标准版测评介绍"),
    TEENAGER("teenager", "青少年版测评", "青少年版测评介绍"),
    CHILD("child", "儿童版测评", "儿童版测评介绍"),
    TARGET_ABILITY("ability", "目标能力版测评", "目标能力版测评介绍"),
    DEEP_CONSULTATION("deep", "深度咨询测评", "深度咨询测评介绍")
    ;

    String code;
    String name;
    String description;

    VersionEnum(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }


    private static final Map<String, VersionEnum> keyMap = new HashMap<>();

    static {
        for (VersionEnum item : VersionEnum.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    public static VersionEnum fromCode(String code) {
        return keyMap.get(code);
    }

    /**
     * 根据代码获取名称
     */
    public static String getNameByCode(String code) {
        VersionEnum type = fromCode(code);
        return type != null ? type.getName() : null;
    }


}
