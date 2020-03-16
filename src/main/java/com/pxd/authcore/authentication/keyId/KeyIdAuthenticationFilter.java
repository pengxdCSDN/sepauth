/**
 *
 */
package com.pxd.authcore.authentication.keyId;

import com.pxd.authcore.constant.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: keyId登录过滤器
 * @author: pxd
 * @create: 2019-01-14 16:40
 **/
public class KeyIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    private String keyIdParameter = SecurityConstants.AUTHENTICATION_NAME_KEYID;

    private boolean postOnly = true;


    //这里匹配请求的url,配置的是请求认证登录的url
    public KeyIdAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.AUTHENTICATION_SIGN_IN_PROCESSING_URL_KEYID, "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String keyId = obtainKeyId(request);


        if (keyId == null) {
            keyId = "";
        }

        keyId = keyId.trim();


        KeyIdAuthenticationToken authRequest = new KeyIdAuthenticationToken(keyId);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     * 获取KeyId
     */
    protected String obtainKeyId(HttpServletRequest request) {
        return request.getParameter(keyIdParameter);
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
    protected void setDetails(HttpServletRequest request, KeyIdAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }


    public String getKeyIdParameter() {
        return keyIdParameter;
    }

    public void setKeyIdParameter(String keyIdParameter) {
        Assert.hasText(keyIdParameter, "codeParameter parameter must not be empty or null");
        this.keyIdParameter = keyIdParameter;
    }
}
