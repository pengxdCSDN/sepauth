package com.pxd.authcore.handle.imgcode;

import cn.hutool.core.util.StrUtil;
import com.pxd.authcore.exception.CodeAuthenticationException;
import com.pxd.authcore.handle.ValidateCodeRepository;
import com.pxd.authcore.handle.AbstractValidateCodeHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 图片处理器
 * @author: pxd
 * @create: 2019-03-22 10:28
 **/
@Component
public class ImgValidateCodeHandle extends AbstractValidateCodeHandle<ImgValidateCodeDTO> {


    @Autowired
    @Qualifier(value = "redisValidateCodeRepository")
    private ValidateCodeRepository validateCodeRepository;

    //创建验证码，包括生成，存储
    @Override
    public ImgValidateCodeDTO create(ServletWebRequest request) throws Exception {

        ImgValidateCodeDTO validateCodeDTO = generate(request);

        validateCodeRepository.save(request, validateCodeDTO);

        return validateCodeDTO;
    }


    @Override
    public void validate(ServletWebRequest request) {

        //  boolean validateFlag = false;
        String codeInRedis = validateCodeRepository.get(request);
        String codeInParam = request.getParameter("code");
        String codeId = request.getParameter("codeId");
        if (StrUtil.isBlank(codeInParam)) {
            throw new CodeAuthenticationException("未能获取验证码值 codeInParam:" + codeInParam);
        }
        if (StrUtil.isBlank(codeInRedis)) {
            throw new CodeAuthenticationException("验证码已过期");
        }
        if (!codeInParam.toLowerCase().equals(codeInRedis.toLowerCase())) {
            throw new CodeAuthenticationException("验证码不匹配:" + codeInParam + "!=" + codeInRedis);
        }
        //  validateFlag = true;
        validateCodeRepository.remove(request, codeId);
        //   return validateFlag;
        return;
    }
}
