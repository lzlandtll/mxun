package com.mxun.sys.controller;

import com.mxun.sys.dto.UserDTO;
import com.mxun.sys.feign.ThirdPartyFeignService;
import com.mxun.sys.service.UserService;
import com.mxun.common.annotation.IpLimitRequest;
import com.mxun.sys.stream.KafkaProducerService;
import com.mxun.sys.vo.PublicUserVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/3/2
 */
@Validated
@RestController
@RequestMapping("/open")
public class SysOpenController {


    @Autowired
    private UserService userService;

    @Autowired
    private ThirdPartyFeignService thirdPartyFeignService;

    @Autowired
    private KafkaProducerService kafkaProducerService;


    @GetMapping("productKafkaMessage")
    public void contextLoads() {
        kafkaProducerService.sendMessage("test-topic", "1", "test123...");
    }

    @IpLimitRequest(path = "/admin/open/getRegisterSmsCode", limit = 100, expireSecond = 300)
    @GetMapping("getRegisterSmsCode")
    public void getRegisterSmsCode(@RequestParam("tel") @Pattern(regexp = "^1[3-9]\\d{9}$", message = "00003") String tel) {
        userService.getRegisterSmsCode(tel);
    }

    @PostMapping("registerAccount")
    public void registerAccount(@Valid @RequestBody UserDTO userDTO) {
        userService.registerAccount(userDTO);
    }

    @GetMapping("getUserById")
    public PublicUserVO getUserById(@RequestParam("userId") @NotNull(message = "01013") Long userId) {
        return userService.getUserById(userId);
    }
}
