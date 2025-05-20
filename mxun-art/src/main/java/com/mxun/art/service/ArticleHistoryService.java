package com.mxun.art.service;

import com.mxun.art.dto.ArticleHistoryDTO;
import com.mxun.art.vo.resp.EditArticleHistoryVO;
import com.mybatisflex.core.service.IService;
import com.mxun.art.entity.ArticleHistory;

import java.security.NoSuchAlgorithmException;

/**
 * 文章历史记录(草稿)表，用户编辑完成之后可以进行发布 服务层。
 *
 * @author moxuan
 * @since 2025-03-30
 */
public interface ArticleHistoryService extends IService<ArticleHistory> {

    //保存文章
    ArticleHistory saveArticleHistory(ArticleHistoryDTO articleHistoryDTO) throws NoSuchAlgorithmException;

    // 发布文章
    void publishArticle(ArticleHistoryDTO articleHistoryDTO) throws NoSuchAlgorithmException;

    // 更新历史文章状态
    ArticleHistory updateStatus(Long id, String status);

    // 获取编辑的文章详
    EditArticleHistoryVO getEditArticleHistoryDetail(Long articleId);
}
