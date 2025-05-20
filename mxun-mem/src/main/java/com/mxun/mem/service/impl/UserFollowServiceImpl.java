package com.mxun.mem.service.impl;

import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.utils.UserUtil;
import com.mxun.mem.service.UserService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.mem.entity.UserFollow;
import com.mxun.mem.mapper.UserFollowMapper;
import com.mxun.mem.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 用户关注关系表 服务层实现。
 *
 * @author moxuan
 * @since 2025-04-07
 */
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements UserFollowService {

    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public void follow(Long userId) {
        QueryWrapper query = this.query();
        query.eq(UserFollow::getFollowerUserId, UserUtil.getUserId());
        query.eq(UserFollow::getFollowedUserId, userId);
        UserFollow userFollowDb = getOne(query);
        if(!Objects.isNull(userFollowDb)){
            throw new BusinessException(ErrorEnum.USER_FOLLOWED_ERROR);
        }
        UserFollow userFollow = new UserFollow();
        userFollow.setFollowerUserId(UserUtil.getUserId());
        userFollow.setFollowedUserId(userId);
        save(userFollow);

        userService.incrFollow(userId);
    }

    @Override
    public void cancelFollow(Long userId) {

        QueryWrapper query = this.query();
        query.eq(UserFollow::getFollowerUserId, UserUtil.getUserId());
        query.eq(UserFollow::getFollowedUserId, userId);

        remove(query);

        userService.decrFollow(userId);
    }


    @Override
    public Boolean hasFollow(Long followedUserId) {
        System.out.println(UserUtil.getUserId());

        QueryWrapper query = this.query();
        query.eq(UserFollow::getFollowerUserId, UserUtil.getUserId());
        query.eq(UserFollow::getFollowedUserId, followedUserId);
        if(exists(query)){
            return true;
        }

        return false;
    }
}
