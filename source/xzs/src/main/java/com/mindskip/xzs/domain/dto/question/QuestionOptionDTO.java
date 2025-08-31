package com.mindskip.xzs.domain.dto.question;


import lombok.Data;

/**
 * 题目选项
 */
@Data
public class QuestionOptionDTO {

    private Integer id;

    private Integer questionId;

    // 选项所属等级
    private Integer level;

    // 选项分数
    private Integer score;

    // 选项排序
    private Integer order;

    // 选项前缀，e.g. A B C D E
    private String prefix;

    // 选项内容
    private String content;
}
