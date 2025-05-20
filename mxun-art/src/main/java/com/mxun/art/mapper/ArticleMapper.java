package com.mxun.art.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mxun.art.entity.Article;
import org.apache.ibatis.annotations.Update;

/**
 * 文章表,存储已经发布成功的文章信息 映射层。
 *
 * @author moxuan
 * @since 2025-03-30
 */
public interface ArticleMapper extends BaseMapper<Article> {

    @Update("update art_article set like_count = like_count + 1 where id = #{articleId}")
    boolean incrLikeCount(Long articleId);

    @Update("update art_article set like_count = like_count - 1 where id = #{articleId}")
    boolean decrLikeCount(Long articleId);

    @Update("update art_article set collection_count = collection_count + 1 where id = #{articleId}")
    boolean incrCollectionCount(Long articleId);

    @Update("update art_article set collection_count = collection_count - 1 where id = #{articleId}")
    boolean decrCollectionCount(Long articleId);

    @Update("update art_article set view_count = view_count + 1 where id = #{articleId}")
    boolean incrViewCount(Long articleId);

    @Update("update art_article set comment_count = comment_count + 1 where id = #{articleId}")
    boolean incrCommentCount(Long articleId);
}
