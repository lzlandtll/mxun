package com.mxun.art.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.mxun.art.service.ArticleService;


/**
 * 文章表,存储已经发布成功的文章信息 控制层。
 * @author moxuan
 * @since 2025-03-30
 */
@Validated
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

}
