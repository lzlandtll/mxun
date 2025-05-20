package com.mxun.art.service.impl;

import com.alibaba.nacos.common.utils.MD5Utils;
import com.mxun.art.dto.ArticleHistoryDTO;
import com.mxun.art.entity.Article;
import com.mxun.art.entity.Tag;
import com.mxun.art.enums.ArticleSaveStausEnum;
import com.mxun.art.enums.ArticleStatusEnum;
import com.mxun.art.feign.MemFeignService;
import com.mxun.art.feign.SysFeignService;
import com.mxun.art.service.ArticleService;
import com.mxun.art.service.ArticleTagService;
import com.mxun.art.vo.resp.EditArticleHistoryVO;
import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.resultView.ResultView;
import com.mxun.common.utils.UserUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.art.entity.ArticleHistory;
import com.mxun.art.mapper.ArticleHistoryMapper;
import com.mxun.art.service.ArticleHistoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章历史记录(草稿)表，用户编辑完成之后可以进行发布 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@Service
public class ArticleHistoryServiceImpl extends ServiceImpl<ArticleHistoryMapper, ArticleHistory> implements ArticleHistoryService {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private MemFeignService memFeignService;
    @Autowired
    private SysFeignService sysFeignService;

    @Transactional
    @Override
    public ArticleHistory saveArticleHistory(ArticleHistoryDTO articleHistoryDTO) throws NoSuchAlgorithmException {
        // 文章编码和文章历史编码应该要么都存在要么都不存在
        if(Objects.isNull(articleHistoryDTO.getId()) ^ Objects.isNull(articleHistoryDTO.getArticleId())){
            throw new BusinessException(ErrorEnum.SYS_VALID_DATA_ERROR);
        }

        // 针对传入文章编码存在的需要判断文章是否存在以及文章拥有者是否为当前用户
        if(!Objects.isNull(articleHistoryDTO.getArticleId())){
            Article article = articleService.getById(articleHistoryDTO.getArticleId());
            if(Objects.isNull(article)){
                throw new BusinessException(ErrorEnum.ART_ARTICLE_NOT_EXIST_ERROR);
            }
            if(!Objects.equals(article.getAuthorId(), UserUtil.getUserId())){
                throw new BusinessException(ErrorEnum.ART_ARTICLE_OWNER_IS_NOT_YOU_ERROR);
            }
        }

        String newMd5 = MD5Utils.md5Hex(String.join(",",
                articleHistoryDTO.getTitle(),
                articleHistoryDTO.getSummary(),
                String.valueOf(articleHistoryDTO.getCategoryId()),
                articleHistoryDTO.getContent()
        ).getBytes());

        if(!Objects.isNull(articleHistoryDTO.getId())){
            // 针对文章不为空的情况下，需要判断文章是否被修改过
            QueryWrapper query = this.query();
            query.eq(ArticleHistory::getId, articleHistoryDTO.getId());
            ArticleHistory articleHistoryDb = this.getOne(query);
            if(Objects.isNull(articleHistoryDb)){
                throw new BusinessException(ErrorEnum.ART_CATEGORY_NAME_NOT_BLANK_ERROR);
            }
            if(Objects.equals(newMd5, articleHistoryDb.getMd5())){
                return articleHistoryDb;
            }
        }

        // fixme: 前面都是在校验数据是否需要正常更新

        articleHistoryDTO.setStatus(ArticleStatusEnum.ARTICLE_SAVE.getStatus());

        // 针对文章编码不存在的需要先创建文章
        if(Objects.isNull(articleHistoryDTO.getArticleId())){
            Article article = new Article();
            BeanUtils.copyProperties(articleHistoryDTO, article);
            article.setAuthorId(UserUtil.getUserId());
            articleService.save(article);
            articleHistoryDTO.setArticleId(article.getId());
        }

        articleHistoryDTO.setMd5(newMd5);
        articleHistoryDTO.setId(null);
        this.save(articleHistoryDTO);

        articleTagService.saveArticleTag(articleHistoryDTO.getArticleId(), articleHistoryDTO.getTagIds());

        return articleHistoryDTO;
    }

