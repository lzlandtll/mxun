package com.mxun.art.service.impl;

import com.mxun.art.entity.Article;
import com.mxun.art.entity.ArticleLike;
import com.mxun.art.feign.MemFeignService;
import com.mxun.art.service.ArticleService;
import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.utils.UserUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.art.entity.ArticleFavorite;
import com.mxun.art.mapper.ArticleFavoriteMapper;
import com.mxun.art.service.ArticleFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 文章收藏表 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@Service
public class ArticleFavoriteServiceImpl extends ServiceImpl<ArticleFavoriteMapper, ArticleFavorite> implements ArticleFavoriteService {

    @Lazy
    @Autowired
    private ArticleService articleService;

    @Autowired
    private MemFeignService memFeignService;

    @Override
    public ArticleFavorite onFavorite(Long articleId) {
        Article articleDb = articleService.getById(articleId);
        if(Objects.isNull(articleDb)){
            throw new BusinessException(ErrorEnum.ART_ARTICLE_NOT_EXIST_ERROR);
        }

        QueryWrapper query = this.query();
        query.eq(ArticleFavorite::getArticleId, articleId);
        query.eq(ArticleFavorite::getFavoriteUserId, UserUtil.getUserId());
        ArticleFavorite articleFavoriteDb = getOne(query);
        if(!Objects.isNull(articleFavoriteDb)){
            throw new BusinessException(ErrorEnum.ART_ALREADY_FAVORITE_ERROR);
        }

        ArticleFavorite articleFavorite = new ArticleFavorite();
        articleFavorite.setArticleId(articleId);
        articleFavorite.setFavoriteUserId(UserUtil.getUserId());
        save(articleFavorite);


        articleService.incrCollectionCount(articleId);
        memFeignService.incrArticleCollection(articleDb.getAuthorId());
        System.out.println(articleDb);
        return articleFavorite;
    }

    @Override
    public void cancelFavorite(Long articleId) {
        Article articleDb = articleService.getById(articleId);

        QueryWrapper query = this.query();
        query.eq(ArticleFavorite::getArticleId, articleId);
        query.eq(ArticleFavorite::getFavoriteUserId, UserUtil.getUserId());
        remove(query);

        articleService.decrCollectionCount(articleId);
        memFeignService.decrArticleCollection(articleDb.getAuthorId());
        System.out.println(articleDb);
    }

    @Override
    public Boolean hasFavorite(Long articleId) {
        if(Objects.isNull(articleId)){
            throw new BusinessException(ErrorEnum.ART_ARTICLE_NOT_EXIST_ERROR);
        }
        if(!UserUtil.hasLogin()){
            return false;
        }
        QueryWrapper query = this.query();
        query.eq(ArticleFavorite::getArticleId, articleId);
        query.eq(ArticleFavorite::getFavoriteUserId, UserUtil.getUserId());
        return exists(query);
    }
}
