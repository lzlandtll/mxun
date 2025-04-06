package com.mxun.mem.controller;

import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.mem.dto.SyncUserDTO;
import com.mxun.mem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/5
 */
@Validated
@RestController
@RequestMapping("/internal")
public class MemInternalController {

    @Autowired
    private UserService userService;

    private int count = 1;

    /**
     * @Description: 同步sys模块注册的用户信息
     * @Author: liuzhilin
     * @Date: 2025/4/5 17:41
     */
    @PostMapping("syncSysUser")
    public void syncSysUser(@Valid @RequestBody SyncUserDTO syncUserDTO) {
        userService.syncSysUser(syncUserDTO);
    }
}
