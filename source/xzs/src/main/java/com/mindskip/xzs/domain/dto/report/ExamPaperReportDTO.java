package com.mindskip.xzs.domain.dto.report;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 题目
 */
@Setter
@Getter
public class ExamPaperReportDTO {

    private String prefix;

    // 优势
    private String advantage;

    // 瓶颈
    private String bottleneck;

    // 痛点
    private String painPoint;

    // 答案算分权重
    private BigDecimal weight;

}
