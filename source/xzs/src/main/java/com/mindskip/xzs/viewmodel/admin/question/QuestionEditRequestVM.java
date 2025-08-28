package com.mindskip.xzs.viewmodel.admin.question;


import com.mindskip.xzs.domain.enums.QuestionTypeEnum;
import com.mindskip.xzs.domain.enums.SceneEnum;
import com.mindskip.xzs.domain.enums.TargetTypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
public class QuestionEditRequestVM {

    private Integer id;

    /**
     * @see QuestionTypeEnum#getCode()
     */
    @NotNull
    private Integer questionType;

    /**
     * 目标类型
     * @see TargetTypeEnum#getCode()
     */
    private String targetType;

    /**
     * 场景
     * @see SceneEnum#getCode()
     */
    private String scene;

    /**
     * 题干
     */
    @NotBlank
    private String title;

    @Deprecated
    // @NotNull
    private Integer subjectId;

    @Deprecated
    private Integer gradeLevel;

    @Valid
    private List<QuestionEditItemVM> items;

    // @NotBlank
    private String analyze;

    private List<String> correctArray;

    private String correct;

    // @NotBlank
    private String score;

    /**
     * 算分权重
     */
    private String weight;

    @Range(min = 1, max = 5, message = "请选择题目难度")
    private Integer difficult;

    private Integer itemOrder;

}
