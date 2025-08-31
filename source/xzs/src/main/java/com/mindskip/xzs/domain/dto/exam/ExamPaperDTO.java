package com.mindskip.xzs.domain.dto.exam;

import com.mindskip.xzs.domain.enums.VersionEnum;
import com.mindskip.xzs.domain.dto.question.QuestionDTO;
import lombok.Data;

import java.util.List;

/**
 * @author Shadowalker
 */
@Data
public class ExamPaperDTO {

    private Integer id;

    private Integer userId;

    /**
     * 试卷名称
     */
    private String title;

    /**
     * 试卷版本
     * @see VersionEnum#getCode()
     */
    private VersionEnum version;

    /**
     * 试卷题目列表
     */
    private List<QuestionDTO> questions;
}
