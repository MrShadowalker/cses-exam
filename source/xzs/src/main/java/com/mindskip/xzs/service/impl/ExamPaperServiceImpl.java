package com.mindskip.xzs.service.impl;

import com.mindskip.xzs.domain.dto.exam.ExamPaperDTO;
import com.mindskip.xzs.domain.dto.question.QuestionDTO;
import com.mindskip.xzs.domain.dto.request.ExamPaperMakeRequest;
import com.mindskip.xzs.domain.enums.VersionEnum;
import com.mindskip.xzs.repository.ExamPaperMapper;
import com.mindskip.xzs.repository.QuestionMapper;
import com.mindskip.xzs.service.ExamPaperService;
import com.mindskip.xzs.service.QuestionService;
import com.mindskip.xzs.service.TextContentService;
import com.mindskip.xzs.utility.ModelMapperSingle;
import com.mindskip.xzs.domain.entity.ExamPaper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamPaperServiceImpl extends BaseServiceImpl<ExamPaper> implements ExamPaperService {

    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
    private final ExamPaperMapper examPaperMapper;
    private final QuestionMapper questionMapper;
    private final QuestionService questionService;
    private final TextContentService textContentService;


    @Autowired
    public ExamPaperServiceImpl(ExamPaperMapper examPaperMapper, QuestionMapper questionMapper, QuestionService questionService, TextContentService textContentService) {
        super(examPaperMapper);
        this.examPaperMapper = examPaperMapper;
        this.questionMapper = questionMapper;
        this.questionService = questionService;
        this.textContentService = textContentService;

    }

    @Override
    public ExamPaperDTO generate(ExamPaperMakeRequest req) {
        return null;
    }

    @Override
    public ExamPaperDTO mockGenerate() {
        ExamPaperDTO examPaperDTO = new ExamPaperDTO();
        examPaperDTO.setId(123);
        examPaperDTO.setUserId(2);
        examPaperDTO.setTitle("模拟试卷");
        examPaperDTO.setVersion(VersionEnum.STANDARD);

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            QuestionDTO questionDTO = questionService.getById(i);
            questionDTOList.add(questionDTO);
        }
        examPaperDTO.setQuestions(questionDTOList);
        return examPaperDTO;
    }
}
