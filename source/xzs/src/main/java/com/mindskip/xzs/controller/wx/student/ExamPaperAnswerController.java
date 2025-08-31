package com.mindskip.xzs.controller.wx.student;

import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.controller.wx.BaseWXApiController;
import com.mindskip.xzs.domain.dto.exam.ExamPaperAnswerSubmitDTO;
import com.mindskip.xzs.domain.viewmodel.student.exam.answer.ExamPaperAnswerSubmitViewModel;
import com.mindskip.xzs.service.ExamPaperAnswerService;
import com.mindskip.xzs.service.ExamPaperService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller("WXStudentExamPaperAnswerController")
@RequestMapping(value = "/api/wx/student/exampaper/answer")
@ResponseBody
public class ExamPaperAnswerController extends BaseWXApiController {

    private final ExamPaperAnswerService examPaperAnswerService;
    private final ApplicationEventPublisher eventPublisher;
    private final ExamPaperService examPaperService;

    @Autowired
    public ExamPaperAnswerController(ExamPaperAnswerService examPaperAnswerService, ApplicationEventPublisher eventPublisher, ExamPaperService examPaperService) {
        this.examPaperAnswerService = examPaperAnswerService;
        this.eventPublisher = eventPublisher;
        this.examPaperService = examPaperService;
    }

    @PostMapping(value = "/submit")
    public RestResponse submit(@RequestBody ExamPaperAnswerSubmitViewModel submit) {
        ExamPaperAnswerSubmitDTO submitDTO = new ExamPaperAnswerSubmitDTO();
        BeanUtils.copyProperties(submit, submitDTO);
        try {
            examPaperAnswerService.submit(submitDTO);
        } catch (Exception e) {
            return RestResponse.fail(500, "试卷提交失败");
        }
        return RestResponse.ok();
    }


}
