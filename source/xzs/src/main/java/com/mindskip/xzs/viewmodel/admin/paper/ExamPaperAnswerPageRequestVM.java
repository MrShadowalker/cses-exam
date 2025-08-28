package com.mindskip.xzs.viewmodel.admin.paper;

import com.mindskip.xzs.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExamPaperAnswerPageRequestVM extends BasePage {
    private Integer subjectId;
}
