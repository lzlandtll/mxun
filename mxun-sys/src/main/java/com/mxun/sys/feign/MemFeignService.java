package com.mxun.sys.feign;

import com.mxun.common.config.FeignConfig;
import com.mxun.common.resultView.ResultView;
import com.mxun.sys.dto.SyncUserDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/5
 */
@FeignClient(name = "mxun-mem", configuration = FeignConfig.class)
public interface MemFeignService {
    @PostMapping("/internal/syncSysUser")
    ResultView syncSysUser(@Valid @RequestBody SyncUserDTO syncUserDTO);
}
