package com.mindskip.xzs.domain.entity;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class PainPoint implements Serializable {

    private static final long serialVersionUID = -1279530310964668131L;

    public PainPoint(String content, Date createTime) {
        this.content = content;
        this.createTime = createTime;
    }

    private Integer id;

    /**
     * 内容(Json)
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
