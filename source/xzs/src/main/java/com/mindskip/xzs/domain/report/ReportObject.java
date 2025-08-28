package com.mindskip.xzs.domain.report;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 题目
 */
@Setter
@Getter
public class ReportObject {

    // 题干内容
    private String titleContent;

    // 解析
    private String analyze;

    // 子项报告列表
    private List<ReportItemObject> reportItemObjects;

}
