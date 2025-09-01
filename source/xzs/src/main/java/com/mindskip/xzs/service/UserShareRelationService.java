package com.mindskip.xzs.service;

import com.mindskip.xzs.domain.entity.UserShareRelation;

import java.util.List;

/**
 * 用户分享关系服务接口
 */
public interface UserShareRelationService {

    /**
     * 创建分享关系记录
     *
     * @param shareUserId 分享者用户ID
     * @param sharedUserId 被分享者用户ID
     * @param shareScene 分享场景
     * @param isNewUser 被分享者是否为新用户
     * @return 分享关系记录
     */
    UserShareRelation createShareRelation(Integer shareUserId, Integer sharedUserId, String shareScene, boolean isNewUser);

    /**
     * 根据被分享者用户ID查询分享关系
     *
     * @param sharedUserId 被分享者用户ID
     * @return 分享关系记录，如果不存在返回null
     */
    UserShareRelation getShareRelationBySharedUserId(Integer sharedUserId);

    /**
     * 根据分享者用户ID查询分享关系列表
     *
     * @param shareUserId 分享者用户ID
     * @return 分享关系列表
     */
    List<UserShareRelation> getShareRelationsByShareUserId(Integer shareUserId);

    /**
     * 统计分享者的分享次数
     *
     * @param shareUserId 分享者用户ID
     * @return 分享次数
     */
    int countSharesByUserId(Integer shareUserId);

    /**
     * 统计分享者的新用户分享次数
     *
     * @param shareUserId 分享者用户ID
     * @return 新用户分享次数
     */
    int countNewUserSharesByUserId(Integer shareUserId);

    /**
     * 统计分享者的老用户分享次数
     *
     * @param shareUserId 分享者用户ID
     * @return 老用户分享次数
     */
    int countOldUserSharesByUserId(Integer shareUserId);
}
