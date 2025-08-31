package com.mindskip.xzs.domain.viewmodel.admin.exam;

import com.mindskip.xzs.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class ExamPaperPageRequestVM extends BasePage {

    private Integer id;
    private String version;

}
