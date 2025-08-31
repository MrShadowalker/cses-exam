package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.entity.QuestionOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionOptionMapper extends BaseMapper<QuestionOption> {

    List<QuestionOption> selectByIds(@Param("ids") List<Integer> ids);

    List<QuestionOption> selectByQuestionId(@Param("questionId") Integer questionId);

}
