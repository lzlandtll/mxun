package com.mxun.thirdparty.controller;

import com.mxun.common.dto.SmsCodeDTO;
import com.mxun.thirdparty.components.SmsComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/7
 */
@RestController
@RequestMapping("/internal")
public class ThirdInternalController {
    @Autowired
    private SmsComponent smsComponent;

    /**
     * @Description: 请求发送验证码
     * @Author: liuzhilin
     * @Date: 2025/3/9 10:23
     * @param smsCodeDTO
     */
    @PostMapping("/sms/sendCode")
    public void sendCode(@RequestBody SmsCodeDTO smsCodeDTO) throws Exception {
        smsComponent.sendSmsCode(smsCodeDTO);
    }

    /**
     * @Description: 其他服务验证验证码,不存在则会抛出异常
     * @Author: liuzhilin
     * @Date: 2025/3/9 10:24
     */
    @PostMapping("/sms/verifyCode")
    public void verifyCode(@RequestBody SmsCodeDTO smsCodeDTO) {
        smsComponent.verifyCode(smsCodeDTO);
    }
}
