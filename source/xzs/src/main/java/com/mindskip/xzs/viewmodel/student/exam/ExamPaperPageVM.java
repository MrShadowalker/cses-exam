package com.mindskip.xzs.viewmodel.student.exam;

import com.mindskip.xzs.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExamPaperPageVM extends BasePage {
    @NotNull
    private Integer paperType;
    private Integer subjectId;
    private Integer levelId;

}
