package com.mxun.mem.service;

import com.mxun.mem.dto.SyncUserDTO;
import com.mxun.mem.vo.UserVO;
import com.mybatisflex.core.service.IService;
import com.mxun.mem.entity.User;

/**
 *  服务层。
 *
 * @author moxuan
 * @since 2025-04-04
 */
public interface UserService extends IService<User> {

    UserVO getOpenUserInfo(Long userId);

    void syncSysUser(SyncUserDTO syncUserDTO);

    void incrArticle(Long userId);

    void incrFollow(Long userId);

    void decrFollow(Long userId);

    void incrArticleLike(Long userId);

    void decrArticleLike(Long userId);

    void incrArticleCollection(Long userId);

    void decrArticleCollection(Long userId);
}
