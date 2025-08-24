package com.mindskip.xzs.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class Subject implements Serializable {

    private static final long serialVersionUID = 8058095034457106501L;

    @Setter
    private Integer id;

    /**
     * 语文 数学 英语 等
     */
    private String name;

    /**
     * 年级 (1-12) 小学 初中
     */
    @Setter
    private Integer level;

    /**
     * 一年级、二年级等
     */
    private String levelName;

    /**
     * 排序
     */
    @Setter
    private Integer itemOrder;

    @Setter
    private Boolean deleted;

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

}