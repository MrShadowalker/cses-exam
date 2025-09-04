package com.mindskip.xzs.service;

import com.mindskip.xzs.domain.entity.UserAssessmentQuota;

import java.util.List;

/**
 * 用户测评次数服务接口
 */
public interface UserAssessmentQuotaService extends BaseService<UserAssessmentQuota> {

    /**
     * 根据用户ID查询所有测评次数记录
     *
     * @param userId 用户ID
     * @return 测评次数记录列表
     */
    List<UserAssessmentQuota> getUserQuotas(Integer userId);

    /**
     * 根据用户ID和测评类型查询测评次数记录
     *
     * @param userId 用户ID
     * @param version 测评类型
     * @return 测评次数记录
     */
    UserAssessmentQuota getUserQuota(Integer userId, String version);

    /**
     * 增加用户测评次数
     *
     * @param userId 用户ID
     * @param version 测评类型
     * @param count 增加的次数
     * @return 是否成功
     */
    boolean increaseQuota(Integer userId, String version, Integer count);

    /**
     * 减少用户测评次数
     *
     * @param userId 用户ID
     * @param version 测评类型
     * @param count 减少的次数
     * @return 是否成功
     */
    boolean decreaseQuota(Integer userId, String version, Integer count);

    /**
     * 检查用户是否有足够的测评次数
     *
     * @param userId 用户ID
     * @param version 测评类型
     * @param requiredCount 需要的次数
     * @return 是否有足够的次数
     */
    boolean checkQuotaAvailable(Integer userId, String version, Integer requiredCount);

    /**
     * 获取用户指定类型的可用测评次数
     *
     * @param userId 用户ID
     * @param version 测评类型
     * @return 可用次数
     */
    Integer getAvailableCount(Integer userId, String version);

    /**
     * 初始化用户测评次数（新用户注册时调用）
     *
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean initUserQuotas(Integer userId);

    /**
     * 为新用户发放体验版测评次数
     *
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean grantTrialAssessment(Integer userId);

    /**
     * 为分享者发放标准版测评次数（当被分享者完成体验版测评时）
     *
     * @param shareUserId 分享者用户ID
     * @return 是否成功
     */
    boolean grantStandardAssessmentForShare(Integer shareUserId);
}


