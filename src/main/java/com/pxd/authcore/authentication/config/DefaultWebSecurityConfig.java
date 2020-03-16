package com.pxd.authcore.authentication.config;

import com.pxd.authcore.authentication.mobile.MobileAuthenticationSecurityConfig;
import com.pxd.authcore.handle.ValidateCodeSecurityConfig;
import com.pxd.authcore.authentication.keyId.KeyIdAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.HashMap;
import java.util.Map;


/**
 * @description: WebSecurityConfigurerAdapter用于保护oauth相关的endpoints，同时主要作用于用户的登录(form login,Basic auth)
 * ResourceServerConfigurerAdapter用于保护oauth要开放的资源，同时主要作用于client端以及token的认证(Bearer auth)
 * @author: pxd
 * @create: 2019-01-10 09:27
 **/
@Configuration
//@Order(1)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DefaultWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    private UserDetailsService userDetailsService;

    @Autowired(required = false)
    private KeyIdAuthenticationSecurityConfig keyIdAuthenticationSecurityConfig;

    @Autowired(required = false)
    private MobileAuthenticationSecurityConfig mobileAuthenticationSecurityConfig;

    @Autowired(required = false)
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    protected AuthenticationSuccessHandler sepAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler sepAuthenticationFailureHandler;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

//    @Autowired
//    private WpAuthenticationEntryPoint wpAuthenticationEntryPoint;
//
//    @Autowired
//    private WpAccessDeniedHandler wpAccessDeniedHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off


        http
                .apply(keyIdAuthenticationSecurityConfig)
                .and()
                .apply(mobileAuthenticationSecurityConfig)
                .and()
                .apply(validateCodeSecurityConfig)
                .and()
                .formLogin()
                //.loginProcessingUrl(SecurityConstants.AUTHORIZATION_DEFAULT_PASSWORD_LOGIN)
                .successHandler(sepAuthenticationSuccessHandler)
                .failureHandler(sepAuthenticationFailureHandler)
                .permitAll()
                .and()
                .requestMatchers()
                .antMatchers("/login", "/oauth/authorize")
                .mvcMatchers("/authentication/keyId", "/logout", "/validate/create")
                .and()
                .authorizeRequests()
                .antMatchers("/logout", "/validate/create", "/authentication/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .httpBasic()
                .and()
                .cors()
                .and()
                .csrf().disable()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // @formatter:off
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder defaultEncoder = new BCryptPasswordEncoder();
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        DelegatingPasswordEncoder passworEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
        passworEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);
        return passworEncoder;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}