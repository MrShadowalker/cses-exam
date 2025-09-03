package com.mindskip.xzs.domain.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mindskip.xzs.domain.dto.exam.ExamPaperDTO;
import com.mindskip.xzs.domain.dto.exam.ExamPaperReportDTO;
import com.mindskip.xzs.domain.dto.question.QuestionDTO;
import com.mindskip.xzs.domain.entity.ExamPaper;
import com.mindskip.xzs.domain.entity.ExamPaperReport;
import com.mindskip.xzs.domain.entity.Question;
import com.mindskip.xzs.domain.enums.VersionEnum;
import com.mindskip.xzs.domain.viewmodel.student.exam.report.ExamPaperReportViewModel;
import com.mindskip.xzs.domain.viewmodel.student.exampaper.ExamPaperViewModel;
import com.mindskip.xzs.domain.viewmodel.student.exampaper.QuestionViewModel;
import com.mindskip.xzs.utility.CollectionUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static ExamPaperReport reportDtoToEntity(ExamPaperReportDTO examPaperReportDTO) {
        ExamPaperReport examPaperReport = new ExamPaperReport();
        // 复制基本类型字段
        examPaperReport.setId(examPaperReportDTO.getId());
        examPaperReport.setExamPaperId(examPaperReportDTO.getExamPaperId());
        examPaperReport.setUserId(examPaperReportDTO.getUserId());
        examPaperReport.setVersion(examPaperReportDTO.getVersion().getCode());
        examPaperReport.setNormalRank(examPaperReportDTO.getNormalRank());
        examPaperReport.setMaxRank(examPaperReportDTO.getMaxRank());
        examPaperReport.setMaxRankDescription(examPaperReportDTO.getMaxRankDescription());
        examPaperReport.setAdvantage(examPaperReportDTO.getAdvantage());
        examPaperReport.setNeedImprovement(examPaperReportDTO.getNeedImprovement());
        examPaperReport.setBottleneck(examPaperReportDTO.getBottleneck());
        examPaperReport.setSynergyAnalysis(examPaperReportDTO.getSynergyAnalysis());
        examPaperReport.setProbablePainpoints(CollectionUtil.intListToString(examPaperReportDTO.getProbablePainpoints()));
        examPaperReport.setPossiblePainpoints(CollectionUtil.intListToString(examPaperReportDTO.getPossiblePainpoints()));

        // 处理文本集合转字符串（将Map<String, String>转换为 | 分隔字符串）
        if (examPaperReportDTO.getProbablePainpointsText() != null && !examPaperReportDTO.getProbablePainpointsText().isEmpty()) {
            examPaperReport.setProbablePainpointsText(
                    examPaperReportDTO.getProbablePainpointsText().entrySet().stream()
                            .map(entry -> entry.getKey() + ":" + entry.getValue())
                            .collect(Collectors.joining("|"))
            );
        }

        if (examPaperReportDTO.getPossiblePainpointsText() != null) {
            examPaperReport.setPossiblePainpointsText(
                    examPaperReportDTO.getPossiblePainpointsText().entrySet().stream()
                            .map(entry -> entry.getKey() + ":" + entry.getValue())
                            .collect(Collectors.joining("|"))
            );
        }

        return examPaperReport;
    }

    public static ExamPaperReportViewModel reportDtoToViewModel(ExamPaperReportDTO examPaperReportDTO) {
        ExamPaperReportViewModel vm = new ExamPaperReportViewModel();
        vm.setExamPaperId(examPaperReportDTO.getExamPaperId());
        vm.setUserId(examPaperReportDTO.getUserId());
        vm.setVersion(examPaperReportDTO.getVersion().getCode());
        vm.setNormalRank(examPaperReportDTO.getNormalRank());
        vm.setMaxRank(examPaperReportDTO.getMaxRank());
        vm.setMaxRankDescription(examPaperReportDTO.getMaxRankDescription());
        vm.setAdvantage(examPaperReportDTO.getAdvantage());
        vm.setNeedImprovement(examPaperReportDTO.getNeedImprovement());
        vm.setBottleneck(examPaperReportDTO.getBottleneck());
        vm.setSynergyAnalysis(examPaperReportDTO.getSynergyAnalysis());
        vm.setProbablePainpoints(examPaperReportDTO.getProbablePainpointsText());
        vm.setPossiblePainpoints(examPaperReportDTO.getPossiblePainpointsText());
        return vm;
    }


    public static ExamPaperReportDTO reportEntityToDto(ExamPaperReport examPaperReport) {
        ExamPaperReportDTO examPaperReportDTO = new ExamPaperReportDTO();
        BeanUtils.copyProperties(examPaperReport, examPaperReportDTO);
        examPaperReportDTO.setVersion(VersionEnum.fromCode(examPaperReport.getVersion()));
        examPaperReportDTO.setProbablePainpoints(CollectionUtil.stringToList(examPaperReport.getProbablePainpoints(), new TypeReference<List<Integer>>() {}, ","));
        examPaperReportDTO.setPossiblePainpoints(CollectionUtil.stringToList(examPaperReport.getPossiblePainpoints(), new TypeReference<List<Integer>>() {}, ","));
        // 处理文本集合转字符串（将 | 分隔字符串转换为 Map<String, String>）
        if (examPaperReport.getProbablePainpointsText() != null && !examPaperReport.getProbablePainpointsText().isEmpty()) {
            // 先将字符串转换为 List<String>
            List<String> probablePainpointsTextList = CollectionUtil.stringToList(examPaperReport.getProbablePainpointsText(), new TypeReference<List<String>>() {}, "|");
            // 再将 List<String> 转换为 Map<String, String>
            examPaperReportDTO.setProbablePainpointsText(
                    probablePainpointsTextList.stream()
                            .collect(Collectors.toMap(
                                    s -> s.split(":")[0],
                                    s -> s.split(":")[1]
                            ))
            );
        }

        if (examPaperReport.getPossiblePainpointsText() != null) {
            // 先将字符串转换为 List<String>
            List<String> possiblePainpointsTextList = CollectionUtil.stringToList(examPaperReport.getPossiblePainpointsText(), new TypeReference<List<String>>() {}, "|");
            // 再将 List<String> 转换为 Map<String, String>
            examPaperReportDTO.setPossiblePainpointsText(
                    possiblePainpointsTextList.stream()
                            .collect(Collectors.toMap(
                                    s -> s.split(":")[0],
                                    s -> s.split(":")[1]
                            ))
            );
        }
        return examPaperReportDTO;
    }
}
