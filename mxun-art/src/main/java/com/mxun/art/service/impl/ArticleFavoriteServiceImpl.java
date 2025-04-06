package com.mxun.art.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.art.entity.ArticleFavorite;
import com.mxun.art.mapper.ArticleFavoriteMapper;
import com.mxun.art.service.ArticleFavoriteService;
import org.springframework.stereotype.Service;

/**
 * 文章收藏表 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@Service
public class ArticleFavoriteServiceImpl extends ServiceImpl<ArticleFavoriteMapper, ArticleFavorite> implements ArticleFavoriteService {

}
