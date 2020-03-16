package com.pxd.authcore.handle;

import cn.hutool.core.util.StrUtil;
import com.pxd.authcore.constant.ValidateConstants;
import com.pxd.authcore.exception.CodeAuthenticationException;
import com.pxd.authcore.handle.context.ValidateCodeHandleContext;
import com.pxd.authcore.properties.AuthenticationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @description: 校验验证码的过滤器
 * @author: pxd
 * @create: 2019-03-25 17:11
 **/
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter {


    private static Logger log = LoggerFactory.getLogger(ValidateCodeFilter.class);

    @Autowired
    private AuthenticationFailureHandler wpAuthenticationFailureHandler;

    @Autowired
    private ValidateCodeHandleContext validateCodeHandleContext;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private AuthenticationProperties authenticationProperties;

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();
        String[] whiteLists = authenticationProperties.getOauth2().getIgnoreCodeUrl();
        boolean flag = true;
          /*  for (String path : whiteLists) {
                if (pathMatcher.match(path, uri)) {
                    log.info(path + "不需要验证码校验！");
                    filterChain.doFilter(request, response);
                    //return;
                }
            }*/
        for (int i = 0; i < whiteLists.length; ++i) {
            if (this.pathMatcher.match(whiteLists[i], uri)) {
                flag = false;
                break;
            }
        }
        try {
            if (flag) {
                ValidateCodeHandle validateCodeHandle = getValidateCodeGenerate(request);
                validateCodeHandle.validate(new ServletWebRequest(request, response));
            }

        } catch (CodeAuthenticationException e) {
            wpAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }


    //获取验证码处理器
    protected ValidateCodeHandle getValidateCodeGenerate(HttpServletRequest request) {
        String codeHandle = request.getParameter("type");
        if (StrUtil.isBlank(codeHandle)) {
            codeHandle = ValidateConstants.IMG_TYPR_PERFIX + ValidateConstants.VALIDATE_CODE_HANDLE_SUFFIX;
        } else {
            codeHandle = codeHandle.toLowerCase() + ValidateConstants.VALIDATE_CODE_HANDLE_SUFFIX;
        }
        Optional<ValidateCodeHandle> validateCodeHandle = Optional.ofNullable(validateCodeHandleContext.getValidateCodeHandleMap().get(codeHandle));
        if (!validateCodeHandle.isPresent()) {
            log.info("验证码处理器" + validateCodeHandle + "不存在");
            throw new CodeAuthenticationException("验证码处理器" + validateCodeHandle + "不存在");
        }
        return validateCodeHandle.get();
    }


}
