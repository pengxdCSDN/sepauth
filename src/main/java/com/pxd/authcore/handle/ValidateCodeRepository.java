package com.pxd.authcore.handle;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 验证码的存储
 * @author: pxd
 * @create: 2019-03-25 09:38
 **/
public interface ValidateCodeRepository<C> {

    /**
     * 保存验证码
     *
     * @param request
     * @param c
     */
    void save(ServletWebRequest request, C c);

    /**
     * 获取验证码
     *
     * @param request
     * @return
     */
    String get(ServletWebRequest request);

    /**
     * 移除验证码
     *
     * @param request
     * @param codeId
     */
    void remove(ServletWebRequest request, String codeId);

}
