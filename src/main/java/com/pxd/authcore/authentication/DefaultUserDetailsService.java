package com.pxd.authcore.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @description: 默认的 UserDetailsService 实现
 * @author: pxd
 * @create: 2019-01-14 16:46
 **/

public class DefaultUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(DefaultUserDetailsService.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.warn("需要自行配置 UserDetailsService 接口的实现或自定义UserDetailsService类似的认证类");
        throw new UsernameNotFoundException("请自定义UserDetailsService的认证类！");
    }


}
