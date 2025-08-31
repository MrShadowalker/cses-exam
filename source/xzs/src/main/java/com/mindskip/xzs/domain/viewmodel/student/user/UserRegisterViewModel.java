package com.mindskip.xzs.domain.viewmodel.student.user;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserRegisterViewModel {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    private Integer userLevel;

}
