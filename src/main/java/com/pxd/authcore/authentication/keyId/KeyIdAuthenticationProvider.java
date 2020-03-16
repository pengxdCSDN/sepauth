/**
 *
 */
package com.pxd.authcore.authentication.keyId;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pxd.authcore.util.BeanMapUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @description: keyId登录验证逻辑
 * @author: pxd
 * @create: 2019-01-14 16:40
 *
 */
public class KeyIdAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        KeyIdAuthenticationToken authenticationToken = (KeyIdAuthenticationToken) authentication;

        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());


        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }


        Map map = BeanMapUtil.beanToMap(user);
        WpUserKeyId wpUserKeyId = new WpUserKeyId();
        wpUserKeyId = BeanMapUtil.mapToBean(map, wpUserKeyId);


        if (((String) authenticationToken.getPrincipal()).isEmpty()) {
            throw new InternalAuthenticationServiceException("keyId参数未传值！");
        }

        if (!authenticationToken.getPrincipal().equals(wpUserKeyId.getKeyId())) {
            throw new InternalAuthenticationServiceException("keyId不匹配！");
        }

        KeyIdAuthenticationToken authenticationResult = new KeyIdAuthenticationToken(user, user.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return KeyIdAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
