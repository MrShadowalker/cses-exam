package com.mindskip.xzs.viewmodel.student.exampaper;

import com.mindskip.xzs.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class ExamPaperAnswerPageVM extends BasePage {

    private Integer subjectId;

    private Integer createUser;

}
