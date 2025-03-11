package com.mxun.sys.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.mxun.sys.service.ApiResourceService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 目前没有用
 * @Author: liuzhilin
 * @Date: 2025/3/9 18:00
 */
@RestController
@RequestMapping("/apiResource")
public class ApiResourceController {

//    @Autowired
    private ApiResourceService apiResourceService;

}
