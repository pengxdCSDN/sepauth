package com.pxd.authcore.authentication.config;

import com.pxd.authcore.properties.AuthenticationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.util.StringUtils;

/**
 * @description: token存储的配置, 必须在DefaultAuthorizationServerConfig之前执行，否则配置bean无效
 * jks的私钥秘钥需自己生成，具体www.bai.com
 * @author: pxd
 * @create: 2019-01-22 15:11
 **/
@Configuration
@ConditionalOnClass(value = DefaultAuthorizationServerConfig.class)
@AutoConfigureBefore(DefaultAuthorizationServerConfig.class)
@Order(Integer.MIN_VALUE - 99)
public class TokenStoreAutoConfiguration {

    /**
     * 默认使用jwt时的配置
     **/
    @Configuration
    @ConditionalOnProperty(prefix = "sep.auth.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
    public static class JwtConfig {


        private static final String PRIVATE_STORE_KEY = "xxx-xxx";

        @Autowired
        private AuthenticationProperties authenticationProperties;

        @Bean(name = "useTokenStore")
        public TokenStore useTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        protected JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            if (!StringUtils.isEmpty(authenticationProperties.getJwt().getJwtSigningKey())) {
                converter.setSigningKey(authenticationProperties.getJwt().getJwtSigningKey());
            } else {
                KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("xxx-jwt.jks"), PRIVATE_STORE_KEY.toCharArray());
                converter.setKeyPair(keyStoreKeyFactory.getKeyPair("xxx-jwt"));
            }
            return converter;
        }


    }

    /**
     * 使用redis存储token的配置，sep.auth.oauth2.tokenStore配置为redis时生效
     */
    @Configuration
    @ConditionalOnProperty(prefix = "sep.auth.oauth2", name = "tokenStore", havingValue = "redis")
    public static class RedisConfig {


        @Autowired(required = false)
        private LettuceConnectionFactory lettuceConnectionFactory;

        @Bean(name = "useTokenStore")
        public TokenStore useTokenStore() {
            LettuceConnectionRedisTokenStore lettuceConnectionRedisTokenStore = new LettuceConnectionRedisTokenStore(lettuceConnectionFactory);
            return lettuceConnectionRedisTokenStore;
        }

    }
}
