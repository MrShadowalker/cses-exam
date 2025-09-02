package com.mindskip.xzs.domain.converter;

import com.mindskip.xzs.domain.dto.exam.ExamPaperDTO;
import com.mindskip.xzs.domain.dto.exam.ExamPaperReportDTO;
import com.mindskip.xzs.domain.dto.question.QuestionDTO;
import com.mindskip.xzs.domain.entity.ExamPaper;
import com.mindskip.xzs.domain.entity.ExamPaperReport;
import com.mindskip.xzs.domain.entity.Question;
import com.mindskip.xzs.domain.enums.VersionEnum;
import com.mindskip.xzs.domain.viewmodel.student.exampaper.ExamPaperViewModel;
import com.mindskip.xzs.domain.viewmodel.student.exampaper.QuestionViewModel;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shadowalker
 */
public class ExamPaperConverter {

    public static ExamPaperDTO entityToDtoWithoutQuestions(ExamPaper examPaper) {
        ExamPaperDTO dto = new ExamPaperDTO();
        dto.setId(examPaper.getId());
        dto.setUserId(examPaper.getUserId());
        dto.setTitle(examPaper.getTitle());
        dto.setVersion(VersionEnum.fromCode(examPaper.getVersion()));
        return dto;
    }

    public static ExamPaperViewModel dtoToViewModel(ExamPaperDTO examPaperDTO) {
        ExamPaperViewModel vm = new ExamPaperViewModel();
        vm.setExamPaperId(examPaperDTO.getId());
        vm.setUserId(examPaperDTO.getUserId());
        vm.setTitle(examPaperDTO.getTitle());
        vm.setVersion(examPaperDTO.getVersion().getCode());
        vm.setVersionName(examPaperDTO.getVersion().getName());
        vm.setQuestions(new ArrayList<>());
        for (QuestionDTO questionDTO : examPaperDTO.getQuestions()) {
            QuestionViewModel questionViewModel = QuestionConverter.dtoToViewModel(questionDTO);
            vm.getQuestions().add(questionViewModel);
        }
        vm.setQuestionCount(examPaperDTO.getQuestions().size());
        return vm;
    }

    public static ExamPaperReport dtoToEntity(ExamPaperReportDTO examPaperReportDTO) {
        ExamPaperReport examPaperReport = new ExamPaperReport();
        BeanUtils.copyProperties(examPaperReportDTO, examPaperReport);
        return examPaperReport;
    }


}
