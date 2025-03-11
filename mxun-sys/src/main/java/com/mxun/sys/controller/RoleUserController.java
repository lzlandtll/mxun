package com.mxun.sys.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.mxun.sys.entity.RoleUser;
import com.mxun.sys.service.RoleUserService;

import java.util.List;

/**
 * @Description: 角色用户控制层
 * @Author: liuzhilin
 * @Date: 2025/3/9 18:01
 */
@RestController
@RequestMapping("/roleUser")
public class RoleUserController {

    @Autowired
    private RoleUserService roleUserService;


    /**
     * @Description: 根据用户编码获取角色列表
     * @Author: liuzhilin
     * @Date: 2025/3/9 18:02
     */
    @GetMapping("getRolesByUserId")
    public List<Long> getRolesByUserId(@RequestParam("userId") Long userId) {
        return roleUserService.getRolesByUserId(userId);
    }

}
