package com.mindskip.xzs.controller.student;

import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.converter.ExamPaperConverter;
import com.mindskip.xzs.domain.dto.exam.ExamPaperDTO;
import com.mindskip.xzs.domain.dto.request.ExamPaperMakeRequest;
import com.mindskip.xzs.domain.viewmodel.student.exampaper.ExamPaperViewModel;
import com.mindskip.xzs.service.ExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController("StudentExamPaperController")
@RequestMapping(value = "/api/student/exampaper")
public class ExamPaperController extends BaseApiController {

    private final ExamPaperService examPaperService;

    @Autowired
    public ExamPaperController(ExamPaperService examPaperService) {
        this.examPaperService = examPaperService;
    }

    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public RestResponse<ExamPaperViewModel> generate(@RequestBody ExamPaperMakeRequest req) {
        ExamPaperDTO examPaperDTO;
        if (req.getExamPaperId() != null) {
            examPaperDTO = examPaperService.mockGenerate();
        } else {
            examPaperDTO = examPaperService.generate(req);
        }
        ExamPaperViewModel vm = ExamPaperConverter.dtoToViewModel(examPaperDTO);
        return RestResponse.ok(vm);
    }

}
