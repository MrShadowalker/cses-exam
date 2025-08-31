package com.mindskip.xzs.controller.wx.student;

import com.mindskip.xzs.controller.wx.BaseWXApiController;
import com.mindskip.xzs.service.ExamPaperService;
import com.mindskip.xzs.service.TextContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller("WXStudentDashboardController")
@RequestMapping(value = "/api/wx/student/dashboard")
@ResponseBody
public class DashboardController extends BaseWXApiController {

    private final ExamPaperService examPaperService;
    private final TextContentService textContentService;

    @Autowired
    public DashboardController(ExamPaperService examPaperService, TextContentService textContentService) {
        this.examPaperService = examPaperService;
        this.textContentService = textContentService;
    }


}
