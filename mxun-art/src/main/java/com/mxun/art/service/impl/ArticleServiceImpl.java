package com.mxun.art.service.impl;

import com.mxun.art.dto.UserDTO;
import com.mxun.art.entity.Category;
import com.mxun.art.entity.Tag;
import com.mxun.art.enums.ArticleStatusEnum;
import com.mxun.art.feign.MemFeignService;
import com.mxun.art.feign.SysFeignService;
import com.mxun.art.service.*;
import com.mxun.art.vo.resp.OpenArticleVO;
import com.mxun.art.vo.req.ParamVO;
import com.mxun.art.vo.resp.RespCommentVO;
import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.resultView.ResultView;
import com.mxun.common.utils.UserUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.art.entity.Article;
import com.mxun.art.mapper.ArticleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mxun.art.entity.table.ArticleTableDef.ARTICLE;

/**
 * 文章表,存储已经发布成功的文章信息 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Lazy
    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private SysFeignService sysFeignService;

    @Autowired
    private ArticleLikeService articleLikeService;

    @Autowired
    private ArticleFavoriteService articleFavoriteService;

    @Autowired
    private MemFeignService memFeignService;


    @Override
    public Article updateStatus(Long id, String status) {
        Article article = new Article();
        article.setId(id);
        article.setStatus(status);
        boolean b = this.updateById(article);
        if(!b){
            throw new BusinessException(ErrorEnum.ART_ARTICLE_STATUS_UPDATE_ERROR);
        }
        return article;
    }

    @Override
    public OpenArticleVO getArticleDetail(Long articleId) {
        Article article = getById(articleId);
        if(Objects.isNull(article)){
            throw new BusinessException(ErrorEnum.ART_ARTICLE_NOT_EXIST_ERROR);
        }
        OpenArticleVO openArticleVO = new OpenArticleVO();
        BeanUtils.copyProperties(article, openArticleVO);

        // 设置作者名称
        ResultView<UserDTO> resultView = sysFeignService.getUserById(article.getAuthorId());
        UserDTO userDTO = resultView.getData();
        openArticleVO.setAuthorName(userDTO.getUsername());

        // 设置分类名称
        if(!Objects.isNull(article.getCategoryId())){
            Category category = categoryService.getById(article.getCategoryId());
            if(!Objects.isNull(category)){
                openArticleVO.setCategoryName(category.getName());
            }
        }

        // 设置文章所属标签信息
        List<Tag> articleTagList = articleTagService.getListByArticleId(article.getId());
        openArticleVO.setTagList(articleTagList);

        if(UserUtil.hasLogin()){
            Boolean hasLiked = articleLikeService.hasLiked(articleId);
            openArticleVO.setIsLike(hasLiked);

            Boolean hasFavorite = articleFavoriteService.hasFavorite(articleId);
            openArticleVO.setIsFavorite(hasFavorite);

            ResultView<Boolean> hasFollowResult = memFeignService.hasFollow(article.getAuthorId());
            if(!Objects.equals(hasFollowResult.getCode(), ErrorEnum.SUCCESS.getCode())){
                throw new BusinessException(ErrorEnum.getError(hasFollowResult.getCode()));
            }
            openArticleVO.setIsFollow(hasFollowResult.getData());
        }

        //  TODO: 设置文章评论信息
        return openArticleVO;
    }

    @Override
    public Page<OpenArticleVO> getArticleListByParam(ParamVO paramVO) {
        QueryWrapper queryWrapper = this.query();
        queryWrapper.eq(Article::getStatus, ArticleStatusEnum.ARTICLE_AUDIT_SUCCESS.getStatus());
        if(!Objects.isNull(paramVO.getUserId())){
            queryWrapper.eq(Article::getAuthorId, paramVO.getUserId());
        }
        if(!Objects.isNull(paramVO.getCategoryId())){
            queryWrapper.eq(Article::getCategoryId, paramVO.getCategoryId());
        }
        if(!Objects.isNull(paramVO.getQueryKey())){
            queryWrapper.and(
                    ARTICLE.TITLE.like(paramVO.getQueryKey())
                            .or(ARTICLE.SUMMARY.like(paramVO.getQueryKey()))
            );
        }
        Page<Article> articlePage = new Page<>(paramVO.getPageNum(), 8);
        Page<Article> page = page(articlePage, queryWrapper);
        List<Article> articleList = page.getRecords();
                ArrayList<OpenArticleVO> openArticleVoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(articleList)){
            articleList.forEach(article -> {
                OpenArticleVO openArticleVO = new OpenArticleVO();
                BeanUtils.copyProperties(article, openArticleVO);

                // 设置作者名称
                ResultView<UserDTO> resultView = sysFeignService.getUserById(article.getAuthorId());
                UserDTO userDTO = resultView.getData();
                openArticleVO.setAuthorName(userDTO.getUsername());

                // 设置分类名称
                if(!Objects.isNull(article.getCategoryId())){
                    Category category = categoryService.getById(article.getCategoryId());
                    if(!Objects.isNull(category)){
                        openArticleVO.setCategoryName(category.getName());
                    }
                }

                // 设置文章所属标签信息
                List<Tag> articleTagList = articleTagService.getListByArticleId(article.getId());
                openArticleVO.setTagList(articleTagList);

                openArticleVoList.add(openArticleVO);
            });
        }
        Page<OpenArticleVO> articleVOPage = new Page<>();
        BeanUtils.copyProperties(articlePage, articleVOPage);
        articleVOPage.setRecords(openArticleVoList);
        return articleVOPage;
    }

    @Override
    public void incrLikeCount(Long articleId) {

        boolean bool = mapper.incrLikeCount(articleId);
        if(!bool){
            throw new BusinessException(ErrorEnum.ART_INCR_ARTICLE_LIKE_COUNT_ERROR);
        }
    }

    @Override
    public void decrLikeCount(Long articleId) {

        boolean bool = mapper.decrLikeCount(articleId);
        if(!bool){
            throw new BusinessException(ErrorEnum.ART_DECR_ARTICLE_LIKE_COUNT_ERROR);
        }
    }

    @Override
    public void incrCollectionCount(Long articleId) {

        boolean bool = mapper.incrCollectionCount(articleId);
        if(!bool){
            throw new BusinessException(ErrorEnum.ART_INCR_ARTICLE_COLLECTION_COUNT_ERROR);
        }
    }

    @Override
    public void decrCollectionCount(Long articleId) {

        boolean bool = mapper.decrCollectionCount(articleId);
        if(!bool){
            throw new BusinessException(ErrorEnum.ART_DECR_ARTICLE_COLLECTION_COUNT_ERROR);
        }
    }

    @Override
    public void incrViewCount(Long articleId) {

        boolean bool = mapper.incrViewCount(articleId);
        if(!bool){
            throw new BusinessException(ErrorEnum.ART_INCR_ARTICLE_VIEW_COUNT_ERROR);
        }
    }

    @Override
    public void incrCommentCount(Long articleId) {

        boolean bool = mapper.incrCommentCount(articleId);
        if(!bool){
            throw new BusinessException(ErrorEnum.ART_INCR_ARTICLE_COMMENT_COUNT_ERROR);
        }
    }


}
