package com.mxun.auth.feign;

import com.mxun.common.resultView.ResultView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description: admin feign
 * @Author: liuzhilin
 * @Date: 2025/3/8
 */
@FeignClient(name = "mxun-sys")
public interface AdminFeignService {
    @GetMapping("/roleUser/getRolesByUserId")
    ResultView<List<Long>> getRolesByUserId(@RequestParam("userId") Long userId);
}
