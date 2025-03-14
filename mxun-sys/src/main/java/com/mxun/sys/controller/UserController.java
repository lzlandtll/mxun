package com.mxun.sys.controller;

import com.mxun.sys.entity.User;
import com.mxun.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户基本信息表 控制层。
 *
 * @author moxuan
 * @since 2025-03-01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("addAiKey")
    public void addAiKey(@RequestParam("aiKey") String aiKey) {
        userService.addAiKey(aiKey);
    }

    @GetMapping("removeAiKey")
    public void removeAiKey() {
        userService.removeAiKey();
    }
}
