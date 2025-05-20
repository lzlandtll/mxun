package com.mxun.sys.controller;

import com.mxun.sys.service.UserService;
import com.mxun.sys.vo.PersonalUserVO;
import com.mxun.sys.vo.PublicUserVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户基本信息表 控制层。
 *
 * @author moxuan
 * @since 2025-03-01
 */
@Validated
@RestController
@RequestMapping("/api/user")
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

    @GetMapping("getPersonalUserInfo")
    public PersonalUserVO getUserById() {
        return userService.getPersonalUserInfo();
    }

    @PostMapping("savePersonalUserInfo")
    public void savePersonalUserInfo(@Valid @RequestBody PersonalUserVO personalUserVO) {
        userService.savePersonalUserInfo(personalUserVO);
    }
}
