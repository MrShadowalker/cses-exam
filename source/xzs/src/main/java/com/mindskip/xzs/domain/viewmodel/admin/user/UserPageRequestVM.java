package com.mindskip.xzs.domain.viewmodel.admin.user;

import com.mindskip.xzs.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageRequestVM extends BasePage {

    private String userName;
    private Integer role;

}
