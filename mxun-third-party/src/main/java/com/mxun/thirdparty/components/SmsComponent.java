package com.mxun.thirdparty.components;


import com.mxun.common.constant.RedisConstant;
import com.mxun.common.dto.SmsCodeDTO;
import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.utils.HttpUtils;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 验证码组件
 * @Author: liuzhilin
 * @Date: 2024/6/2
 */
@Data
@ConfigurationProperties(prefix = "spring.cloud.alicloud.sms")
@Component
public class SmsComponent {

    private String host;
    private String path;
    private String appcode;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * @Description: 发送验证码
     * @Author: liuzhilin
     * @Date: 2025/3/9 10:25
     */
    public void sendSmsCode(SmsCodeDTO smsCodeDTO) throws Exception {
        // 先判断是否发送过验证码
        String smsKey = String.join(":", RedisConstant.SMS_PREFIX, smsCodeDTO.getType(), smsCodeDTO.getTel());
        RBucket<String> smsBucket = redissonClient.getBucket(smsKey);
        if(Objects.nonNull(smsBucket) && Objects.nonNull(smsBucket.get())){
            throw new BusinessException(ErrorEnum.THIRD_SMS_NOT_EXPIRE_ERROR);
        }

        // 将验证码存储到redis中
        smsBucket.set(smsCodeDTO.getCode(), smsCodeDTO.getExpireSecond(), TimeUnit.SECONDS);

        // 通过阿里云短信服务发送短信
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:" + smsCodeDTO.getCode());
        bodys.put("template_id", smsCodeDTO.getTemplateId());
        bodys.put("phone_number", smsCodeDTO.getTel());
        HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
        System.out.println(response.toString());
        if(response.getStatusLine().getStatusCode() != 200){
            throw new BusinessException(ErrorEnum.THIRD_SMS_SEND_ERROR);
        }
    }

    /**
     * @Description: 验证验证码
     * @Author: liuzhilin
     * @Date: 2025/3/9 10:27
     */
    public void verifyCode(SmsCodeDTO smsCodeDTO) {
        String smsKey = String.join(":", RedisConstant.SMS_PREFIX, smsCodeDTO.getType(), smsCodeDTO.getTel());
        RBucket<String> smsBucket = redissonClient.getBucket(smsKey);
        if(Objects.isNull(smsBucket) || Objects.isNull(smsBucket.get())){
            throw new BusinessException(ErrorEnum.THIRD_SMS_NOT_EXIST_ERROR);
        }
        if(!Objects.equals(smsCodeDTO.getCode(), smsBucket.get())){
            throw new BusinessException(ErrorEnum.THIRD_SMS_CODE_VERIFY_ERROR);
        }
        smsBucket.delete();
    }
}
