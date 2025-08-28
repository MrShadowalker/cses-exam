package com.mindskip.xzs.viewmodel.admin.question;


import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class QuestionEditItemVM {

    @NotBlank
    private String prefix;

    @NotBlank
    private String content;

    private String level;

    private String score;

    private String itemUuid;

}
