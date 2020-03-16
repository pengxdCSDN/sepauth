package com.pxd.authcore.properties;

import lombok.Data;

/**
 * @description: 授权服务 ClientDetailsServiceConfigurer的配置信息
 * @author: pxd
 * @create: 2019-01-22 08:49
 **/
@Data
public class Oauth2ClientDetailProperties {

    private String clientId;
    ;

    private String clientSecret;

    private int accessTokenValidateSeconds = 10800;

    private boolean autoApprove = true;

    private String[] authorizedGrantTypes = {"refresh_token", "authorization_code", "password"};

    private int refreshTokenValiditySeconds = 36000;

    private String resourceIds;

    private String[] scope;

}
