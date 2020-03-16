[toc]
# 1 版本分类
## 1.0.0-RELEASE
1.授权服务的core包

1.1 此为授权服务的core，其他应用添加该依赖就相当于为一个授权服务

a.需自定义UserDetailsService

/**
 *  实现UserDetailsService，用于用户验证
 */
@Service
public class UserServiceDetail implements UserDetailsService{
        return null;
    }

    
}

b.支持jwt或redis存储token，只需要在tokenStore更改为redis或jwt即可，默认为jwt
sep:
  auth:
    oauth2:
      tokenStore: jwt  

c.如果需要增强jwt，添加该类，自定义信息配置在info即可

@Component("jwtTokenEnhancer")
public class TokenJwtEnhancer implements TokenEnhancer {

	/* (non-Javadoc)
	 * @see org.springframework.security.oauth2.provider.token.TokenEnhancer#enhance(org.springframework.security.oauth2.common.OAuth2AccessToken, org.springframework.security.oauth2.provider.OAuth2Authentication)
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
		info.put("pxd", "吃饭啦");
		
		((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}


d.keyId的登录,在a点配置的UserDetailsService的返回实体需要有keyId参数
    url：xxx/authentication/keyId
    methor:post
    params:keyId

e.需配置客户端信息，token生效时间等，属性具体看DefaultAuthorizationServerConfig的ClientDetailsServiceConfigurer和Oauth2ClientDetailProperties类
**resouresId,clientId,secret三个参数注意和编写的其他资源服务器需要一致**
sep:
  auth:
    oauth2:
      clientDetails[0]:
        clientId: SampleClientId
        clientSecret: secret
        scope: user_info
      clientDetails[1]:
        clientId: SampleClientId2
        clientSecret: secret2
        scope: user_info

f.系统退出,调用xxx/authentication/logout请求，具体业务逻辑参考查看WpLogoutSuccessHandler,如果需要自定制登出逻辑参考该类的编写，配置为bean
(1) 如果配置redirect-sign-in，则退出会重定向到url，比如登录页面
sep:
  auth:
    oauth2:
      redirect-sign-in：http://localhost:8082/ui/aaa类似的url
(2) 如果没有配置，则返回json
    

<------------------------------------------------------------------>


# 1.1.0-RELEASE
1.版本升级到sc(2.1.3.RELEASE)
  a.修复上个版本(2.0.2.RELEASE)的Springcloud gateway与Oauth2集成冲突

2.增加验证码的生成(可配置)，储存(redis)与校验功能

3.增加oauth2异常处理器

sdada




