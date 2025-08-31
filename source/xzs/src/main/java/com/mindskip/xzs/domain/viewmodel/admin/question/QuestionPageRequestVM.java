package com.mindskip.xzs.domain.viewmodel.admin.question;

import com.mindskip.xzs.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionPageRequestVM extends BasePage {

    private Integer id;
    private Integer subject;
    private String scene;
    private String targetType;
    private String content;

}
