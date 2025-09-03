package com.mindskip.xzs.service.impl;

import com.mindskip.xzs.domain.entity.UserAssessmentQuota;
import com.mindskip.xzs.domain.enums.AssessmentTypeEnum;
import com.mindskip.xzs.repository.UserAssessmentQuotaMapper;
import com.mindskip.xzs.service.UserAssessmentQuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户测评次数服务实现类
 */
@Service
public class UserAssessmentQuotaServiceImpl extends BaseServiceImpl<UserAssessmentQuota> implements UserAssessmentQuotaService {

    private final UserAssessmentQuotaMapper userAssessmentQuotaMapper;

    @Autowired
    public UserAssessmentQuotaServiceImpl(UserAssessmentQuotaMapper userAssessmentQuotaMapper) {
        super(userAssessmentQuotaMapper);
        this.userAssessmentQuotaMapper = userAssessmentQuotaMapper;
    }

    @Override
    public List<UserAssessmentQuota> getUserQuotas(Integer userId) {
        return userAssessmentQuotaMapper.selectByUserId(userId);
    }

    @Override
    public UserAssessmentQuota getUserQuota(Integer userId, Integer assessmentType) {
        return userAssessmentQuotaMapper.selectByUserIdAndType(userId, assessmentType);
    }

    @Override
    @Transactional
    public boolean increaseQuota(Integer userId, Integer assessmentType, Integer count) {
        UserAssessmentQuota quota = getUserQuota(userId, assessmentType);
        
        if (quota == null) {
            // 如果不存在，创建新记录
            quota = new UserAssessmentQuota();
            quota.setUserId(userId);
            quota.setAssessmentType(assessmentType);
            quota.setAvailableCount(count);
            quota.setUsedCount(0);
            quota.setTotalCount(count);
            quota.setCreateTime(new Date());
            quota.setUpdateTime(new Date());
            quota.setDeleted(false);
            
            return userAssessmentQuotaMapper.insertSelective(quota) > 0;
        } else {
            // 如果存在，增加次数
            return userAssessmentQuotaMapper.increaseQuota(userId, assessmentType, count) > 0;
        }
    }

    @Override
    @Transactional
    public boolean decreaseQuota(Integer userId, Integer assessmentType, Integer count) {
        return userAssessmentQuotaMapper.decreaseQuota(userId, assessmentType, count) > 0;
    }

    @Override
    public boolean checkQuotaAvailable(Integer userId, Integer assessmentType, Integer requiredCount) {
        return userAssessmentQuotaMapper.checkQuotaAvailable(userId, assessmentType, requiredCount);
    }

    @Override
    public Integer getAvailableCount(Integer userId, Integer assessmentType) {
        UserAssessmentQuota quota = getUserQuota(userId, assessmentType);
        return quota != null ? quota.getAvailableCount() : 0;
    }

    @Override
    @Transactional
    public boolean initUserQuotas(Integer userId) {
        // 为新用户初始化所有类型的测评次数为0
        boolean success = true;
        
        for (AssessmentTypeEnum type : AssessmentTypeEnum.values()) {
            UserAssessmentQuota quota = new UserAssessmentQuota();
            quota.setUserId(userId);
            quota.setAssessmentType(type.getCode());
            quota.setAvailableCount(0);
            quota.setUsedCount(0);
            quota.setTotalCount(0);
            quota.setCreateTime(new Date());
            quota.setUpdateTime(new Date());
            quota.setDeleted(false);
            
            if (userAssessmentQuotaMapper.insertSelective(quota) <= 0) {
                success = false;
            }
        }
        
        return success;
    }

    @Override
    @Transactional
    public boolean grantTrialAssessment(Integer userId) {
        // 为新用户发放1次体验版测评次数
        return increaseQuota(userId, AssessmentTypeEnum.TRIAL.getCode(), 1);
    }

    @Override
    @Transactional
    public boolean grantStandardAssessmentForShare(Integer shareUserId) {
        // 为分享者发放1次标准版测评次数
        return increaseQuota(shareUserId, AssessmentTypeEnum.STANDARD.getCode(), 1);
    }
}


