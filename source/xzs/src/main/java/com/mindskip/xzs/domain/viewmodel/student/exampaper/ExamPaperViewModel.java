package com.mindskip.xzs.domain.viewmodel.student.exampaper;

import com.mindskip.xzs.domain.enums.VersionEnum;
import lombok.Data;

import java.util.List;

/**
 * 试卷详情
 * @author Shadowalker
 */
@Data
public class ExamPaperViewModel {

    private Integer examPaperId;

    private Integer userId;

    /**
     * 试卷名称
     */
    private String title;

    /**
     * 试卷版本
     * @see VersionEnum#getCode()
     * TODO or getName()
     */
    private String version;

    private String versionName;

    /**
     * 试卷题目列表
     */
    private List<QuestionViewModel> questions;

    private Integer questionCount;
}
