/**
 *
 */
package com.pxd.authcore.authentication.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @description: 短信登录配置
 *
 * @author: pxd
 * @create: 2019-01-14 16:40
 *
 */
@Component
public class MobileAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public void configure(HttpSecurity http) throws Exception {

        MobileAuthenticationFilter mobileCodeAuthenticationFilter = new MobileAuthenticationFilter();
        mobileCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        MobileAuthenticationProvider mobileCodeAuthenticationProvider = new MobileAuthenticationProvider();
        mobileCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

        http.authenticationProvider(mobileCodeAuthenticationProvider)
                .addFilterAfter(mobileCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

//		

    }

}
