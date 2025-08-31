package com.mindskip.xzs.domain.viewmodel.student.user;


import lombok.Data;

@Data
public class MessageResponseViewModel {
    private Integer id;

    private String title;

    private Integer messageId;

    private String content;

    private Boolean readed;

    private String  createTime;

    private String sendUserName;

}
