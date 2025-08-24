package com.mindskip.xzs.domain.question;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionItemObject {

    private String prefix;

    private String content;

    private Integer score;

    private String itemUuid;

}
