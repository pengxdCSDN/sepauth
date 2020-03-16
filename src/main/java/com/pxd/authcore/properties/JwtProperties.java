package com.pxd.authcore.properties;

import lombok.Data;

/**
 * @description: jwt相关配置
 * @author: pxd
 * @create: 2019-01-22 11:47
 **/
@Data
public class JwtProperties {

    private String jwtSigningKey;

    private boolean defaultOpen;

}
