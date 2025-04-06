package com.mxun.art.service.impl;

import com.mxun.art.entity.Article;
import com.mxun.art.entity.Tag;
import com.mxun.art.service.ArticleService;
import com.mxun.art.service.TagService;
import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.art.entity.ArticleTag;
import com.mxun.art.mapper.ArticleTagMapper;
import com.mxun.art.service.ArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 文章标签关联表 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    /**
     * @Description:
     *      1.首先对数据进行校验
     *      2.针对已经没有使用的标签需要进行删除
     *      3.数据库已存在的标签需要从待新增标签里面进行删除
     *      4.保存新增文章标签关联数据
     * @Author: liuzhilin
     * @Date: 2025/4/1 21:25
     */
    @Transactional
    @Override
    public void saveArticleTag(Long articleId, List<Long> rawTagIds) {
        // 校验传入数据是否为空
        if(articleId == null || rawTagIds == null || rawTagIds.size() == 0){
            return;
        }

        // 去重
        List<Long> tagIdList = rawTagIds.stream().distinct().collect(Collectors.toList());

        Article article = articleService.getById(articleId);
        if(Objects.isNull(article)){
            // 检验文章是否存在
            throw new BusinessException(ErrorEnum.ART_ARTICLE_NOT_EXIST_ERROR);
        }

        List<Tag> tagList = tagService.listByIds(tagIdList);
        if(CollectionUtils.isEmpty(tagList) || tagList.size() != tagIdList.size()){
            // 检验标签是否存在
            throw new BusinessException(ErrorEnum.ART_TAG_NOT_EXIST_ERROR);
        }

        QueryWrapper query = this.query();
        query.eq(ArticleTag::getArticleId, articleId);
        query.notIn(ArticleTag::getTagId, tagIdList);
        // 删除去掉了的标签
        this.remove(query);

        query = this.query();
        query.eq(ArticleTag::getArticleId, articleId);
        query.in(ArticleTag::getTagId, tagIdList);

        List<ArticleTag> articleTagListDb = this.list(query);
        if(!CollectionUtils.isEmpty(articleTagListDb)){
            // 针对数据存在的文章标签关联数据不需要再次新增
            List<Long> tagIdListDb = articleTagListDb.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
            tagIdList.removeAll(tagIdListDb);
        }
        if(CollectionUtils.isEmpty(tagIdList)){
            return;
        }
        List<ArticleTag> articleTagList = tagIdList.stream().map(tagId -> {
            ArticleTag articleTag = ArticleTag.create()
                    .setArticleId(articleId)
                    .setTagId(tagId);
            return articleTag;
        }).collect(Collectors.toList());
        // 保存新增文章标签关联数据
        this.saveBatch(articleTagList);
    }

    /**
     * @Description: 根据文章查找tag集合
     * @Author: liuzhilin
     * @Date: 2025/4/2 19:41
     */
    @Override
    public List<Tag> getListByArticleId(Long articleId) {
        QueryWrapper query = this.query();
        query.eq(ArticleTag::getArticleId, articleId);

        List<ArticleTag> articleTagList = list(query);
        if(CollectionUtils.isEmpty(articleTagList)){
            return List.of();
        }
        List<Long> tagIds = articleTagList.stream()
                .map(articleTag -> articleTag.getTagId())
                .collect(Collectors.toList());
        return tagService.listByIds(tagIds);
    }
}
