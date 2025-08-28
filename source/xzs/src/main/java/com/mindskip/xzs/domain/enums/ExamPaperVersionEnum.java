package com.mindskip.xzs.domain.enums;

import lombok.Getter;

import java.util.*;

/**
 * 试卷版本
 */
@Getter
public enum ExamPaperVersionEnum {

    EXPERIENCE("experience", "体验版"),
    STANDARD("standard", "标准版"),
    TEENAGER("teenager", "青少年版"),
    CHILD("child", "儿童版"),
    ;

    String code;
    String name;

    ExamPaperVersionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    private static final Map<String, ExamPaperVersionEnum> keyMap = new HashMap<>();

    static {
        for (ExamPaperVersionEnum item : ExamPaperVersionEnum.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    public static ExamPaperVersionEnum fromCode(String code) {
        return keyMap.get(code);
    }


}
