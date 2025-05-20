package com.mxun.art.controller;

import com.mybatisflex.core.paginate.Page;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.mxun.art.entity.ArticleLike;
import com.mxun.art.service.ArticleLikeService;

import java.util.List;

/**
 * 文章点赞表 控制层。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@RestController
@RequestMapping("/api/articleLike")
public class ArticleLikeController {

    @Autowired
    private ArticleLikeService articleLikeService;

    @GetMapping("onLike")
    public ArticleLike onLike(@RequestParam("articleId") @NotNull(message = "05010") Long articleId) {
        return articleLikeService.onLike(articleId);
    }

    @GetMapping("cancelLike")
    public void cancelLike(@RequestParam("articleId") @NotNull(message = "05010") Long articleId) {
        articleLikeService.cancelLike(articleId);
    }
}
