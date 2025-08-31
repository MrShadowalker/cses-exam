package com.mindskip.xzs.domain.converter;

import com.mindskip.xzs.domain.dto.question.QuestionDTO;
import com.mindskip.xzs.domain.dto.question.QuestionOptionDTO;
import com.mindskip.xzs.domain.entity.Question;
import com.mindskip.xzs.domain.entity.QuestionOption;
import com.mindskip.xzs.domain.enums.SceneEnum;
import com.mindskip.xzs.domain.enums.SubjectEnum;
import com.mindskip.xzs.domain.enums.TargetTypeEnum;
import com.mindskip.xzs.domain.viewmodel.student.exampaper.OptionViewModel;
import com.mindskip.xzs.domain.viewmodel.student.exampaper.QuestionViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shadowalker
 */
public class QuestionConverter {

    public static QuestionViewModel dtoToViewModel(QuestionDTO questionDTO) {
        QuestionViewModel questionViewModel = new QuestionViewModel();
        questionViewModel.setQuestionId(questionDTO.getId());
        questionViewModel.setOrder(questionDTO.getOrder());
        questionViewModel.setContent(questionDTO.getContent());
        // 组装试题选项
        List<QuestionOptionDTO> options = questionDTO.getOptions();
        List<OptionViewModel> optionViewModels = new ArrayList<>();
        for (QuestionOptionDTO option : options) {
            OptionViewModel optionViewModel = new OptionViewModel();
            optionViewModel.setOptionId(option.getId());
            optionViewModel.setOrder(option.getOrder());
            optionViewModel.setContent(option.getContent());
            optionViewModels.add(optionViewModel);
        }
        questionViewModel.setOptions(optionViewModels);
        return questionViewModel;
    }

    public static QuestionDTO entityToDtoWithoutOptions(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setTargetType(TargetTypeEnum.fromCode(question.getTargetType()));
        questionDTO.setSubject(SubjectEnum.fromCode(question.getSubject()));
        questionDTO.setScene(SceneEnum.fromCode(question.getScene()));
        questionDTO.setWeight(question.getWeight());
        questionDTO.setContent(question.getContent());
        return questionDTO;
    }

    public static List<QuestionOptionDTO> batchEntityToOptionDto(List<QuestionOption> questionOptions) {
        List<QuestionOptionDTO> questionOptionDTOList = new ArrayList<>();
        for (QuestionOption questionOption : questionOptions) {
            questionOptionDTOList.add(entityToOptionDto(questionOption));
        }
        return questionOptionDTOList;
    }

    public static QuestionOptionDTO entityToOptionDto(QuestionOption questionOption) {
        QuestionOptionDTO questionOptionDTO = new QuestionOptionDTO();
        questionOptionDTO.setId(questionOption.getId());
        questionOptionDTO.setQuestionId(questionOption.getQuestionId());
        questionOptionDTO.setLevel(questionOption.getLevel());
        questionOptionDTO.setScore(questionOption.getScore());
        questionOptionDTO.setOrder(questionOption.getOrder());
        questionOptionDTO.setContent(questionOption.getContent());
        return questionOptionDTO;
    }
}
