package com.mindskip.xzs.domain.report;


import lombok.Data;

import java.math.BigDecimal;

/**
 * 题目选项
 */
@Data
public class ReportItemObject {

    private String prefix;

    // 优势
    private String advantage;

    // 瓶颈
    private String bottleneck;

    // 痛点
    private String painPoint;

    // 答案算分权重
    private BigDecimal weight;

    private String itemUuid;

}
