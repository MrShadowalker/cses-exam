package com.mindskip.xzs.controller.admin;

import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("AdminDashboardController")
@RequestMapping(value = "/api/admin/dashboard")
public class DashboardController extends BaseApiController {

    private final ExamPaperService examPaperService;
    private final QuestionService questionService;
    private final ExamPaperAnswerService examPaperAnswerService;
    private final UserEventLogService userEventLogService;

    @Autowired
    public DashboardController(ExamPaperService examPaperService, QuestionService questionService, ExamPaperAnswerService examPaperAnswerService, UserEventLogService userEventLogService) {
        this.examPaperService = examPaperService;
        this.questionService = questionService;
        this.examPaperAnswerService = examPaperAnswerService;
        this.userEventLogService = userEventLogService;
    }
}
