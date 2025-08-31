package com.mindskip.xzs.service.impl;

import com.mindskip.xzs.domain.entity.Question;
import com.mindskip.xzs.domain.dto.question.QuestionOptionDTO;
import com.mindskip.xzs.domain.dto.question.QuestionDTO;
import com.mindskip.xzs.domain.entity.QuestionOption;
import com.mindskip.xzs.domain.enums.SceneEnum;
import com.mindskip.xzs.domain.enums.SubjectEnum;
import com.mindskip.xzs.domain.enums.TargetTypeEnum;
import com.mindskip.xzs.repository.QuestionMapper;
import com.mindskip.xzs.repository.QuestionOptionMapper;
import com.mindskip.xzs.service.QuestionService;
import com.mindskip.xzs.service.TextContentService;
import com.mindskip.xzs.utility.ModelMapperSingle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question> implements QuestionService {

    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
    private final QuestionMapper questionMapper;
    private final QuestionOptionMapper questionOptionMapper;
    private final TextContentService textContentService;

    @Autowired
    public QuestionServiceImpl(QuestionMapper questionMapper, QuestionOptionMapper questionOptionMapper, TextContentService textContentService) {
        super(questionMapper);
        this.textContentService = textContentService;
        this.questionMapper = questionMapper;
        this.questionOptionMapper = questionOptionMapper;
    }

    @Override
    public QuestionDTO getById(int questionId) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(questionId);
        questionDTO.setId(question.getId());
        questionDTO.setOrder(question.getId());
        questionDTO.setTargetType(TargetTypeEnum.fromCode(question.getTargetType()));
        questionDTO.setSubject(SubjectEnum.fromCode(question.getSubject()));
        questionDTO.setScene(SceneEnum.fromCode(question.getScene()));
        questionDTO.setWeight(question.getWeight());
        questionDTO.setContent(question.getContent());

        // 题目选项
        List<QuestionOption> questionOptionList = questionOptionMapper.selectByQuestionId(questionId);
        List<QuestionOptionDTO> questionOptions = new ArrayList<>();
        for (QuestionOption questionOption : questionOptionList) {
            QuestionOptionDTO questionOptionDTO = modelMapper.map(questionOption, QuestionOptionDTO.class);
            // 暂时先用level作为order
            questionOptionDTO.setOrder(questionOption.getLevel());
            questionOptions.add(questionOptionDTO);
        }
        questionDTO.setOptions(questionOptions);

        return questionDTO;
    }


}
