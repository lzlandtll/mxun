package com.mxun.sys.feign;

import com.mxun.common.dto.SmsCodeDTO;
import com.mxun.common.resultView.ResultView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/3/4
 */
@FeignClient(name = "mxun-third-party")
public interface ThirdPartyFeignService {

    @PostMapping("/sms/sendCode")
    ResultView sendCode(@RequestBody SmsCodeDTO smsCodeDTO);

    @PostMapping("/sms/verifyCode")
    ResultView verifyCode(@RequestBody SmsCodeDTO smsCodeDTO);
}
