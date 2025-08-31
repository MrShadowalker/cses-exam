package com.mindskip.xzs.domain.entity;

import com.mindskip.xzs.domain.enums.VersionEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 试卷实体类
 */
@Data
public class ExamPaper implements Serializable {

    private static final long serialVersionUID = 8509645224550501395L;

    private Integer id;

    private Integer userId;

    /**
     * 试卷标题
     */
    private String title;

    /**
     * 试卷版本
     * @see VersionEnum#getCode()
     */
    private String version;

    /**
     * 上限原始分数组合
     * 这个在解读时会关注原始得分，确保精度
     */
    private String originMaxScoreCombination;

    /**
     * 上限分数组合
     */
    private String maxScoreCombination;

    /**
     * 上限分数排名
     */
    private Integer maxRank;

    /**
     * 常态原始分数组合
     */
    private String originNormalScoreCombination;

    /**
     * 常态分数组合
     */
    private String normalScoreCombination;
    /**
     * 常态分数排名
     */
    private Integer normalRank;

    private Date createTime;

    private Date updateTime;

    private Byte deleted;

}
