package com.mindskip.xzs.domain.viewmodel.student.user;


import lombok.Data;

@Data
public class UserEventLogViewModel {

    private Integer id;

    private Integer userId;

    private String userName;

    private String realName;

    private String content;

    private String createTime;

}
