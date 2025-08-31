package com.mindskip.xzs.domain.dto.request;

import lombok.Data;

/**
 * @author Shadowalker
 */
@Data
public class ExamPaperMakeRequest {
    private Integer userId;
    private String version;
    /**
     * 试卷id，这个在mock的时候用一下
     */
    private Integer examPaperId;
}
