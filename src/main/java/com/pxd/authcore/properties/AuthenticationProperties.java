package com.pxd.authcore.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 安全模块配置信息
 * @author: pxd
 * @create: 2019-01-21 14:13
 **/
@ConfigurationProperties(prefix = "sep.auth")
@Data
public class AuthenticationProperties {

    //登出重定向url
    private String redirectSignIn;

    private Oauth2Properties oauth2 = new Oauth2Properties();

    private JwtProperties jwt = new JwtProperties();

    private ImageCodeProperties imageCode = new ImageCodeProperties();


}
