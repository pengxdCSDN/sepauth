package com.pxd.authcore.authentication;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @description: 认证相关的bean配置，不同业务需要自定义声明覆盖
 * @author: pxd
 * @create: 2019-01-14 16:40
 **/
@Configuration
public class DefaultAuthenticationBeanConfig {


    /**
     * 默认密码处理器
     * @return
     */
/*    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }*/

    /**
     * 默认认证器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsService();
    }

    /**
     * 默认登出处理器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new SepLogoutSuccessHandler();
    }

    /*    *//**
     * 默认成功处理器
     *
     * @return
     *//*
    @Bean
    @ConditionalOnMissingBean(SepAuthenticationSuccessHandler.class)
    public AuthenticationSuccessHandler defaultAuthenticationSuccessHandler(){
        return new SepAuthenticationSuccessHandler();
    }

    *//**
     * 默认失败处理器
     *
     * @return
     *//*
    @Bean
    @ConditionalOnMissingBean(SepAuthenctiationFailureHandler.class)
    public AuthenticationFailureHandler defaultAuthenctiationFailureHandler(){
        return new SepAuthenctiationFailureHandler();
    }

    */

}
