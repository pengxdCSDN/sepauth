package com.pxd.authcore.handle.imgcode;

import cn.hutool.core.util.StrUtil;
import com.pxd.authcore.exception.CodeAuthenticationException;
import com.pxd.authcore.handle.ValidateCodeRepository;
import com.pxd.authcore.properties.AuthenticationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * @description: 基于redis对验证码的·处理
 * @author: pxd
 * @create: 2019-03-25 10:49
 **/
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository<ImgValidateCodeDTO> {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AuthenticationProperties authenticationProperties;

    @Override
    public void save(ServletWebRequest request, ImgValidateCodeDTO imgValidateCodeDTO) {
        redisTemplate.opsForValue().set(imgValidateCodeDTO.getCodeId(), imgValidateCodeDTO.getCode(), authenticationProperties.getImageCode().getExpireIn(), TimeUnit.SECONDS);
    }

    @Override
    public String get(ServletWebRequest request) {

        String codeId = request.getParameter("codeId");
        if (StrUtil.isBlank(codeId)) {
            throw new CodeAuthenticationException("验证码参数未传 codeId:" + codeId);
        }
        return (String) redisTemplate.opsForValue().get(codeId);
    }

    @Override
    public void remove(ServletWebRequest request, String codeId) {
        redisTemplate.delete(codeId);
    }
}
