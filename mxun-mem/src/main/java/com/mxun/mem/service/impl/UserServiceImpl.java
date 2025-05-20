package com.mxun.mem.service.impl;

import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.resultView.ResultView;
import com.mxun.mem.dto.SyncUserDTO;
import com.mxun.mem.dto.UserDTO;
import com.mxun.mem.feign.SysFeignService;
import com.mxun.mem.vo.UserVO;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.mem.entity.User;
import com.mxun.mem.mapper.UserMapper;
import com.mxun.mem.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 *  服务层实现。
 * @author moxuan
 * @since 2025-04-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private SysFeignService sysFeignService;

    @Override
    public UserVO getOpenUserInfo(Long userId) {
        User user = getById(userId);
        if(Objects.isNull(user)){
            throw new BusinessException(ErrorEnum.USER_ID_VALID_ERROR);
        }

        ResultView<UserDTO> userResult = sysFeignService.getUserById(userId);
        if(!Objects.equals(userResult.getCode(), ErrorEnum.SUCCESS.getCode())){
            throw new BusinessException(ErrorEnum.getError(userResult.getCode()));
        }
        UserDTO userDTO = userResult.getData();


        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setUsername(userDTO.getUsername());
        userVO.setSummary(userDTO.getSummary());
        return userVO;
    }

    /**
     * @Description: 同步sys模块的用户数据
     * @Author: liuzhilin
     * @Date: 2025/4/5 17:47
     */
    @Retryable(value = RetryException.class, maxAttempts = 2, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    @Override
    public void syncSysUser(SyncUserDTO syncUserDTO) {
        QueryWrapper query = query();
        query.eq(User::getId, syncUserDTO.getId());
        User user = getOne(query);
        if(Objects.isNull(user)){
            user = new User();
            BeanUtils.copyProperties(syncUserDTO, user);
            save(user);
        }else {
            BeanUtils.copyProperties(syncUserDTO, user);
            updateById(user);
        }
        System.out.println("同步用户数据成功");
    }

    @Override
    public void incrArticle(Long userId) {
        boolean bool = mapper.incrArticle(userId);
        if(!bool){
            throw new BusinessException(ErrorEnum.USER_INCR_ARTICLE_COUNT_ERROR);
        }
    }

    @Override
    public void incrFollow(Long userId) {
        boolean bool = mapper.incrFollow(userId);
        if(!bool){
            throw new BusinessException(ErrorEnum.USER_INCR_FOLLOW_COUNT_ERROR);
        }
    }

    @Override
    public void decrFollow(Long userId) {

        boolean bool = mapper.decrFollow(userId);
        if(!bool){
            throw new BusinessException(ErrorEnum.USER_DECR_FOLLOW_COUNT_ERROR);
        }
    }

    @Override
    public void incrArticleLike(Long userId) {
        boolean bool = mapper.incrArticleLike(userId);
        if(!bool){
            throw new BusinessException(ErrorEnum.USER_INCR_ARTICLE_LIKE_ERROR);
        }
    }

    @Override
    public void decrArticleLike(Long userId) {

        boolean bool = mapper.decrArticleLike(userId);
        if(!bool){
            throw new BusinessException(ErrorEnum.USER_DECR_ARTICLE_LIKE_ERROR);
        }
    }

    @Override
    public void incrArticleCollection(Long userId) {
        boolean bool = mapper.incrArticleCollection(userId);
        if(!bool){
            throw new BusinessException(ErrorEnum.USER_INCR_ARTICLE_COLLECTION_ERROR);
        }
    }

    @Override
    public void decrArticleCollection(Long userId) {
        boolean bool = mapper.decrArticleCollection(userId);
        if(!bool){
            throw new BusinessException(ErrorEnum.USER_DECR_ARTICLE_COLLECTION_ERROR);
        }
    }
}