    @Transactional
    @Override
    public void publishArticle(ArticleHistoryDTO articleHistoryDTO) throws NoSuchAlgorithmException {
        articleHistoryDTO.setSaveStatus(ArticleSaveStausEnum.ARTICLE_PUBLISH_SAVE.getSaveStatus());
        // 1.首先进行保存
        saveArticleHistory(articleHistoryDTO);

        // 2.修改历史文章状态为待审核状态
        updateStatus(articleHistoryDTO.getId(), ArticleStatusEnum.ARTICLE_PENDING_AUDIT.getStatus());

        // 3.修改文章状态为待审核状态
        Article article = articleService.getById(articleHistoryDTO.getArticleId());
        if(!Objects.equals(article.getStatus(), ArticleStatusEnum.ARTICLE_AUDIT_SUCCESS.getStatus())){
            articleService.updateStatus(articleHistoryDTO.getArticleId(), ArticleStatusEnum.ARTICLE_PENDING_AUDIT.getStatus());
        }

        // 4.模拟成功审核文章
        listenerPublishArticle(articleHistoryDTO.getId());
    }

    /**
     * @Description: 监听发布文章
     * @Author: liuzhilin
     * @Date: 2025/4/1 22:44
     */
    public void listenerPublishArticle(Long articleHistoryId){
        ArticleHistory articleHistory = this.getById(articleHistoryId);
        if(Objects.isNull(articleHistory)){
            return;
        }

        // 2.修改文章状态为发布状态
        Article article = articleService.getById(articleHistory.getArticleId());
        String articleStatus = article.getStatus();

        article.setId(articleHistory.getArticleId());
        article.setTitle(articleHistory.getTitle());
        article.setSummary(articleHistory.getSummary());
        article.setCategoryId(articleHistory.getCategoryId());
        article.setContent(articleHistory.getContent());
        article.setStatus(ArticleStatusEnum.ARTICLE_AUDIT_SUCCESS.getStatus());

        articleService.updateById(article);

        // 3.修改历史文章状态为发布状态
        updateStatus(articleHistoryId, ArticleStatusEnum.ARTICLE_AUDIT_SUCCESS.getStatus());


        // 4.增加用户的发布文章总数
        if(!Objects.equals(articleStatus, ArticleStatusEnum.ARTICLE_AUDIT_SUCCESS.getStatus())){
            ResultView resultView = memFeignService.incrArticle(article.getAuthorId());
            if(!resultView.getCode().equals(ErrorEnum.SUCCESS.getCode())){
                throw new BusinessException(ErrorEnum.getError(resultView.getCode()));
            }
        }
    }

    @Override
    public ArticleHistory updateStatus(Long id, String status) {
        ArticleHistory articleHistory = new ArticleHistory();
        articleHistory.setId(id);
        articleHistory.setStatus(status);
        boolean b = this.updateById(articleHistory);
        if(!b){
            throw new BusinessException(ErrorEnum.ART_ARTICLE_STATUS_UPDATE_ERROR);
        }

        return articleHistory;
    }

    @Override
    public EditArticleHistoryVO getEditArticleHistoryDetail(Long articleId) {
        Article article = articleService.getById(articleId);
        if(Objects.isNull(article) || !Objects.equals(article.getAuthorId(), UserUtil.getUserId())){
            throw new BusinessException(ErrorEnum.ART_ARTICLE_NOT_EXIST_ERROR);
        }
        QueryWrapper query = this.query();
        query.eq(ArticleHistory::getArticleId, articleId);
        query.orderBy(ArticleHistory::getId, false);
        query.limit(1);

        ArticleHistory articleHistory = getOne(query);
        if(Objects.isNull(articleHistory)){
            throw new BusinessException(ErrorEnum.SYS_DATA_ERROR);
        }
        // 前面都是在校验数据是否正常

        EditArticleHistoryVO editArticleHistoryVO = new EditArticleHistoryVO();
        BeanUtils.copyProperties(articleHistory, editArticleHistoryVO);

        List<Tag> articleTagList = articleTagService.getListByArticleId(articleId);
        if(!CollectionUtils.isEmpty(articleTagList)){
            editArticleHistoryVO.setTagIds(articleTagList.stream().map(Tag::getId).collect(Collectors.toList()));
        }

        return editArticleHistoryVO;
    }
}
