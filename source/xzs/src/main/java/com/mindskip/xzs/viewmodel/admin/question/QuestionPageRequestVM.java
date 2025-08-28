package com.mindskip.xzs.viewmodel.admin.question;

import com.mindskip.xzs.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionPageRequestVM extends BasePage {

    private Integer id;
    private Integer level;
    private Integer subjectId;
    private Integer questionType;
    private String questionTargetType;
    private String questionScene;
    private String content;


}
