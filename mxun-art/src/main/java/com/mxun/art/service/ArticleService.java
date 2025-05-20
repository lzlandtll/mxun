package com.mxun.art.service;

import com.mxun.art.vo.resp.OpenArticleVO;
import com.mxun.art.vo.req.ParamVO;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.mxun.art.entity.Article;

/**
 * 文章表,存储已经发布成功的文章信息 服务层。
 *
 * @author moxuan
 * @since 2025-03-30
 */
public interface ArticleService extends IService<Article> {

    Article updateStatus(Long id, String status);

    OpenArticleVO getArticleDetail(Long articleId);

    Page<OpenArticleVO> getArticleListByParam(ParamVO paramVO);

    void incrLikeCount(Long articleId);
    void decrLikeCount(Long articleId);
    void incrCollectionCount(Long articleId);
    void decrCollectionCount(Long articleId);
    void incrViewCount(Long articleId);
    void incrCommentCount(Long articleId);
}
