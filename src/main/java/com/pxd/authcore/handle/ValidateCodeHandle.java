package com.pxd.authcore.handle;

import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 验证处理器, 封装校验码的行为
 * @author: pxd
 * @create: 2019-03-22 09:49
 **/
public interface ValidateCodeHandle<V> {


    /**
     * 创建校验码
     *
     * @param request
     * @throws Exception
     */
    V create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     *
     * @param request
     * @throws Exception
     */
    void validate(ServletWebRequest request);


}
