package com.mindskip.xzs.service.impl;

import com.mindskip.xzs.domain.entity.UserAssessment;
import com.mindskip.xzs.domain.entity.UserShareRelation;
import com.mindskip.xzs.domain.enums.VersionEnum;
import com.mindskip.xzs.repository.UserAssessmentMapper;
import com.mindskip.xzs.service.UserAssessmentQuotaService;
import com.mindskip.xzs.service.UserAssessmentService;
import com.mindskip.xzs.service.UserShareRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 用户测评服务实现类
 */
@Service
public class UserAssessmentServiceImpl extends BaseServiceImpl<UserAssessment> implements UserAssessmentService {

    private final UserAssessmentMapper userAssessmentMapper;
    private final UserAssessmentQuotaService userAssessmentQuotaService;
    private final UserShareRelationService userShareRelationService;

    @Autowired
    public UserAssessmentServiceImpl(UserAssessmentMapper userAssessmentMapper, 
                                   UserAssessmentQuotaService userAssessmentQuotaService,
                                   UserShareRelationService userShareRelationService) {
        super(userAssessmentMapper);
        this.userAssessmentMapper = userAssessmentMapper;
        this.userAssessmentQuotaService = userAssessmentQuotaService;
        this.userShareRelationService = userShareRelationService;
    }

    @Override
    @Transactional
    public Integer startAssessment(Integer userId, String version) {
        // 检查是否可以开始测评
        AssessmentCheckResult checkResult = checkCanStartAssessment(userId, version);
        if (!checkResult.isCanStart()) {
            return null;
        }

        // 减少测评次数
        if (!userAssessmentQuotaService.decreaseQuota(userId, version, 1)) {
            return null;
        }

        // 创建测评记录
        UserAssessment assessment = new UserAssessment();
        assessment.setUserId(userId);
        assessment.setVersion(version);
        assessment.setStatus(2); // 进行中
        assessment.setGrantTime(new Date());
        assessment.setStartTime(new Date());
        assessment.setCreateTime(new Date());
        assessment.setUpdateTime(new Date());
        assessment.setDeleted(false);

        userAssessmentMapper.insertSelective(assessment);
        return assessment.getId();
    }

    @Override
    @Transactional
    public boolean completeAssessment(Integer assessmentId, String resultData) {
        UserAssessment assessment = userAssessmentMapper.selectByPrimaryKey(assessmentId);
        if (assessment == null || assessment.getStatus() != 2) {
            return false;
        }

        assessment.setStatus(3); // 已完成
        assessment.setCompleteTime(new Date());
        assessment.setResultData(resultData);
        assessment.setUpdateTime(new Date());

        boolean success = userAssessmentMapper.updateByPrimaryKeySelective(assessment) > 0;
        
        // 如果完成的是体验版测评，检查是否需要给分享者发放标准版测评次数
        if (success && assessment.getVersion().equals(VersionEnum.EXPERIENCE.getCode())) {
            handleTrialAssessmentCompletion(assessment.getUserId());
        }
        
        return success;
    }

    @Override
    @Transactional
    public boolean grantAssessmentQuota(Integer userId, String version, Integer count) {
        return userAssessmentQuotaService.increaseQuota(userId, version, count);
    }

    @Override
    public AssessmentCheckResult checkCanStartAssessment(Integer userId, String version) {
        // 检查测评次数是否足够
        if (!userAssessmentQuotaService.checkQuotaAvailable(userId, version, 1)) {
            return new AssessmentCheckResult(false, "测评次数不够");
        }

        // 检查是否在当月已进行过该类型测评
        if (hasAssessedInMonth(userId, version, new Date())) {
            Date lastTime = getLastAssessmentTime(userId, version);
            String message = "上次测评时间是" + formatDate(lastTime) + "，未满30日，无法再次测评";
            return new AssessmentCheckResult(false, message, lastTime);
        }

        return new AssessmentCheckResult(true, "可以开始测评");
    }

