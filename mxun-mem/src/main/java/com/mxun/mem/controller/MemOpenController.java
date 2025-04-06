package com.mxun.mem.controller;

import com.mxun.mem.service.UserService;
import com.mxun.mem.vo.UserVO;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/4
 */
@Validated
@RestController
@RequestMapping("/open")
public class MemOpenController {

    @Autowired
    private UserService userService;


    @GetMapping("getOpenUserInfo")
    public UserVO getOpenUserInfo(@RequestParam("userId") @NotNull(message = "01013") Long userId) {
        return userService.getOpenUserInfo(userId);
    }
}
