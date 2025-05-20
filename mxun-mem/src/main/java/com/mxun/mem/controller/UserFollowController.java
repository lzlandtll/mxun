package com.mxun.mem.controller;

import com.mybatisflex.core.paginate.Page;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.mxun.mem.entity.UserFollow;
import com.mxun.mem.service.UserFollowService;

import java.util.List;

/**
 * 用户关注关系表 控制层。
 *
 * @author moxuan
 * @since 2025-04-07
 */
@RestController
@RequestMapping("/api/userFollow")
public class UserFollowController {

    @Autowired
    private UserFollowService userFollowService;



    @GetMapping("follow")
    public void follow(@RequestParam("userId") @NotNull(message = "01013") Long userId) {
        userFollowService.follow(userId);
    }

    @GetMapping("cancelFollow")
    public void cancelFollow(@RequestParam("userId") @NotNull(message = "01013") Long userId) {
        userFollowService.cancelFollow(userId);
    }

    @GetMapping("hasFollow")
    public Boolean hasFollow(@RequestParam("followedUserId") @NotNull(message = "01013") Long followedUserId) {
        return userFollowService.hasFollow(followedUserId);
    }


}
