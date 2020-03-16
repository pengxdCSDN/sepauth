package com.pxd.authcore.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxd.authcore.domain.ResultCode;
import com.pxd.authcore.dto.ResponseWrapperDto;
import com.pxd.authcore.properties.AuthenticationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 退出处理器
 * @author: pxd
 * @create: 2019-01-21 10:38
 **/
//@Component("sepLogoutSuccessHandler")
public class SepLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(SepLogoutSuccessHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthenticationProperties authenticationProperties;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        logger.info("退出处理");
        //如果没有配置重定向到登录页面,则返回json,此处如果需要删除redis的token等操作再编写
        if (StringUtils.isEmpty(authenticationProperties.getRedirectSignIn())) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new ResponseWrapperDto(true, "退出成功！", ResultCode.SUCCESS)));
        } else if (UrlUtils.isValidRedirectUrl(authenticationProperties.getRedirectSignIn())) {
            getRedirectStrategy().sendRedirect(request, response, authenticationProperties.getRedirectSignIn());

        } else {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new ResponseWrapperDto(false, "配置重定向的url有误！请重新设置", 500)));
        }
        //   response.sendRedirect("http://localhost:8082/ui/aaa");
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
