package com.mxun.art.controller;

import com.mxun.art.service.ArticleService;
import com.mxun.art.vo.OpenArticleVO;
import com.mxun.art.vo.ParamVO;
import com.mybatisflex.core.paginate.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

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

    @GetMapping("getOpenArticleDetail")
    public OpenArticleVO getArticleDetail(@RequestParam("articleId") @NotNull(message = "05010") Long articleId) throws NoSuchAlgorithmException {
        return articleService.getArticleDetail(articleId);
    }

    @PostMapping("getArticleListByParam")
    public Page<OpenArticleVO> getArticleListByParam(@Valid @RequestBody ParamVO query) {
        return articleService.getArticleListByParam(query);
    }

}
