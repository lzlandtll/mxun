package com.mxun.art.service;

import com.mybatisflex.core.service.IService;
import com.mxun.art.entity.ArticleLike;

/**
 * 文章点赞表 服务层。
 *
 * @author moxuan
 * @since 2025-03-30
 */
public interface ArticleLikeService extends IService<ArticleLike> {

    ArticleLike onLike(Long articleId);

    void cancelLike(Long articleId);

    Boolean hasLiked(Long articleId);
}
