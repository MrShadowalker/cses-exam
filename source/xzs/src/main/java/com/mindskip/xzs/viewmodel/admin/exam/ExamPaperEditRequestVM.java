package com.mindskip.xzs.viewmodel.admin.exam;


import com.mindskip.xzs.domain.enums.ExamPaperTypeEnum;
import com.mindskip.xzs.domain.enums.ExamPaperVersionEnum;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Data
public class ExamPaperEditRequestVM {

    private Integer id;

    @NotBlank
    private String name;

    /**
     * @see ExamPaperTypeEnum#getCode()
     */
    @NotNull
    private Integer paperType;

    /**
     * 试卷版本
     *
     * @see ExamPaperVersionEnum#getCode()
     */
    private String paperVersion;

    @Size(min = 1, message = "请添加试卷标题")
    @Valid
    private List<ExamPaperTitleItemVM> titleItems;

    private String score;

    @Deprecated
    // @NotNull
    private Integer level;

    @Deprecated
    // @NotNull
    private Integer subjectId;

    @Deprecated
    // @NotNull
    private Integer suggestTime;

    @Deprecated
    private List<String> limitDateTime;

}
