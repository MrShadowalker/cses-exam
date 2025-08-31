package com.mindskip.xzs.domain.viewmodel.admin.question;


import com.mindskip.xzs.domain.enums.SceneEnum;
import com.mindskip.xzs.domain.enums.TargetTypeEnum;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;


@Data
public class QuestionViewModel {

    /**
     * 题目ID
     */
    private Integer questionId;

    /**
     * 目标类型
     * @see TargetTypeEnum#getCode()
     */
    private String targetType;

    /**
     * 场景类型
     * @see SceneEnum#getCode()
     */
    private String scene;

    /**
     * 算分权重
     */
    private String weight;

    /**
     * 题目排序
     */
    private Integer itemOrder;

    /**
     * 题干内容
     */
    @NotBlank
    private String content;

    /**
     * 题目选项
     */
    @Valid
    private List<QuestionOptionViewModel> items;

}
