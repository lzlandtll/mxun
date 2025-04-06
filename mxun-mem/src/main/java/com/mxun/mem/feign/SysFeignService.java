package com.mxun.mem.feign;

import com.mxun.common.resultView.ResultView;
import com.mxun.mem.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/2
 */
@FeignClient("mxun-sys")
public interface SysFeignService {
    @GetMapping("/open/getUserById")
    ResultView<UserDTO> getUserById(@RequestParam("userId") Long userId);
}
