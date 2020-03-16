package com.pxd.authcore.authentication.config;

import cn.hutool.core.util.ArrayUtil;
import com.pxd.authcore.properties.AuthenticationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 默认的认证服务器配置, 必须在TokenStoreConfig之前执行，否则使用内存存储token
 * @author: pxd
 * @create: 2019-01-10 09:28
 **/
@Configuration
@EnableAuthorizationServer
@Order(Integer.MIN_VALUE - 100)
@AutoConfigureAfter(TokenStoreAutoConfiguration.class)
//@ConditionalOnProperty(prefix = "wp.auth.oauth2",name = "defaultServer",havingValue = "true",matchIfMissing = true)
public class DefaultAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired(required = false)
    private AuthenticationProperties authenticationProperties;

    @Autowired(required = false)
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier(value = "useTokenStore")
    private TokenStore useTokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
        ;
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if (ArrayUtil.isNotEmpty(authenticationProperties.getOauth2().getClientDetails())) {
            Arrays.stream(authenticationProperties.getOauth2().getClientDetails()).forEach(clientDetail -> {
                builder
                        .withClient(clientDetail.getClientId())
                        .secret(passwordEncoder.encode(clientDetail.getClientSecret()))
                        .authorizedGrantTypes(clientDetail.getAuthorizedGrantTypes())
                        .scopes(clientDetail.getScope())
                        .resourceIds(clientDetail.getResourceIds())
                        .accessTokenValiditySeconds(clientDetail.getAccessTokenValidateSeconds())
                        .refreshTokenValiditySeconds(clientDetail.getRefreshTokenValiditySeconds())
                        .autoApprove(true)
                ;
            });
        }
    }


    /**
     * 认证及token配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(useTokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
        //增强jwt，配置jwt的额外信息
        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);
            endpoints.tokenEnhancer(enhancerChain).accessTokenConverter(jwtAccessTokenConverter);
        }
    }


}