    @Override
    public List<UserAssessment> getUserAssessments(Integer userId) {
        return userAssessmentMapper.selectByUserId(userId);
    }

    @Override
    public List<UserAssessment> getUserAssessmentsByType(Integer userId, String version) {
        return userAssessmentMapper.selectByUserIdAndVersion(userId, version);
    }

    @Override
    public UserAssessment getLatestAssessment(Integer userId, String version) {
        return userAssessmentMapper.selectLatestByUserIdAndVersion(userId, version);
    }

    @Override
    public int countUserAssessments(Integer userId, String version) {
        return userAssessmentMapper.countByUserIdAndVersion(userId, version);
    }

    @Override
    public int countUserAssessmentsInMonth(Integer userId, String version, Date startDate, Date endDate) {
        return userAssessmentMapper.countByUserIdAndVersionInMonth(userId, version, startDate, endDate);
    }

    @Override
    public boolean hasAssessedInMonth(Integer userId, String version, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        // 获取当月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();
        
        // 获取下月第一天
        calendar.add(Calendar.MONTH, 1);
        Date endDate = calendar.getTime();
        
        return countUserAssessmentsInMonth(userId, version, startDate, endDate) > 0;
    }

    @Override
    public Date getLastAssessmentTime(Integer userId, String version) {
        UserAssessment latestAssessment = getLatestAssessment(userId, version);
        return latestAssessment != null ? latestAssessment.getGrantTime() : null;
    }

    /**
     * 格式化日期为中文格式
     */
    private String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        return year + "年" + month + "月" + day + "日";
    }

    /**
     * 处理体验版测评完成后的分享奖励逻辑
     */
    private void handleTrialAssessmentCompletion(Integer userId) {
        try {
            // 查找该用户的分享关系
            UserShareRelation shareRelation = userShareRelationService.getShareRelationBySharedUserId(userId);
            if (shareRelation == null) {
                return; // 没有分享关系，无需处理
            }

            Integer shareUserId = shareRelation.getShareUserId();
            
            // 统计该分享者已经分享了多少个不同的用户完成了体验版测评
            int completedTrialCount = countCompletedTrialAssessmentsByShareUser(shareUserId);
            
            // 如果分享者已经有5个不同的用户完成了体验版测评，则发放1次标准版测评次数
            if (completedTrialCount >= 5) {
                userAssessmentQuotaService.grantStandardAssessmentForShare(shareUserId);
            }
        } catch (Exception e) {
            // 分享奖励处理失败不影响测评完成，记录日志即可
            System.err.println("处理体验版测评完成分享奖励失败: " + e.getMessage());
        }
    }

    /**
     * 统计分享者分享的不同用户中完成体验版测评的数量
     */
    private int countCompletedTrialAssessmentsByShareUser(Integer shareUserId) {
        // 获取该分享者的所有分享关系
        List<UserShareRelation> shareRelations = userShareRelationService.getShareRelationsByShareUserId(shareUserId);
        if (shareRelations == null || shareRelations.isEmpty()) {
            return 0;
        }

        int completedCount = 0;
        for (UserShareRelation relation : shareRelations) {
            Integer sharedUserId = relation.getSharedUserId();
            // 检查该被分享者是否已完成过体验版测评
            if (hasCompletedTrialAssessment(sharedUserId)) {
                completedCount++;
            }
        }
        
        return completedCount;
    }

    /**
     * 检查用户是否已完成过体验版测评
     */
    private boolean hasCompletedTrialAssessment(Integer userId) {
        List<UserAssessment> assessments = userAssessmentMapper.selectByUserIdAndVersion(userId, VersionEnum.EXPERIENCE.getCode());
        if (assessments == null || assessments.isEmpty()) {
            return false;
        }
        
        // 检查是否有已完成的体验版测评
        for (UserAssessment assessment : assessments) {
            if (assessment.getStatus() == 3) { // 已完成
                return true;
            }
        }
        
        return false;
    }
}


