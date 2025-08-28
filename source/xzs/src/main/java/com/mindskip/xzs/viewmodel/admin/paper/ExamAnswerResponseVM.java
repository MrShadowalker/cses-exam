package com.mindskip.xzs.viewmodel.admin.paper;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExamAnswerResponseVM {
    private Integer id;

    private String name;

    private Integer questionCount;

    private BigDecimal score;

    private String createTime;

    private Integer createUser;

    private Integer subjectId;

    private Integer paperType;

    private Integer frameTextContentId;

}
