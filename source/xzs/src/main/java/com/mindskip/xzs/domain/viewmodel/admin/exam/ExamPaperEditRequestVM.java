package com.mindskip.xzs.domain.viewmodel.admin.exam;


import com.mindskip.xzs.domain.enums.VersionEnum;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;


@Data
public class ExamPaperEditRequestVM {

    private Integer id;

    private String name;

    /**
     * 试卷版本
     *
     * @see VersionEnum#getCode()
     */
    private String paperVersion;

    @Size(min = 1, message = "请添加试卷标题")
    @Valid
    private List<ExamPaperTitleItemVM> titleItems;

}
