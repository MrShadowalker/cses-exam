package com.mindskip.xzs.domain.entity;

import com.mindskip.xzs.domain.enums.VersionEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ExamPaperReport implements Serializable {

    private static final long serialVersionUID = -814719595269981551L;

    private Integer id;

    private Integer examPaperId;

    private Integer userId;

    /**
     * 版本
     * @see VersionEnum#getCode()
     */
    private String version;

    private Integer normalRank;

    private Integer maxRank;

    private String maxRankDescription;

    private String advantage;

    private String needImprovement;

    private String bottleneck;

    private String synergyAnalysis;

    private String probablePainpoints;

    private String possiblePainpoints;

    private String probablePainpointsText;

    private String possiblePainpointsText;

    private Date createTime;

    private Date updateTime;

    private Byte deleted;

}
