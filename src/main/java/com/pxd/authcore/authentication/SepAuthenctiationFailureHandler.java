/**
 *
 */
package com.pxd.authcore.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxd.authcore.domain.ResultCode;
import com.pxd.authcore.dto.ResponseWrapperDto;
import com.pxd.authcore.constant.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 登录失败的处理
 * @author: pxd
 * @create: 2019-01-18 10:40
 **/
@Component("sepAuthenctiationFailureHandler")
public class SepAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        logger.info("登录出现问题！！");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        String uri = request.getRequestURI();
        if (pathMatcher.match(SecurityConstants.AUTHENTICATION_SIGN_IN_PROCESSING_URL_KEYID, uri)) {
            response.getWriter().write(objectMapper.writeValueAsString(new ResponseWrapperDto(false, exception.getMessage(), HttpStatus.UNAUTHORIZED.value())));
        } else {
            response.getWriter().write(objectMapper.writeValueAsString(new ResponseWrapperDto(false, ResultCode.USER_LOGIN_ERROR, null)));
        }
    }
}
