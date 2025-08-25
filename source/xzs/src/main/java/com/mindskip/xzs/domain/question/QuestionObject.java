package com.mindskip.xzs.domain.question;



import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 题目
 */
@Setter
@Getter
public class QuestionObject {

    // 题干内容
    private String titleContent;

    // 解析
    private String analyze;

    // 题目选项
    private List<QuestionItemObject> questionItemObjects;

    // 对于某些类型的题目，是有正确选项的
    private String correct;

}
