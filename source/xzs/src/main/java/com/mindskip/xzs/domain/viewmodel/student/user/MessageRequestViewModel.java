package com.mindskip.xzs.domain.viewmodel.student.user;

import com.mindskip.xzs.base.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageRequestViewModel extends BasePage {

    private Integer receiveUserId;

}
