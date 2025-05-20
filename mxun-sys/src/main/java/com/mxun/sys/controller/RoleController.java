package com.mxun.sys.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.mxun.sys.entity.Role;
import com.mxun.sys.service.RoleService;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @Description: 目前没有用
 * @Author: liuzhilin
 * @Date: 2025/3/9 18:01
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

}
