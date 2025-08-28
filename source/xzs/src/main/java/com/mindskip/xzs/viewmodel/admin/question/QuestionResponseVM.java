package com.mindskip.xzs.viewmodel.admin.question;

import com.mindskip.xzs.viewmodel.BaseVM;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionResponseVM extends BaseVM {

    private Integer id;

    private Integer questionType;

    private Integer textContentId;

    private String createTime;

    private Integer subjectId;

    private Integer createUser;

    private String score;

    private String weight;

    private Integer status;

    private String correct;

    private Integer analyzeTextContentId;

    private Integer difficult;

    private String shortTitle;

}
