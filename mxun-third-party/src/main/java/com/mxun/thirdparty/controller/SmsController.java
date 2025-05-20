package com.mxun.thirdparty.controller;

import com.mxun.thirdparty.components.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 短信服务控制层,通过其他服务调用该接口
 * @Author: liuzhilin
 * @Date: 2024/6/2
 */
@RestController
@RequestMapping("/api/sms")
public class SmsController {

    @Autowired
    private SmsComponent smsComponent;

}
