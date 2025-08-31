package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.entity.MessageUser;
import com.mindskip.xzs.domain.viewmodel.student.user.MessageRequestViewModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageUserMapper extends BaseMapper<MessageUser> {

    List<MessageUser> selectByMessageIds(List<Integer> ids);

    int inserts(List<MessageUser> list);

    List<MessageUser> studentPage(MessageRequestViewModel requestVM);

    Integer unReadCount(Integer userId);
}
