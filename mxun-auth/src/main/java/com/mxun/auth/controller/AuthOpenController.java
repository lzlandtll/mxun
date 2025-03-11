package com.mxun.auth.controller;

import com.mxun.auth.entity.User;
import com.mxun.auth.service.UserService;
import com.mxun.auth.vo.UserVO;
import com.mxun.common.enums.UserTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @Description: 认证服务开发接口
 * @Author: liuzhilin
 * @Date: 2025/3/2
 */
@RestController
@RequestMapping("/open")
public class AuthOpenController {
    @Autowired
    private UserService userService;

    /**
     * @Description: 普通用户登录
     * @Author: liuzhilin
     * @Date: 2025/3/1 19:13
     */
    @PostMapping("/login")
    public UserVO login(@RequestBody User userDTO) {
        ArrayList<String> userTypeList = new ArrayList<String>();
        userTypeList.add(UserTypeEnum.COMMON_USER.getUserType());
        return userService.commonUserLogin(userDTO, userTypeList);
    }

    /**
     * @Description: 管理员用户登录
     * @Author: liuzhilin
     * @Date: 2025/3/1 19:13
     */
    @PostMapping("/adminLogin")
    public UserVO adminLogin(@RequestBody User userDTO) {
        ArrayList<String> userTypeList = new ArrayList<String>();
        userTypeList.add(UserTypeEnum.ADMIN.getUserType());
        userTypeList.add(UserTypeEnum.SUPER_ADMIN.getUserType());
        return userService.commonUserLogin(userDTO, userTypeList);
    }

    /**
     * @Description: 三方账号登录
     * @Author: liuzhilin
     * @Date: 2025/3/1 19:13
     */
    @PostMapping("/thirdLogin")
    public String thirdLogin(@RequestBody User userDTO) {
        ArrayList<String> userTypeList = new ArrayList<String>();
        userTypeList.add(UserTypeEnum.THIRD_USER.getUserType());
        return userService.commonUserLogin(userDTO, userTypeList).getToken();
    }
}
