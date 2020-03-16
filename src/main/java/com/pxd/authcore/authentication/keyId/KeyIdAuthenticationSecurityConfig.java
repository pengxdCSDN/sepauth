/**
 *
 */
package com.pxd.authcore.authentication.keyId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @description: keyId登录配置
 *
 * @author: pxd
 * @create: 2019-01-14 16:40
 *
 */
@Component
public class KeyIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler wpAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler wpAuthenticationFailureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        KeyIdAuthenticationFilter keyIdAuthenticationFilter = new KeyIdAuthenticationFilter();
        keyIdAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        KeyIdAuthenticationProvider keyIdAuthenticationProvider = new KeyIdAuthenticationProvider();
        keyIdAuthenticationProvider.setUserDetailsService(userDetailsService);
        keyIdAuthenticationFilter.setAuthenticationSuccessHandler(wpAuthenticationSuccessHandler);
        keyIdAuthenticationFilter.setAuthenticationFailureHandler(wpAuthenticationFailureHandler);
        http.authenticationProvider(keyIdAuthenticationProvider)
                .addFilterAfter(keyIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

//

    }

}
