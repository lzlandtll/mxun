package com.mxun.mem.service;

import com.mybatisflex.core.service.IService;
import com.mxun.mem.entity.UserFollow;

/**
 * 用户关注关系表 服务层。
 *
 * @author moxuan
 * @since 2025-04-07
 */
public interface UserFollowService extends IService<UserFollow> {

    void follow(Long userId);

    void cancelFollow(Long userId);

    Boolean hasFollow(Long followedUserId);
}
