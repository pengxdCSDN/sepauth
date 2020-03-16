package com.pxd.authcore.properties;

import com.pxd.authcore.constant.SecurityConstants;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: oauth2相关配置
 * @author: pxd
 * @create: 2019-01-22 11:45
 **/
@Data
public class Oauth2Properties {

    private static final String[] IGNORE_CODE_URL = {
            SecurityConstants.AUTHENTICATION_SIGN_IN_PROCESSING_URL_KEYID,
            "/validate/create"

    };

    /**
     * 默认使用authcore的AuthorizationServerConfigurerAdapter
     */
    private boolean defaultServer;


    /*
     * 使用token的存储方式，默认jwt
     *
     */
    private String tokenStore;

    /*
     * AuthorizationServerConfigurerAdapter 中clientDetail的配置信息
     *
     */
    private String[] ignoreCodeUrl;


    private Oauth2ClientDetailProperties[] clientDetails = {};


    public String[] getIgnoreCodeUrl() {
        if (StringUtils.isEmpty(ignoreCodeUrl) || ignoreCodeUrl.length == 0) {
            return IGNORE_CODE_URL;
        }

        List<String> list = new ArrayList<>();
        for (String url : IGNORE_CODE_URL) {
            list.add(url);
        }
        for (String url : ignoreCodeUrl) {
            list.add(url);
        }

        return list.toArray(new String[list.size()]);
    }

    public void setIgnoreCodeUrl(String[] ignoreCodeUrl) {
        this.ignoreCodeUrl = ignoreCodeUrl;
    }
}
