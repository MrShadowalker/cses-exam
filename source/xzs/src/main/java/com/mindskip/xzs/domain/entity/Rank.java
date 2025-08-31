package com.mindskip.xzs.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Rank implements Serializable {

    private static final long serialVersionUID = -4938122208902300404L;


    private Integer id;

    /**
     * 排名
     */
    private Integer rank;

    /**
     * 称号
     */
    private String title;

    /**
     * 分数组合
     */
    private String scoreCombination;

    /**
     * 组合hash
     */
    private String combinationHash;

    /**
     * 总分
     */
    private Integer totalCount;

}
