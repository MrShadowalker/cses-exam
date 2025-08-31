package com.mindskip.xzs.service;

import com.mindskip.xzs.domain.dto.question.QuestionDTO;
import com.mindskip.xzs.domain.entity.Question;
import com.mindskip.xzs.domain.viewmodel.admin.question.QuestionViewModel;
import com.mindskip.xzs.domain.viewmodel.admin.question.QuestionPageRequestVM;
import com.github.pagehelper.PageInfo;

public interface QuestionService extends BaseService<Question> {

    QuestionDTO getById(int questionId);
}
