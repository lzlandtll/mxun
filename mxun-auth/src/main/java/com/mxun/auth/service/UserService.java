package com.mxun.auth.service;

import com.mxun.auth.vo.UserVO;
import com.mybatisflex.core.service.IService;
import com.mxun.auth.entity.User;

import java.util.ArrayList;

/**
 * 用户基本信息表 服务层。
 *
 * @author moxuan
 * @since 2025-03-01
 */
public interface UserService extends IService<User> {

    // 用户登录方法
    UserVO commonUserLogin(User userDTO, ArrayList<String> userTypeList);
}
