package com.mxun.art.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.art.entity.Comment;
import com.mxun.art.mapper.CommentMapper;
import com.mxun.art.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * 文章评论关联表 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
