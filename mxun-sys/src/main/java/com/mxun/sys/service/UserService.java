package com.mxun.sys.service;


import com.mxun.sys.dto.UserDTO;
import com.mxun.sys.entity.User;
import com.mybatisflex.core.service.IService;

/**
 * 用户基本信息表 服务层。
 *
 * @author moxuan
 * @since 2025-03-01
 */
public interface UserService extends IService<User> {
    void getRegisterSmsCode( String tel);

    void registerAccount(UserDTO userDTO);

    Long addAiKey(String aiKey);

    Long removeAiKey();
}
