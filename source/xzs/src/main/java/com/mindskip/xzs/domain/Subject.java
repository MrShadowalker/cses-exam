package com.mindskip.xzs.domain;

import com.mindskip.xzs.domain.enums.PhaseEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class Subject implements Serializable {

    private static final long serialVersionUID = 8058095034457106501L;

    @Setter
    private Integer id;

    /**
     * @see PhaseEnum
     */
    private String name;

    /**
     * 每个环节（科目）各五个级别
     */
    @Setter
    private Integer level;

    /**
     * 每个环节（科目）各五个级别
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