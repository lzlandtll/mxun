package com.mxun.sys.service.impl;


import com.alibaba.cloud.commons.lang.StringUtils;
import com.mxun.common.enums.*;
import com.mxun.common.utils.UserUtil;
import com.mxun.sys.dto.UserDTO;
import com.mxun.sys.entity.User;
import com.mxun.sys.feign.ThirdPartyFeignService;
import com.mxun.sys.mapper.UserMapper;
import com.mxun.sys.service.RoleUserService;
import com.mxun.sys.service.UserService;
import com.mxun.common.dto.SmsCodeDTO;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.resultView.ResultView;
import com.mxun.common.utils.RSADecoder;
import com.mxun.common.utils.TextDigester;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 用户基本信息表 服务层实现
 * @author moxuan
 * @since 2025-03-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private ThirdPartyFeignService thirdPartyFeignService;

    @Autowired
    private RoleUserService roleUserService;

    @Override
    public void getRegisterSmsCode(String tel) {

        QueryWrapper query = this.query();
        query.eq(User::getTel, tel);
        User user = getOne(query);
        if(Objects.nonNull(user)){
            throw new BusinessException(ErrorEnum.USER_TEL_REGISTERED_ERROR);
        }

        SmsCodeDTO smsCodeDTO = new SmsCodeDTO();
        smsCodeDTO.setTel(tel);
        smsCodeDTO.setType(SmsTypeEnum.REGISTER.getType());
        smsCodeDTO.setTemplateId(SmsTemplateEnum.REGISTER_TEMPLATE.getTemplateId());
        smsCodeDTO.setCode(String.valueOf((int)((Math.random()*9+1)*100000)));
        smsCodeDTO.setExpireSecond(300L);
        ResultView resultView = thirdPartyFeignService.sendCode(smsCodeDTO);
        if(!Objects.equals(resultView.getCode(), ErrorEnum.SUCCESS.getCode())){
            throw new BusinessException(ErrorEnum.getError(resultView.getCode()));
        }
    }

    @Override
    public void registerAccount(UserDTO userDTO) {
        String decodePassword = RSADecoder.decode(userDTO.getPassword());
        if(StringUtils.isBlank(decodePassword)){
            throw new BusinessException(ErrorEnum.USER_PASSWORD_BLANK_ERROR);
        }
        if (!decodePassword.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new BusinessException(ErrorEnum.USER_PASSWORD_NO_SPECIAL_CHARACTER_ERROR);
        }
        if (!decodePassword.matches(".*[A-Z].*")) {
            throw new BusinessException(ErrorEnum.USER_PASSWORD_NO_UPPERCASE_LETTER_ERROR);
        }
        if (!decodePassword.matches(".*[a-z].*")) {
            throw new BusinessException(ErrorEnum.USER_PASSWORD_NO_LOWERCASE_LETTER_ERROR);
        }
        if (!decodePassword.matches(".*\\d.*")) {
            throw new BusinessException(ErrorEnum.USER_PASSWORD_NO_DIGIT_ERROR);
        }
        if (decodePassword.length() < 8) {
            throw new BusinessException(ErrorEnum.USER_PASSWORD_LENGTH_TOO_SHORT_ERROR);
        }
        SmsCodeDTO smsCodeDTO = new SmsCodeDTO();
        smsCodeDTO.setTel(userDTO.getTel());
        smsCodeDTO.setType(SmsTypeEnum.REGISTER.getType());
        smsCodeDTO.setCode(userDTO.getCode());
        ResultView codeView = thirdPartyFeignService.verifyCode(smsCodeDTO);
        if(!Objects.equals(codeView.getCode(), ErrorEnum.SUCCESS.getCode())){
            throw new BusinessException(ErrorEnum.getError(codeView.getCode()));
        }

        QueryWrapper query = this.query();
        query.eq(User::getTel, userDTO.getTel());
        User existUser = getOne(query);
        if(Objects.nonNull(existUser)){
            throw new BusinessException(ErrorEnum.USER_TEL_REGISTERED_ERROR);
        }
        User user = new User();
        User build = user.builder()
                .password(TextDigester.digest(decodePassword))
                .tel(userDTO.getTel())
                .userType(UserTypeEnum.COMMON_USER.getUserType())
                .build();
        super.save(build);
    }

    @Transactional
    @Override
    public String addAiKey(String aiKey) {
        roleUserService.addRoleUser(UserUtil.getUserId(), RoleEnum.CHAT_AI.getRoleCode());

        User user = new User();
        user.setId(UserUtil.getUserId());
        user.setAiKey(aiKey);
        this.updateById(user);
        UserUtil.setAiKey(aiKey);
        String roleCode = RoleEnum.CHAT_AI.getRoleCode();
        return roleCode;
    }

    @Transactional
    @Override
    public String removeAiKey() {
        roleUserService.removeRoleUser(UserUtil.getUserId(), RoleEnum.CHAT_AI.getRoleCode());
        User user = new User();
        user.setId(UserUtil.getUserId());
        user.setAiKey("");
        this.updateById(user);
        return RoleEnum.CHAT_AI.getRoleCode();
    }
}
