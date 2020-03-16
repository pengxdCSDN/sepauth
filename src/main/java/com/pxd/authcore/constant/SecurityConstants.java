package com.pxd.authcore.constant;

/**
 * @description: 安全常量
 * @author: pxd
 * @create: 2019-01-14 16:58
 **/
public interface SecurityConstants {

    /**
     * 验证短信验证码时，传递手机号参数
     */
    String AUTHENTICATION_NAME_MOBILE = "mobile";

    /**
     * 验证码参数
     */
    String AUTHENTICATION_NAME_CODE = "code";

    /**
     * 默认的手机验证码登录请求处理url
     */
    String AUTHENTICATION_SIGN_IN_PROCESSING_URL_MOBILE = "/authentication/mobile";

    /**
     * 验证码的参数的名称
     */
    String AUTHENTICATION_NAME_KEYID = "keyId";


    /**
     * 默认的keyId登录请求处理url
     */
    String AUTHENTICATION_SIGN_IN_PROCESSING_URL_KEYID = "/authentication/keyId";

    /**
     * 请求头 Basic前缀
     */
    String AUTHENTICATION_HEAR_BASIC_PREFIX = "Basic ";

    /**
     * 请求头
     */
    String AUTHORIZATION_DEFAULT_HEARER = "Authorization";


    /**
     * 默认密码登录
     */
    String AUTHORIZATION_DEFAULT_PASSWORD_LOGIN = "/login";


}
