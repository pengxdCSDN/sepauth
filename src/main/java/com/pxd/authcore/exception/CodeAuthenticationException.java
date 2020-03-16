package com.pxd.authcore.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @description: 验证码异常
 * @author: pxd
 * @create: 2019-01-14 17:30
 **/
public class CodeAuthenticationException extends AuthenticationException {

    public CodeAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public CodeAuthenticationException(String msg) {
        super(msg);
    }
}
