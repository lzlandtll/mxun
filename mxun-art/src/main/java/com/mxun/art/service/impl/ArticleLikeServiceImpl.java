package com.mxun.art.service.impl;

import com.mxun.art.entity.Article;
import com.mxun.art.feign.MemFeignService;
import com.mxun.art.service.ArticleService;
import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.utils.UserUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.art.entity.ArticleLike;
import com.mxun.art.mapper.ArticleLikeMapper;
import com.mxun.art.service.ArticleLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 文章点赞表 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@Service
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper, ArticleLike> implements ArticleLikeService {

    @Lazy
    @Autowired
    private ArticleService articleService;

    @Autowired
    private MemFeignService memFeignService;


    @Override
    public ArticleLike onLike(Long articleId) {
        Article articleDb = articleService.getById(articleId);
        if(Objects.isNull(articleDb)){
            throw new BusinessException(ErrorEnum.ART_ARTICLE_NOT_EXIST_ERROR);
        }

        QueryWrapper query = this.query();
        query.eq(ArticleLike::getArticleId, articleId);
        query.eq(ArticleLike::getLikeUserId, UserUtil.getUserId());
        ArticleLike articleLikeDb = getOne(query);
        if(!Objects.isNull(articleLikeDb)){
            throw new BusinessException(ErrorEnum.ART_ALREADY_LIKE_ERROR);
        }

        ArticleLike articleLike = new ArticleLike();
        articleLike.setArticleId(articleId);
        articleLike.setLikeUserId(UserUtil.getUserId());
        save(articleLike);

        articleService.incrLikeCount(articleId);
        memFeignService.incrArticleLike(UserUtil.getUserId());
        return articleLike;
    }


    @Override
    public void cancelLike(Long articleId) {
        QueryWrapper query = this.query();
        query.eq(ArticleLike::getArticleId, articleId);
        query.eq(ArticleLike::getLikeUserId, UserUtil.getUserId());
        remove(query);

        articleService.decrLikeCount(articleId);
        memFeignService.decrArticleLike(UserUtil.getUserId());
    }

    @Override
    public Boolean hasLiked(Long articleId) {
        if(Objects.isNull(articleId)){
            throw new BusinessException(ErrorEnum.ART_ARTICLE_NOT_EXIST_ERROR);
        }
        if(!UserUtil.hasLogin()){
            return false;
        }
        QueryWrapper query = this.query();
        query.eq(ArticleLike::getArticleId, articleId);
        query.eq(ArticleLike::getLikeUserId, UserUtil.getUserId());
        return exists(query);
    }
}
