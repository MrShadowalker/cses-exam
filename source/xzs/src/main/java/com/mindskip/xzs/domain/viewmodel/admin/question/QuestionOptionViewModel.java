package com.mindskip.xzs.domain.viewmodel.admin.question;


import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class QuestionOptionViewModel {

    /**
     * 选项前缀
     */
    @NotBlank
    private String prefix;

    /**
     * 选项内容
     */
    @NotBlank
    private String content;

    /**
     * 选项对应环节
     */
    @NotBlank
    private String level;

    /**
     * 选项对应分数
     */
    @NotBlank
    private String score;

    private String itemUuid;

}
