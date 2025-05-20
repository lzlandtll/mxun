package com.mxun.art.feign;

import com.mxun.common.resultView.ResultView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/10
 */
@FeignClient(name = "mxun-mem")
public interface MemFeignService {
    @GetMapping("/api/userFollow/hasFollow")
    ResultView<Boolean> hasFollow(@RequestParam("followedUserId") Long followedUserId);

    @GetMapping("/internal/incrArticle")
    ResultView incrArticle(@RequestParam("userId") Long userId);

    @GetMapping("/internal/incrArticleLike")
    ResultView incrArticleLike(@RequestParam("userId") Long userId);

    @GetMapping("/internal/decrArticleLike")
    ResultView decrArticleLike(@RequestParam("userId") Long userId);

    @GetMapping("/internal/incrArticleCollection")
    ResultView incrArticleCollection(@RequestParam("userId") Long userId);

    @GetMapping("/internal/decrArticleCollection")
    ResultView decrArticleCollection(@RequestParam("userId") Long userId);
}
