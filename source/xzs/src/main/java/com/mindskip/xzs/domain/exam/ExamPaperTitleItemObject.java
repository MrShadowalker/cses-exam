package com.mindskip.xzs.domain.exam;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ExamPaperTitleItemObject {

    private String name;

    private List<ExamPaperQuestionItemObject> questionItems;

}
