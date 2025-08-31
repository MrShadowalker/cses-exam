package com.mindskip.xzs.controller.student;

import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.service.ExamPaperService;
import com.mindskip.xzs.service.TextContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("StudentDashboardController")
@RequestMapping(value = "/api/student/dashboard")
public class DashboardController extends BaseApiController {

    private final ExamPaperService examPaperService;
    private final TextContentService textContentService;

    @Autowired
    public DashboardController(ExamPaperService examPaperService, TextContentService textContentService) {
        this.examPaperService = examPaperService;
        this.textContentService = textContentService;
    }


}
