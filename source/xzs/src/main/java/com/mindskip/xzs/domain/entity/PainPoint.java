package com.mindskip.xzs.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PainPoint implements Serializable {

    private static final long serialVersionUID = -6088334183387186517L;

    private Integer id;

    private Integer subject;

    private Integer subject_name;

    private Integer level;

    private String name;

    /**
     * 内容(Json)
     */
    private String description;
}
