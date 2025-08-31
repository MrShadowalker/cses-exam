package com.mindskip.xzs.domain.dto.question;



import com.mindskip.xzs.domain.enums.SceneEnum;
import com.mindskip.xzs.domain.enums.SubjectEnum;
import com.mindskip.xzs.domain.enums.TargetTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 题目
 * 业务层数据封装对象
 */
@Data
public class QuestionDTO {

    /**
     * t_question 表主键ID
     */
    private Integer id;

    /**
     * 题目目标类型
     */
    private TargetTypeEnum targetType;

    /**
     * 题目科目
     */
    private SubjectEnum subject;

    /**
     * 题目场景
     */
    private SceneEnum scene;

    // 题目算分权重
    private BigDecimal weight;

    /**
     * 题目排序，题号
     */
    private Integer order;

    // 题干内容
    private String content;

    // 题目选项
    private List<QuestionOptionDTO> options;

}
