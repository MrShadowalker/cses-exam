package com.mindskip.xzs.domain.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ExamPaperTypeEnum {

    Fixed(1, "固定试卷"),
    TimeLimit(4, "时段试卷"),
    Task(6, "任务试卷");

    int code;
    String name;

    ExamPaperTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }


    private static final Map<Integer, ExamPaperTypeEnum> keyMap = new HashMap<>();

    static {
        for (ExamPaperTypeEnum item : ExamPaperTypeEnum.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    public static ExamPaperTypeEnum fromCode(Integer code) {
        return keyMap.get(code);
    }


    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }


}
