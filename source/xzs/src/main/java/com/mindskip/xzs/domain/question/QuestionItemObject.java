package com.mindskip.xzs.domain.question;


import lombok.Data;

import java.math.BigDecimal;

/**
 * 题目选项
 */
@Data
public class QuestionItemObject {

    // 选项前缀，e.g. A B C D E
    private String prefix;

    // 选项内容
    private String content;

    // 答案算分
    private BigDecimal score;

    // 答案算分权重
    private BigDecimal weight;

    private String itemUuid;

}
