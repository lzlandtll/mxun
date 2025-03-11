package com.mxun.chatai.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description: 测试接口,目前没有实际作用
 * @Author: liuzhilin
 * @Date: 2025/2/23
 */
@RestController
@RequestMapping("/open")
public class ChatAiOpenController {

    @RequestMapping("/getOpenData")
    public String getChatAi(@RequestBody Map map) {
        System.out.println("open data");
        return "open data";
    }

}
