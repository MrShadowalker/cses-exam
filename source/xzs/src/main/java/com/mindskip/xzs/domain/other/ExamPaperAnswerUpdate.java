package com.mindskip.xzs.domain.other;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ExamPaperAnswerUpdate {
    private Integer id;
    private BigDecimal customerScore;
    private Boolean doRight;

}
