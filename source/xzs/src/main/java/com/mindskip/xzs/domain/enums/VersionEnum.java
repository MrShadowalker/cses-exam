package com.mindskip.xzs.domain.enums;

import lombok.Getter;

import java.util.*;

/**
 * 试卷版本
 */
@Getter
public enum VersionEnum {

    EXPERIENCE("experience", "体验版"),
    STANDARD("standard", "标准版"),
    TEENAGER("teenager", "青少年版"),
    CHILD("child", "儿童版"),
    ;

    String code;
    String name;

    VersionEnum(String code, String name) {
        this.code = code;
        this.name = name;
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


}
