package com.mindskip.xzs.domain.viewmodel.student.exampaper;

import com.mindskip.xzs.domain.dto.question.QuestionOptionDTO;
import lombok.Data;

import java.util.List;

/**
 * @author Shadowalker
 */
@Data
public class QuestionViewModel {

    /**
     * 题目ID
     */
    private Integer questionId;

    /**
     * 题号
     */
    private Integer order;

    // 题干内容
    private String content;

    // 题目选项
    private List<OptionViewModel> options;

}
