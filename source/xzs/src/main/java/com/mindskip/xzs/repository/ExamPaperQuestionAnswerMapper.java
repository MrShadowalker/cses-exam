package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.entity.ExamPaperQuestionAnswer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExamPaperQuestionAnswerMapper extends BaseMapper<ExamPaperQuestionAnswer> {

    List<ExamPaperQuestionAnswer> selectByPaperId(int examPaperId);
}
