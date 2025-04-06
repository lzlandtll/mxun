package com.mxun.art.service;

import com.mxun.art.entity.Tag;
import com.mybatisflex.core.service.IService;
import com.mxun.art.entity.ArticleTag;

import java.util.List;

/**
 * 文章标签关联表 服务层。
 *
 * @author moxuan
 * @since 2025-03-30
 */
public interface ArticleTagService extends IService<ArticleTag> {
    void saveArticleTag(Long articleId, List<Long> rawTagIds);


    // 根据文章查找tag集合
    List<Tag> getListByArticleId(Long articleId);
}
