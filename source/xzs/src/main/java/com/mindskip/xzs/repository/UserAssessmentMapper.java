package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.entity.UserAssessment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 用户测评记录Mapper接口
 */
@Mapper
public interface UserAssessmentMapper extends BaseMapper<UserAssessment> {

    /**
     * 根据用户ID和测评类型查询测评记录
     *
     * @param userId 用户ID
     * @param version 测评类型
     * @return 测评记录列表
     */
    List<UserAssessment> selectByUserIdAndVersion(@Param("userId") Integer userId, @Param("version") String version);

    /**
     * 根据用户ID和测评类型查询最近的测评记录
     *
     * @param userId 用户ID
     * @param version 测评类型
     * @return 最近的测评记录
     */
    UserAssessment selectLatestByUserIdAndVersion(@Param("userId") Integer userId, @Param("version") String version);

    /**
     * 根据用户ID和测评类型查询指定月份内的测评记录
     *
     * @param userId 用户ID
     * @param version 测评类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 测评记录列表
     */
    List<UserAssessment> selectByUserIdAndVersionInMonth(@Param("userId") Integer userId,
                                                      @Param("version") String version,
                                                      @Param("startDate") Date startDate, 
                                                      @Param("endDate") Date endDate);

    /**
     * 根据用户ID查询所有测评记录
     *
     * @param userId 用户ID
     * @return 测评记录列表
     */
    List<UserAssessment> selectByUserId(@Param("userId") Integer userId);

    /**
     * 根据测评状态查询测评记录
     *
     * @param status 测评状态
     * @return 测评记录列表
     */
    List<UserAssessment> selectByStatus(@Param("status") Integer status);

    /**
     * 统计用户指定类型的测评次数
     *
     * @param userId 用户ID
     * @param version 测评类型
     * @return 测评次数
     */
    int countByUserIdAndVersion(@Param("userId") Integer userId, @Param("version") String version);

    /**
     * 统计用户指定类型在指定月份内的测评次数
     *
     * @param userId 用户ID
     * @param version 测评类型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 测评次数
     */
    int countByUserIdAndVersionInMonth(@Param("userId") Integer userId,
                                    @Param("version") String version,
                                    @Param("startDate") Date startDate, 
                                    @Param("endDate") Date endDate);
}


