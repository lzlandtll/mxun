package com.mxun.sys.service.impl;


import com.alibaba.cloud.commons.lang.StringUtils;
import com.mxun.common.enums.*;
import com.mxun.common.utils.UserUtil;
import com.mxun.sys.dto.SyncUserDTO;
import com.mxun.sys.dto.UserDTO;
import com.mxun.sys.entity.User;
import com.mxun.sys.feign.MemFeignService;
import com.mxun.sys.feign.ThirdPartyFeignService;
import com.mxun.sys.mapper.UserMapper;
import com.mxun.sys.service.RoleUserService;
import com.mxun.sys.service.UserService;
import com.mxun.common.dto.SmsCodeDTO;
import com.mxun.common.resultView.BusinessException;
import com.mxun.common.resultView.ResultView;
import com.mxun.common.utils.RSADecoder;
import com.mxun.common.utils.TextDigester;
import com.mxun.sys.stream.KafkaProducerService;
import com.mxun.sys.vo.PersonalUserVO;
import com.mxun.sys.vo.PublicUserVO;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Row;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static com.mxun.sys.entity.table.UserTableDef.USER;
/**
 * 用户基本信息表 服务层实现
 * @author moxuan
 * @since 2025-03-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Value("${custom.kafka.sync-user-topic}")
    private String syncUserTopic;

    @Autowired
    private ThirdPartyFeignService thirdPartyFeignService;

    @Autowired
    private RoleUserService roleUserService;

    @Autowired
    private MemFeignService memFeignService;

    @Autowired
    private KafkaProducerService kafkaProducerService;


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

    @Transactional
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
        String username;
        while (true){
            // 生成一个随机用户名
            username = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
            query = this.query();
            query.eq(User::getUsername, username);
            User userDb = getOne(query);
            if(Objects.isNull(userDb)){
                break;
            }
        }
        User user = User.builder()
                .username(username)
                .password(TextDigester.digest(decodePassword))
                .tel(userDTO.getTel())
                .userType(UserTypeEnum.COMMON_USER.getUserType())
                .build();
        super.save(user);

        // 同步用户数据到会员模块
        SyncUserDTO syncUserDTO = new SyncUserDTO();
        BeanUtils.copyProperties(user, syncUserDTO);
        ResultView resultView = memFeignService.syncSysUser(syncUserDTO);
        if(!Objects.equals(resultView.getCode(), ErrorEnum.SUCCESS.getCode())){
            kafkaProducerService.sendMessage(syncUserTopic, syncUserDTO.getId(), syncUserDTO);
        }
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

    @Override
    public PublicUserVO getUserById(Long userId) {
        User user = getById(userId);
        if(Objects.isNull(user)){
            throw new BusinessException(ErrorEnum.USER_ID_VALID_ERROR);
        }
        PublicUserVO publicUserVO = new PublicUserVO();
        publicUserVO.setUserId(user.getId());
        publicUserVO.setUsername(user.getUsername());
        publicUserVO.setSummary(user.getSummary());
        return publicUserVO;
    }

    @Override
    public PersonalUserVO getPersonalUserInfo() {
        User userDb = getById(UserUtil.getUserId());
        PersonalUserVO personalUserVO = new PersonalUserVO();
        personalUserVO.setUserId(userDb.getId());
        personalUserVO.setUsername(userDb.getUsername());
        personalUserVO.setSummary(userDb.getSummary());
        personalUserVO.setEmail(userDb.getEmail());
        personalUserVO.setTel(userDb.getTel());
        return personalUserVO;
    }

    @Override
    public void savePersonalUserInfo(PersonalUserVO personalUserVO) {
        Long userId = UserUtil.getUserId();

        QueryWrapper query = this.query();
        query.ne(User::getId, userId);
        query.and(USER.USERNAME.eq(personalUserVO.getUsername())
                .or(USER.TEL.eq(personalUserVO.getTel()))
                .or(USER.EMAIL.eq(personalUserVO.getEmail())));
        User userDb = getOne(query);

        if(Objects.nonNull(userDb)){
            if(Objects.equals(userDb.getUsername(), personalUserVO.getUsername())){
                throw new BusinessException(ErrorEnum.USER_NAME_ALREADY_EXIST_ERROR);
            }
            if (Objects.equals(userDb.getTel(), personalUserVO.getTel())){
                throw new BusinessException(ErrorEnum.USER_TEL_ALREADY_EXIST_ERROR);
            }
            if (Objects.equals(userDb.getEmail(), personalUserVO.getEmail())){
                throw new BusinessException(ErrorEnum.USER_EMAIL_ALREADY_EXIST_ERROR);
            }
        }

        UpdateChain.of(User.class)
                .set(User::getEmail, personalUserVO.getEmail())
                .set(User::getTel, personalUserVO.getTel())
                .set(User::getSummary, personalUserVO.getSummary())
                .set(User::getUsername, personalUserVO.getUsername())
                .where(User::getId).eq(userId)
                .update();
    }
}
