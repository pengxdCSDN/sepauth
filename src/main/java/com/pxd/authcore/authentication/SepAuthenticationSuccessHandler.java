/**
 *
 */
package com.pxd.authcore.authentication;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxd.authcore.constant.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 登录成功后返回token
 * @author: pxd
 * @create: 2019-01-18 10:40
 **/
@Component("sepAuthenticationSuccessHandler")
public class SepAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(SepAuthenticationSuccessHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientDetailsService clientDetailsService;

    //private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String header = request.getHeader(SecurityConstants.AUTHORIZATION_DEFAULT_HEARER);

        if (header == null || !header.startsWith(SecurityConstants.AUTHENTICATION_HEAR_BASIC_PREFIX)) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }

        String[] clientHeader = extractAndDecodeHeader(header, request);

        if (StringUtils.isEmpty(clientHeader) && clientHeader.length != 2) {
            throw new UnapprovedClientAuthenticationException("错误的请求头信息");
        }

        String clientId = clientHeader[0];
        String clientSecret = clientHeader[1];

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);


        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
        }

		/*if (!passwordEncoder.matches(clientSecret,clientDetails.getClientSecret())){
			throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
		}*/

        if (StringUtils.isEmpty(clientDetails.getClientSecret()) || clientDetails.getClientSecret().equalsIgnoreCase(clientSecret)) {
            throw new InvalidClientException("Given client ID does not match authenticated client");
        }

        TokenRequest tokenRequest = new TokenRequest(MapUtil.newHashMap(), clientId, clientDetails.getScope(), "all");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        response.setContentType("application/json;charset=UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(token));

    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }

}
