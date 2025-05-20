package com.mxun.art.controller;

import com.mxun.art.entity.ArticleLike;
import com.mybatisflex.core.paginate.Page;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.mxun.art.entity.ArticleFavorite;
import com.mxun.art.service.ArticleFavoriteService;

import java.util.List;

/**
 * 文章收藏表 控制层。
 * @author moxuan
 * @since 2025-03-30
 */
@RestController
@RequestMapping("/api/articleFavorite")
public class ArticleFavoriteController {

    @Autowired
    private ArticleFavoriteService articleCollectionService;

    @GetMapping("onFavorite")
    public ArticleFavorite onFavorite(@RequestParam("articleId") @NotNull(message = "05010") Long articleId) {
        return articleCollectionService.onFavorite(articleId);
    }

    @GetMapping("cancelFavorite")
    public void cancelFavorite(@RequestParam("articleId") @NotNull(message = "05010") Long articleId) {
        articleCollectionService.cancelFavorite(articleId);
    }
}
