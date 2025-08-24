package com.mindskip.xzs.domain.enums;

import lombok.Getter;

/**
 * @author Shadowalker
 */
@Getter
public enum SceneEnum {
    LIFE("life", "生活"),
    WORK("work", "工作"),
    SOCIAL("social", "社交"),
    STUDY("study", "学习"),
    HEALTH("health", "健康"),
    ;

    String code;
    String name;

    SceneEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SceneEnum fromCode(String code) {
        for (SceneEnum item : SceneEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public static SceneEnum fromName(String name) {
        for (SceneEnum item : SceneEnum.values()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

}
