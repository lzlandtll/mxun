package com.mxun.art.service;

import com.mxun.art.vo.req.ReqCommentVO;
import com.mxun.art.vo.resp.RespCommentVO;
import com.mybatisflex.core.service.IService;
import com.mxun.art.entity.Comment;

import java.util.List;

/**
 * 文章评论关联表 服务层。
 *
 * @author moxuan
 * @since 2025-03-30
 */
public interface CommentService extends IService<Comment> {

    List<RespCommentVO> getCommentList(Long articleId);
    RespCommentVO save(ReqCommentVO reqCommentVO);
}
