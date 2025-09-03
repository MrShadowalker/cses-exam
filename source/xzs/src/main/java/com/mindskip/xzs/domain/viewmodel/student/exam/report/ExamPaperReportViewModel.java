package com.mindskip.xzs.domain.viewmodel.student.exam.report;

import com.mindskip.xzs.domain.enums.VersionEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 试卷答案提交
 */
@Data
public class ExamPaperReportViewModel {

    private Integer examPaperId;

    private Integer userId;

    /**
     * 试卷版本
     * 1. 测评报告 标题中会显示版本，前端根据code转换
     * 2. 体验版、标准版 展示的测评报告内容不同
     *
     * @see VersionEnum#getCode()
     */
    private String version;

    /**
     * 排名总数
     */
    private Integer totalRank;

    private Integer normalRank;

    /**
     * 上限排名
     * 仅在标准版展示
     * @see VersionEnum#STANDARD
     */
    private Integer maxRank;

    /**
     * 上限排名描述
     * 仅在标准版展示
     * @see VersionEnum#STANDARD
     */
    private String maxRankDescription;

    /**
     * 优势
     */
    private String advantage;

    /**
     * 待提升点
     * 仅在标准版展示
     * @see VersionEnum#STANDARD
     */
    private String needImprovement;

    /**
     * 瓶颈
     */
    private String bottleneck;

    /**
     * 整体协同性分析
     */
    private String synergyAnalysis;

    /**
     * 大概率存在的痛点
     * key 名字
     * value 描述
     */
    private Map<String, String> probablePainpoints;

    /**
     * 可能存在的痛点
     * key 名字
     * value 描述
     */
    private Map<String, String> possiblePainpoints;
}
