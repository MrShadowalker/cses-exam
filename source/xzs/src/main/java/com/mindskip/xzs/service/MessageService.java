package com.mindskip.xzs.service;

import com.mindskip.xzs.domain.entity.Message;
import com.mindskip.xzs.domain.entity.MessageUser;
import com.mindskip.xzs.domain.viewmodel.admin.message.MessagePageRequestVM;
import com.mindskip.xzs.domain.viewmodel.student.user.MessageRequestViewModel;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MessageService {

    List<Message> selectMessageByIds(List<Integer> ids);

    PageInfo<MessageUser> studentPage(MessageRequestViewModel requestVM);

    PageInfo<Message> page(MessagePageRequestVM requestVM);

    List<MessageUser> selectByMessageIds(List<Integer> ids);

    void sendMessage(Message message, List<MessageUser> messageUsers);

    void read(Integer id);

    Integer unReadCount(Integer userId);

    Message messageDetail(Integer id);
}
