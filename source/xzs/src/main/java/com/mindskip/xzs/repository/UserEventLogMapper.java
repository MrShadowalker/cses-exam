package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.entity.UserEventLog;
import com.mindskip.xzs.domain.dto.common.KeyValue;
import com.mindskip.xzs.domain.viewmodel.admin.user.UserEventPageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserEventLogMapper extends BaseMapper<UserEventLog> {

    List<UserEventLog> getUserEventLogByUserId(Integer id);

    List<UserEventLog> page(UserEventPageRequestVM requestVM);

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
