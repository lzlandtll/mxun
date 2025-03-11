package com.mxun.thirdparty.controller;

import com.mxun.common.dto.SmsCodeDTO;
import com.mxun.thirdparty.components.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 短信服务控制层,通过其他服务调用该接口
 * @Author: liuzhilin
 * @Date: 2024/6/2
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private SmsComponent smsComponent;

    /**
     * @Description: 请求发送验证码
     * @Author: liuzhilin
     * @Date: 2025/3/9 10:23
     * @param smsCodeDTO
     */
    @PostMapping("/sendCode")
    public void sendCode(@RequestBody SmsCodeDTO smsCodeDTO) throws Exception {
        smsComponent.sendSmsCode(smsCodeDTO);
    }

    /**
     * @Description: 其他服务验证验证码,不存在则会抛出异常
     * @Author: liuzhilin
     * @Date: 2025/3/9 10:24
     */
    @PostMapping("/verifyCode")
    public void verifyCode(@RequestBody SmsCodeDTO smsCodeDTO) {
        smsComponent.verifyCode(smsCodeDTO);
    }
}
