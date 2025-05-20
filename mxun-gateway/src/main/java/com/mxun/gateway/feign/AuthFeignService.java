package com.mxun.gateway.feign;

import com.mxun.common.dto.ThirdUserInfo;
import com.mxun.common.resultView.ResultView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description: 系统对接的时候需要对对用户信息进行验证
 * @Author: liuzhilin
 * @Date: 2025/3/1
 */
@FeignClient(name = "mxun-auth")
public interface AuthFeignService {
    @PostMapping("/api/user/thirdLogin")
    ResultView thirdLogin(@RequestBody ThirdUserInfo userDTO);
}
