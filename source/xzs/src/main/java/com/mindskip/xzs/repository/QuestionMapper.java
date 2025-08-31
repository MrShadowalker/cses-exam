package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.dto.common.KeyValue;
import com.mindskip.xzs.domain.entity.Question;
import com.mindskip.xzs.domain.viewmodel.admin.question.QuestionPageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    List<Question> page(QuestionPageRequestVM requestVM);

    List<Question> selectByIds(@Param("ids") List<Integer> ids);

    Integer selectAllCount();

}
