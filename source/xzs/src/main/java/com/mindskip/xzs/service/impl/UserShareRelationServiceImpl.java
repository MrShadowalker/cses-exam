package com.mindskip.xzs.service.impl;

import com.mindskip.xzs.domain.entity.UserShareRelation;
import com.mindskip.xzs.repository.UserShareRelationMapper;
import com.mindskip.xzs.service.UserShareRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户分享关系服务实现类
 */
@Service
public class UserShareRelationServiceImpl implements UserShareRelationService {

    private static final Logger logger = LoggerFactory.getLogger(UserShareRelationServiceImpl.class);

    private final UserShareRelationMapper userShareRelationMapper;

    @Autowired
    public UserShareRelationServiceImpl(UserShareRelationMapper userShareRelationMapper) {
        this.userShareRelationMapper = userShareRelationMapper;
    }

    @Override
    public UserShareRelation createShareRelation(Integer shareUserId, Integer sharedUserId, String shareScene, boolean isNewUser) {
        if (shareUserId == null || sharedUserId == null) {
            logger.warn("创建分享关系失败：分享者用户ID或被分享者用户ID为空");
            return null;
        }

        // 检查是否已存在分享关系，避免重复创建
        UserShareRelation existingRelation = userShareRelationMapper.selectBySharedUserId(sharedUserId);
        if (existingRelation != null) {
            logger.info("用户 {} 已存在分享关系，不重复创建", sharedUserId);
            return existingRelation;
        }

        UserShareRelation shareRelation = new UserShareRelation();
        shareRelation.setShareUserId(shareUserId);
        shareRelation.setSharedUserId(sharedUserId);
        shareRelation.setSharedUserType(isNewUser ? "new_user" : "old_user");
        shareRelation.setShareScene(shareScene);
        shareRelation.setCreateTime(new Date());

        try {
            userShareRelationMapper.insert(shareRelation);
            logger.info("创建分享关系成功：分享者用户ID={}, 被分享者用户ID={}, 用户类型={}", 
                       shareUserId, sharedUserId, shareRelation.getSharedUserType());
            return shareRelation;
        } catch (Exception e) {
            logger.error("创建分享关系失败：分享者用户ID={}, 被分享者用户ID={}", shareUserId, sharedUserId, e);
            return null;
        }
    }

    @Override
    public UserShareRelation getShareRelationBySharedUserId(Integer sharedUserId) {
        if (sharedUserId == null) {
            return null;
        }
        return userShareRelationMapper.selectBySharedUserId(sharedUserId);
    }

    @Override
    public List<UserShareRelation> getShareRelationsByShareUserId(Integer shareUserId) {
        if (shareUserId == null) {
            return null;
        }
        return userShareRelationMapper.selectByShareUserId(shareUserId);
    }

    @Override
    public int countSharesByUserId(Integer shareUserId) {
        if (shareUserId == null) {
            return 0;
        }
        return userShareRelationMapper.countByShareUserId(shareUserId);
    }

    @Override
    public int countNewUserSharesByUserId(Integer shareUserId) {
        if (shareUserId == null) {
            return 0;
        }
        return userShareRelationMapper.countNewUserByShareUserId(shareUserId);
    }

    @Override
    public int countOldUserSharesByUserId(Integer shareUserId) {
        if (shareUserId == null) {
            return 0;
        }
        return userShareRelationMapper.countOldUserByShareUserId(shareUserId);
    }
}
