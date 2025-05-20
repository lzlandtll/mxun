package com.mxun.mem.mapper;

import com.mybatisflex.core.BaseMapper;
import com.mxun.mem.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 *  映射层。
 *
 * @author moxuan
 * @since 2025-04-04
 */
public interface UserMapper extends BaseMapper<User> {


    @Update("update mem_user set article_count = article_count + 1 where id = #{userId}")
    boolean incrArticle(@Param("userId") Long userId);

    @Update("update mem_user set follow_count = follow_count + 1 where id = #{userId}")
    boolean incrFollow(@Param("userId") Long userId);

    @Update("update mem_user set follow_count = follow_count - 1 where id = #{userId}")
    boolean decrFollow(@Param("userId") Long userId);

    @Update("update mem_user set article_like_count = article_like_count + 1 where id = #{userId}")
    boolean incrArticleLike(@Param("userId") Long userId);

    @Update("update mem_user set article_like_count = article_like_count - 1 where id = #{userId}")
    boolean decrArticleLike(@Param("userId") Long userId);

    @Update("update mem_user set article_collection_count = article_collection_count + 1 where id = #{userId}")
    boolean incrArticleCollection(@Param("userId") Long userId);

    @Update("update mem_user set article_collection_count = article_collection_count - 1 where id = #{userId}")
    boolean decrArticleCollection(@Param("userId") Long userId);


}
