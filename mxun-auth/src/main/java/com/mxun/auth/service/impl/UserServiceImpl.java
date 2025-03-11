package com.mxun.auth.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.mxun.auth.feign.AdminFeignService;
import com.mxun.auth.vo.UserVO;
import com.mxun.common.dto.UserInfoDTO;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.enums.ErrorEnum;
import com.mxun.common.resultView.ResultView;
import com.mxun.common.utils.JwtTokenUtil;
import com.mxun.common.utils.RSADecoder;
import com.mxun.common.utils.TextDigester;
import com.mxun.common.utils.UserUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.mxun.auth.entity.User;
import com.mxun.auth.mapper.UserMapper;
import com.mxun.auth.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mxun.auth.entity.table.UserTableDef.USER;

/**
 * 用户基本信息表 服务层实现
 * @author moxuan
 * @since 2025-03-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AdminFeignService adminFeignService;

    @Override
    public UserVO commonUserLogin(User userDTO, ArrayList<String> userTypeList) {
        if(StringUtils.isBlank(userDTO.getPassword()) || StringUtils.isBlank(userDTO.getUsername())){
            throw new BusinessException(ErrorEnum.USER_USERNAME_PASSWORD_NOT_BLANK_ERROR);
        }
        String decodePassword;
        try {
            decodePassword = RSADecoder.decode(userDTO.getPassword());
        }catch (Exception e){
            throw new BusinessException(ErrorEnum.USER_USERNAME_PASSWORD_ERROR);
        }
        QueryWrapper queryWrapper = this.query();
        queryWrapper.eq(User::getPassword, TextDigester.digest(decodePassword));
        queryWrapper.in(User::getUserType, userTypeList);
        queryWrapper.and(
                USER.USERNAME.eq(userDTO.getUsername())
                        .or(USER.EMAIL.eq(userDTO.getUsername()))
                        .or(USER.TEL.eq(userDTO.getUsername()))
        );

        User user = getOne(queryWrapper);
        if(Objects.isNull(user)){
            throw new BusinessException(ErrorEnum.USER_USERNAME_PASSWORD_ERROR);
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setToken(JwtTokenUtil.createJwtToken(user.getId().toString(), user.getUsername(), user.getEmail(), user.getTel(), user.getUserType(), 24 * 60 * 60 * 1000));

        // 登录成功之后,设置用户缓存信息
        ResultView<List<Long>> resultView = adminFeignService.getRolesByUserId(user.getId());
        if(!Objects.equals(resultView.getCode(), ErrorEnum.SUCCESS.getCode())){
            throw new BusinessException(ErrorEnum.getError(resultView.getCode()));
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserId(user.getId());
        BeanUtils.copyProperties(user, userInfoDTO);
        userInfoDTO.setRoleList(resultView.getData());
        UserUtil.setUserCache(userInfoDTO);
        return userVO;
    }
}
