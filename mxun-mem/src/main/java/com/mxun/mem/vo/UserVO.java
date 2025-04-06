package com.mxun.mem.vo;

import com.mxun.mem.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/4
 */
@NoArgsConstructor
@Data
public class UserVO extends User {

    private String username;
    private String summary;
}
