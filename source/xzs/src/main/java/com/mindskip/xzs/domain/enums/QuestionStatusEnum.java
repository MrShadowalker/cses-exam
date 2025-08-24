package com.mindskip.xzs.domain.enums;

import lombok.Getter;

@Getter
public enum QuestionStatusEnum {

    OK(1, "正常"),
    Publish(2, "发布");

    int code;
    String name;

    QuestionStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }


    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }


}
