package com.mxun.art.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.art.entity.ArticleLike;
import com.mxun.art.mapper.ArticleLikeMapper;
import com.mxun.art.service.ArticleLikeService;
import org.springframework.stereotype.Service;

/**
 * 文章点赞表 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@Service
public class ArticleLikeServiceImpl extends ServiceImpl<ArticleLikeMapper, ArticleLike> implements ArticleLikeService {

}
