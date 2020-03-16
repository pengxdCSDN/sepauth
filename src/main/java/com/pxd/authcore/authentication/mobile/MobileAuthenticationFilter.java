/**
 *
 */
package com.pxd.authcore.authentication.mobile;

import com.pxd.authcore.constant.RedisPerfixKeyConstants;
import com.pxd.authcore.constant.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 短信登录过滤器 自行接入短信平台，如阿里巴巴短信平台等
 * @author: pxd
 * @create: 2019-01-14 16:40
 **/
public class MobileAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String mobileParameter = SecurityConstants.AUTHENTICATION_NAME_MOBILE;

    private String codeParameter = SecurityConstants.AUTHENTICATION_NAME_CODE;

    private boolean postOnly = true;


    //这里匹配请求的url,配置的是请求认证登录的url
    public MobileAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.AUTHENTICATION_SIGN_IN_PROCESSING_URL_MOBILE, "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);

        String code = obtainCode(request);

        if (mobile == null) {
            mobile = "";
        }

        mobile = mobile.trim();


        if (code == null) {
            code = "";
        }

        code = code.trim();

        String mobileCode = (String) redisTemplate.opsForValue().get(RedisPerfixKeyConstants.AUTHENTICATION_REDIS_MOBILE_PERFIX + mobile);
        if (mobileCode == null && code == "" && code.equals(mobile)) {
            throw new AuthenticationServiceException("验证码校验异常！！！");
        }

        MobileAuthenticationToken authRequest = new MobileAuthenticationToken(mobile);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     * 获取手机号
     */
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }


    /**
     * 获取验证码
     */
    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(codeParameter);
    }


    /**
     * Provided so that subclasses may configure what is put into the
     * authentication request's details property.
     *
     * @param request
     *            that an authentication request is being created for
     * @param authRequest
     *            the authentication request object that should have its details
     *            set
     */
    protected void setDetails(HttpServletRequest request, MobileAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from
     * the login request.
     *
     * @param mobileParameter
     *            the parameter name. Defaults to "mobileParameter".
     */
    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "mobileParameter parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }


    public String getCodeParameter() {
        return codeParameter;
    }

    public void setCodeParameter(String codeParameter) {
        Assert.hasText(codeParameter, "codeParameter parameter must not be empty or null");
        this.codeParameter = codeParameter;
    }
}
