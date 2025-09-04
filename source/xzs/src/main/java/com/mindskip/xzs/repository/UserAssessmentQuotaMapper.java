package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.entity.UserAssessmentQuota;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户测评次数Mapper接口
 */
@Mapper
public interface UserAssessmentQuotaMapper extends BaseMapper<UserAssessmentQuota> {

    /**
     * 根据用户ID查询所有测评次数记录
     *
     * @param userId 用户ID
     * @return 测评次数记录列表
     */
    List<UserAssessmentQuota> selectByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户ID和测评类型查询测评次数记录
     *
     * @param userId 用户ID
     * @param version 测评类型
     * @return 测评次数记录
     */
    UserAssessmentQuota selectByUserIdAndVersion(@Param("userId") Integer userId, @Param("version") String version);

    /**
     * 增加用户测评次数
     *
     * @param userId 用户ID
     * @param version 测评类型
     * @param count 增加的次数
     * @return 影响行数
     */
    int increaseQuota(@Param("userId") Integer userId, @Param("version") String version, @Param("count") Integer count);

    /**
     * 减少用户测评次数
     *
     * @param userId 用户ID
     * @param version 测评类型
     * @param count 减少的次数
     * @return 影响行数
     */
    int decreaseQuota(@Param("userId") Integer userId, @Param("version") String version, @Param("count") Integer count);

    /**
     * 检查用户是否有足够的测评次数
     *
     * @param userId 用户ID
     * @param version 版本
     * @param requiredCount 需要的次数
     * @return 是否有足够的次数
     */
    boolean checkQuotaAvailable(@Param("userId") Integer userId, @Param("version") String version, @Param("requiredCount") Integer requiredCount);
}


