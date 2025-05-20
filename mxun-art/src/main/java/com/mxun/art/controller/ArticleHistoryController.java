package com.mxun.art.controller;


import com.mxun.art.dto.ArticleHistoryDTO;
import com.mxun.art.enums.ArticleSaveStausEnum;
import com.mxun.art.vo.resp.EditArticleHistoryVO;
import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.utils.StringUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.mxun.art.entity.ArticleHistory;
import com.mxun.art.service.ArticleHistoryService;

import java.security.NoSuchAlgorithmException;

/**
 * 文章历史记录(草稿)表，用户编辑完成之后可以进行发布 控制层。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@Validated
@RestController
@RequestMapping("/api/articleHistory")
public class ArticleHistoryController {

    @Autowired
    private ArticleHistoryService articleHistoryService;

    /**
     * 文章保存
     */
    @PostMapping("saveArticleHistory")
    public ArticleHistory saveArticleHistory(@Valid @RequestBody ArticleHistoryDTO articleHistoryDTO) throws NoSuchAlgorithmException {
        if(!StringUtils.equalAny(articleHistoryDTO.getSaveStatus(), ArticleSaveStausEnum.ARTICLE_AUTO_SAVE.getSaveStatus(), ArticleSaveStausEnum.ARTICLE_MANUAL_SAVE.getSaveStatus())){
            throw new BusinessException(ErrorEnum.ART_ARTICLE_VALID_SAVE_STATUS_ERROR);
        }
        return articleHistoryService.saveArticleHistory(articleHistoryDTO);
    }


    @PostMapping("publishArticle")
    public void publishArticle(@Valid @RequestBody ArticleHistoryDTO articleHistoryDTO) throws NoSuchAlgorithmException {
        articleHistoryService.publishArticle(articleHistoryDTO);
    }

    /**
     * @Description: 获取编辑的文章详
     * @Author: liuzhilin
     * @Date: 2025/4/6 20:51
     */
    @GetMapping("getEditArticleHistoryDetail")
    public EditArticleHistoryVO getEditArticleHistoryDetail(@RequestParam("articleId") @NotNull(message = "05010") Long articleId) throws NoSuchAlgorithmException {
        return articleHistoryService.getEditArticleHistoryDetail(articleId);
    }
}
