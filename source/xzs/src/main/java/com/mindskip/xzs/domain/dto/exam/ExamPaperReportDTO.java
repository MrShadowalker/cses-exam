package com.mindskip.xzs.domain.dto.exam;

import com.mindskip.xzs.domain.enums.VersionEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Shadowalker
 */
@Data
public class ExamPaperReportDTO {

    private Integer id;

    private Integer examPaperId;

    private Integer userId;

    /**
     * 试卷版本
     * @see VersionEnum#getCode()
     */
    private VersionEnum version;

    private Integer totalRank;

    private Integer normalRank;

    private Integer maxRank;

    private String maxRankDescription;

    private String advantage;

    private String needImprovement;

    private String bottleneck;

    private String synergyAnalysis;

    private List<Integer> probablePainpoints;

    private List<Integer> possiblePainpoints;

    private Map<String, String> probablePainpointsText;

    private Map<String, String> possiblePainpointsText;

}
