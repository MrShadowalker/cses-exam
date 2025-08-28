package com.mindskip.xzs.domain.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 开源版本提供了 1-5 五种题型
 *
 * 目前题库优先按照 6 复合选择题设计
 */
@Getter
public enum QuestionTypeEnum {

    SingleChoice(1, "单选题"),
    MultipleChoice(2, "多选题"),
    TrueFalse(3, "判断题"),
    GapFilling(4, "填空题"),
    ShortAnswer(5, "简答题"),
    ComplexChoice(6, "复合选择题"),
    ;

    int code;
    String name;

    QuestionTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }


    private static final Map<Integer, QuestionTypeEnum> keyMap = new HashMap<>();

    static {
        for (QuestionTypeEnum item : QuestionTypeEnum.values()) {
            keyMap.put(item.getCode(), item);
        }
    }

    public static QuestionTypeEnum fromCode(Integer code) {
        return keyMap.get(code);
    }

    public static boolean needSaveTextContent(Integer code) {
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(code);
        switch (questionTypeEnum) {
            case GapFilling:
            case ShortAnswer:
                return true;
            default:
                return false;
        }
    }

}
