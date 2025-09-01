package com.mindskip.xzs.repository;

import com.mindskip.xzs.domain.entity.UserShareRelation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户分享关系Mapper接口
 */
@Mapper
public interface UserShareRelationMapper {

    /**
     * 插入分享关系记录
     *
     * @param record 分享关系记录
     * @return 影响行数
     */
    int insert(UserShareRelation record);

    /**
     * 根据主键查询分享关系
     *
     * @param id 主键ID
     * @return 分享关系记录
     */
    UserShareRelation selectByPrimaryKey(Integer id);

    /**
     * 根据分享者用户ID查询分享关系列表
     *
     * @param shareUserId 分享者用户ID
     * @return 分享关系列表
     */
    List<UserShareRelation> selectByShareUserId(Integer shareUserId);

    /**
     * 根据被分享者用户ID查询分享关系
     *
     * @param sharedUserId 被分享者用户ID
     * @return 分享关系记录
     */
    UserShareRelation selectBySharedUserId(Integer sharedUserId);

    /**
     * 根据分享者用户ID统计分享次数
     *
     * @param shareUserId 分享者用户ID
     * @return 分享次数
     */
    int countByShareUserId(Integer shareUserId);

    /**
     * 根据分享者用户ID统计新用户分享次数
     *
     * @param shareUserId 分享者用户ID
     * @return 新用户分享次数
     */
    int countNewUserByShareUserId(Integer shareUserId);

    /**
     * 根据分享者用户ID统计老用户分享次数
     *
     * @param shareUserId 分享者用户ID
     * @return 老用户分享次数
     */
    int countOldUserByShareUserId(Integer shareUserId);
}
