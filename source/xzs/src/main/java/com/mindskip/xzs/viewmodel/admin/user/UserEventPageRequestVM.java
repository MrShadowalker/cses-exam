package com.mindskip.xzs.viewmodel.admin.user;

import com.mindskip.xzs.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserEventPageRequestVM extends BasePage {

    private Integer userId;

    private String userName;

}
