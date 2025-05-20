package com.mxun.art.controller;

import com.mxun.art.vo.req.ReqCommentVO;
import com.mxun.art.vo.resp.RespCommentVO;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.mxun.art.entity.Comment;
import com.mxun.art.service.CommentService;

import java.util.List;

/**
 * 文章评论关联表 控制层。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("comment")
    public RespCommentVO comment(@RequestBody ReqCommentVO reqCommentVO) {
        return commentService.save(reqCommentVO);
    }



}
