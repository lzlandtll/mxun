package com.mxun.art.service.impl;

import com.mxun.art.dto.UserDTO;
import com.mxun.art.feign.SysFeignService;
import com.mxun.art.service.ArticleService;
import com.mxun.art.vo.req.ReqCommentVO;
import com.mxun.art.vo.resp.RespCommentVO;
import com.mxun.common.constant.Constants;
import com.mxun.common.core.CommonEntity;
import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.resultView.ResultView;
import com.mxun.common.utils.UserUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.art.entity.Comment;
import com.mxun.art.mapper.CommentMapper;
import com.mxun.art.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章评论关联表 服务层实现。
 *
 * @author moxuan
 * @since 2025-03-30
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private SysFeignService sysFeignService;

    @Autowired
    private ArticleService articleService;

    @Override
    public List<RespCommentVO> getCommentList(Long articleId) {
        if(Objects.isNull(articleId)){
            throw new BusinessException(ErrorEnum.ART_ARTICLE_ID_NOT_BLANK_ERROR);
        }
        QueryWrapper query = this.query();
        query.eq(Comment::getArticleId, articleId);

        List<Comment> commentListDb = list(query);
        if(CollectionUtils.isEmpty(commentListDb)){
            return List.of();
        }

        // 获取用户信息
        List<RespCommentVO> respCommentVoDbList = commentListDb.stream().map(commentDb -> {
            RespCommentVO respCommentVO = new RespCommentVO();
            BeanUtils.copyProperties(commentDb, respCommentVO);
            ResultView<UserDTO> resultUser = sysFeignService.getUserById(commentDb.getUserId());
            if(!Objects.equals(resultUser.getCode(), ErrorEnum.SUCCESS.getCode())){
                throw new BusinessException(ErrorEnum.getError(resultUser.getCode()));
            }
            respCommentVO.setUserName(resultUser.getData().getUsername());
            return respCommentVO;
        }).collect(Collectors.toList());

        // 收集根评论
        List<RespCommentVO> respCommentVoList = respCommentVoDbList.stream()
                .filter(commentDb -> Objects.equals(commentDb.getParentId(), Constants.ROOT_PARENT_ID))
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(respCommentVoList)){
            return List.of();
        }
        for (RespCommentVO respCommentVO : respCommentVoList) {
            ArrayList<RespCommentVO> childrenRespCommentVoList = new ArrayList<RespCommentVO>();
            respCommentVO.setChildren(childrenRespCommentVoList);
            // 获取子评论
            getCommentChildren(respCommentVO, respCommentVoDbList, childrenRespCommentVoList);
            childrenRespCommentVoList.sort(Comparator.comparing(CommonEntity::getCreateTime));
        }
        return respCommentVoList;
    }

    private void getCommentChildren(RespCommentVO parentRespCommentVO, List<RespCommentVO> commentList, List<RespCommentVO> childrenCommentList){
        if(CollectionUtils.isEmpty(commentList)){
            return;
        }
        List<RespCommentVO> childrenRespCommentVoList = commentList.stream()
                .filter(commentDb -> Objects.equals(commentDb.getParentId(), parentRespCommentVO.getId()))
                .map(commentDb -> {
                    RespCommentVO respCommentVO = new RespCommentVO();
                    BeanUtils.copyProperties(commentDb, respCommentVO);
                    respCommentVO.setParentUserName(parentRespCommentVO.getUserName());
                    return respCommentVO;
                }).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(childrenRespCommentVoList)){
            return;
        }
        childrenCommentList.addAll(childrenRespCommentVoList);
        for (RespCommentVO childrenRespCommentVO : childrenRespCommentVoList) {
            getCommentChildren(childrenRespCommentVO, commentList, childrenCommentList);
        }
    }



    @Transactional
    @Override
    public RespCommentVO save(ReqCommentVO reqCommentVO) {

        Comment comment = new Comment();
        comment.setUserId(UserUtil.getUserId());
        BeanUtils.copyProperties(reqCommentVO, comment);
        save(comment);

        comment = getById(comment.getId());

        RespCommentVO respCommentVO = new RespCommentVO();
        BeanUtils.copyProperties(comment, respCommentVO);
        respCommentVO.setUserName(UserUtil.getUserName());

        articleService.incrCommentCount(comment.getArticleId());

        if(!Objects.equals(comment.getParentId(), Constants.ROOT_PARENT_ID)){
            Comment commentDb = getById(reqCommentVO.getParentId());
            if(Objects.isNull(commentDb)){
                throw new BusinessException(ErrorEnum.ART_VALID_PARENT_COMMENT_ID_ERROR);
            }

            ResultView<UserDTO> resultUser = sysFeignService.getUserById(commentDb.getUserId());
            if(!Objects.equals(resultUser.getCode(), ErrorEnum.SUCCESS.getCode())){
                throw new BusinessException(ErrorEnum.getError(resultUser.getCode()));
            }
            UserDTO parentUser = resultUser.getData();
            respCommentVO.setParentUserName(parentUser.getUsername());
        }

        return respCommentVO;
    }
}
