package com.mxun.art.controller;

import com.mxun.art.service.ArticleService;
import com.mxun.art.service.CommentService;
import com.mxun.art.service.EsArticleService;
import com.mxun.art.vo.resp.OpenArticleVO;
import com.mxun.art.vo.req.ParamVO;
import com.mxun.art.vo.resp.RespCommentVO;
import com.mybatisflex.core.paginate.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @Description: xxx
 * @Author: liuzhilin
 * @Date: 2025/4/2
 */
@Validated
@RestController
@RequestMapping("/open")
public class ArtOpenController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EsArticleService esArticleService;

    @GetMapping("getOpenArticleDetail")
    public OpenArticleVO getArticleDetail(@RequestParam("articleId") @NotNull(message = "05010") Long articleId) throws NoSuchAlgorithmException {
        return articleService.getArticleDetail(articleId);
    }

    @PostMapping("getArticleListByParam1")
    public Page<OpenArticleVO> getArticleListByParam1(@Valid @RequestBody ParamVO query) {
        return articleService.getArticleListByParam(query);
    }

    @GetMapping("getCommentList")
    public List<RespCommentVO> comment(@RequestParam("articleId") @NotNull(message = "05010") Long articleId) {
        return commentService.getCommentList(articleId);
    }


    @PostMapping("getArticleListByParam")
    public org.springframework.data.domain.Page<OpenArticleVO> getArticleListByParam(@RequestBody ParamVO paramVO) throws IOException {
        return esArticleService.searchArticle(paramVO);
    }


}
