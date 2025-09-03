package com.mindskip.xzs.controller.wx.student;

import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.controller.wx.BaseWXApiController;
import com.mindskip.xzs.domain.entity.UserAssessment;
import com.mindskip.xzs.domain.entity.UserAssessmentQuota;
import com.mindskip.xzs.domain.enums.AssessmentTypeEnum;
import com.mindskip.xzs.service.UserAssessmentQuotaService;
import com.mindskip.xzs.service.UserAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信端用户测评控制器
 */
@Controller("WXStudentAssessmentController")
@RequestMapping(value = "/api/wx/student/assessment")
@ResponseBody
public class AssessmentController extends BaseWXApiController {

    private final UserAssessmentService userAssessmentService;
    private final UserAssessmentQuotaService userAssessmentQuotaService;

    @Autowired
    public AssessmentController(UserAssessmentService userAssessmentService, 
                               UserAssessmentQuotaService userAssessmentQuotaService) {
        this.userAssessmentService = userAssessmentService;
        this.userAssessmentQuotaService = userAssessmentQuotaService;
    }

    /**
     * 获取用户测评次数信息
     */
    @RequestMapping(value = "/quota", method = RequestMethod.GET)
    public RestResponse getUserQuota() {
        Integer userId = getCurrentUser().getId();
        List<UserAssessmentQuota> quotas = userAssessmentQuotaService.getUserQuotas(userId);
        
        Map<String, Object> result = new HashMap<>();
        for (UserAssessmentQuota quota : quotas) {
            String typeName = AssessmentTypeEnum.getNameByCode(quota.getAssessmentType());
            Map<String, Object> quotaInfo = new HashMap<>();
            quotaInfo.put("availableCount", quota.getAvailableCount());
            quotaInfo.put("usedCount", quota.getUsedCount());
            quotaInfo.put("totalCount", quota.getTotalCount());
            result.put(typeName, quotaInfo);
        }
        
        return RestResponse.ok(result);
    }

    /**
     * 检查是否可以开始测评
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public RestResponse checkCanStartAssessment(@Valid @RequestBody AssessmentCheckRequest request) {
        Integer userId = getCurrentUser().getId();
        UserAssessmentService.AssessmentCheckResult checkResult = 
            userAssessmentService.checkCanStartAssessment(userId, request.getAssessmentType());
        
        Map<String, Object> result = new HashMap<>();
        result.put("canStart", checkResult.isCanStart());
        result.put("message", checkResult.getMessage());
        if (checkResult.getLastAssessmentTime() != null) {
            result.put("lastAssessmentTime", checkResult.getLastAssessmentTime());
        }
        
        return RestResponse.ok(result);
    }

    /**
     * 开始测评
     */
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public RestResponse startAssessment(@Valid @RequestBody AssessmentStartRequest request) {
        Integer userId = getCurrentUser().getId();
        Integer assessmentId = userAssessmentService.startAssessment(userId, request.getAssessmentType());
        
        if (assessmentId == null) {
            // 检查失败原因
            UserAssessmentService.AssessmentCheckResult checkResult = 
                userAssessmentService.checkCanStartAssessment(userId, request.getAssessmentType());
            return RestResponse.fail(1, checkResult.getMessage());
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("assessmentId", assessmentId);
        result.put("message", "测评已开始");
        
        return RestResponse.ok(result);
    }

    /**
     * 完成测评
     */
    @RequestMapping(value = "/complete", method = RequestMethod.POST)
    public RestResponse completeAssessment(@Valid @RequestBody AssessmentCompleteRequest request) {
        boolean success = userAssessmentService.completeAssessment(request.getAssessmentId(), request.getResultData());
        
        if (success) {
            return RestResponse.ok("测评已完成");
        } else {
            return RestResponse.fail(1, "完成测评失败");
        }
    }

    /**
     * 获取用户测评记录
     */
    @RequestMapping(value = "/records", method = RequestMethod.GET)
    public RestResponse getUserAssessments(@RequestParam(required = false) Integer assessmentType) {
        Integer userId = getCurrentUser().getId();
        List<UserAssessment> assessments;
        
        if (assessmentType != null) {
            assessments = userAssessmentService.getUserAssessmentsByType(userId, assessmentType);
        } else {
            assessments = userAssessmentService.getUserAssessments(userId);
        }
        
        return RestResponse.ok(assessments);
    }

    /**
     * 获取测评类型列表
     */
    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public RestResponse getAssessmentTypes() {
        Map<String, Object> result = new HashMap<>();
        for (AssessmentTypeEnum type : AssessmentTypeEnum.values()) {
            Map<String, Object> typeInfo = new HashMap<>();
            typeInfo.put("code", type.getCode());
            typeInfo.put("name", type.getName());
            result.put(type.getName(), typeInfo);
        }
        
        return RestResponse.ok(result);
    }

    /**
     * 测评检查请求类
     */
    public static class AssessmentCheckRequest {
        @NotNull(message = "测评类型不能为空")
        private Integer assessmentType;

        public Integer getAssessmentType() {
            return assessmentType;
        }

        public void setAssessmentType(Integer assessmentType) {
            this.assessmentType = assessmentType;
        }
    }

    /**
     * 开始测评请求类
     */
    public static class AssessmentStartRequest {
        @NotNull(message = "测评类型不能为空")
        private Integer assessmentType;

        public Integer getAssessmentType() {
            return assessmentType;
        }

        public void setAssessmentType(Integer assessmentType) {
            this.assessmentType = assessmentType;
        }
    }

    /**
     * 完成测评请求类
     */
    public static class AssessmentCompleteRequest {
        @NotNull(message = "测评ID不能为空")
        private Integer assessmentId;
        
        private String resultData;

        public Integer getAssessmentId() {
            return assessmentId;
        }

        public void setAssessmentId(Integer assessmentId) {
            this.assessmentId = assessmentId;
        }

        public String getResultData() {
            return resultData;
        }

        public void setResultData(String resultData) {
            this.resultData = resultData;
        }
    }
}

