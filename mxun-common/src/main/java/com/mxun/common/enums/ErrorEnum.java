package com.mxun.common.enums;

/**
* @Description:  返回信息枚举错误提示信息(xx|xxx: 模块名|错误编号)
* @Author: zhilinliu
* @Tel: 13984919416
* @Date: 2022/5/12
*/
public enum ErrorEnum {
    SUCCESS("200", "成功"),
    SYS_ERROR("00000", "系统异常,请稍后重试..."),
    SYS_REQUEST_LIMIT_ERROR("00001", "手速过快,请稍后再试..."),
    SYS_VALID_DATA_ERROR("00002", "请确认数据是否正常..."),
    SYS_TEL_FORMAT_ERROR("00003", "电话号码格式错误..."),
    SYS_ROLE_REPEAT_ADD_ERROR("00004", "权限已添加,请勿重复操作..."),
    SYS_TIME_OUT_ERROR("00005", "网络超时,请稍后再试..."),
    SYS_VALID_PAGE_NUM_ERROR("00006", "非法的页码数据..."),
    SYS_DATA_ERROR("00007", "系统数据错误..."),
    USER_USERNAME_PASSWORD_NOT_BLANK_ERROR("01001", "用户信息不能为空..."),
    USER_USERNAME_PASSWORD_ERROR("01002", "用户名或密码错误..."),
    USER_TOKEN_EXPIRE_ERROR("01003", "登录失效,请重新登录..."),
    USER_TOKEN_VALID_ERROR("01004", "认证凭证错误..."),
    USER_UNAUTHORIZED_ERROR("01005", "用户未进行认证..."),
    USER_TEL_REGISTERED_ERROR("01006", "该电话号码已注册..."),
    USER_PASSWORD_BLANK_ERROR("01007", "密码不能为空..."),
    USER_PASSWORD_LENGTH_TOO_SHORT_ERROR("01008", "密码长度不能低于8位..."),
    USER_PASSWORD_NO_SPECIAL_CHARACTER_ERROR("01009", "密码需要包含特殊字符..."),
    USER_PASSWORD_NO_UPPERCASE_LETTER_ERROR("01010", "密码需要包含大写字符..."),
    USER_PASSWORD_NO_LOWERCASE_LETTER_ERROR("01011", "密码需要包含小写字符..."),
    USER_PASSWORD_NO_DIGIT_ERROR("01012", "密码需要包含数字..."),
    USER_ID_NOT_BLANK_ERROR("01013", "用户编码不能为空..."),
    USER_ID_VALID_ERROR("01014", "非法的用户编码..."),
    THIRD_SMS_SEND_ERROR("02001", "验证码发送失败..."),
    THIRD_SMS_NOT_EXPIRE_ERROR("02002", "请稍后再次尝试发送验证码..."),
    THIRD_SMS_NOT_EXIST_ERROR("02003", "验证码不存在,请重新获取验证码..."),
    THIRD_SMS_CODE_VERIFY_ERROR("02004", "验证码错误,请确认验证码是否正确..."),
    THIRD_SMS_CODE_BLANK_ERROR("02005", "验证码不能为空..."),
    INTERFACE_PERMISSION_NOT_ENOUGH_ERROR("03001", "请开通AI助手服务权限..."),
    CHAT_AI_CONTENT_NOT_BLANK_ERROR("04001", "消息内容不能为空..."),
    CHAT_AI_VALID_AI_KEY_ERROR("04002", "非法的AI密钥..."),
    ART_CATEGORY_NAME_NOT_BLANK_ERROR("05001", "分类名称不能为空..."),
    ART_CATEGORY_NAME_ALREADY_EXIST_ERROR("05002", "该分类已经存在..."),
    ART_ARTICLE_NOT_EXIST_ERROR("05003", "该文章不存在..."),
    ART_ARTICLE_OWNER_IS_NOT_YOU_ERROR("05004", "该文章不属于你..."),
    ART_ARTICLE_VALID_SAVE_STATUS_ERROR("05005", "非法的保存状态..."),
    ART_ARTICLE_STATUS_UPDATE_ERROR("05006", "非法的保存状态..."),
    ART_TAG_NOT_EXIST_ERROR("05007", "标签不存在..."),
    ART_ARTICLE_CONTENT_NOT_BLANK_ERROR("05008", "文章内容不能为空..."),
    ART_ARTICLE_TITLE_NOT_BLANK_ERROR("05009", "文章标题不能为空..."),
    ART_ARTICLE_ID_NOT_BLANK_ERROR("05010", "文章编码不能为空...");

    private String code;
    private String info;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    ErrorEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    /**
     * @Description: 根据错误编码获取对应的错误枚举信息,一般
     *      注解和feign远程调用的时候需要从这个方法进行获取错误信息
     * @Author: liuzhilin
     * @Date: 2025/3/9 11:10
     */
    public static ErrorEnum getError(String code) {
        for (ErrorEnum errorEnum : values()) {
            if (errorEnum.getCode().equals(code)) {
                return errorEnum;
            }
        }
        return SYS_ERROR; // 或者抛出异常，取决于你的需求
    }
}
