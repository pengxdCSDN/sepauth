package com.pxd.authcore.handle.imgcode;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import com.pxd.authcore.constant.ValidateConstants;
import com.pxd.authcore.handle.ValidateCodeGenerateStrategy;
import com.pxd.authcore.properties.AuthenticationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @description: 验证码生成
 * @author: pxd
 * @create: 2019-03-22 15:56
 **/
@Component(value = "imgValidateCodeGenerate")
public class ImgValidateCodeGenerate implements ValidateCodeGenerateStrategy {


    @Autowired
    private AuthenticationProperties authenticationProperties;

    @Override
    public ImgValidateCodeDTO generate(ServletWebRequest request) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(authenticationProperties.getImageCode().getWidth(), authenticationProperties.getImageCode().getHeight(),
                authenticationProperties.getImageCode().getCodeCount(), authenticationProperties.getImageCode().getLineCount());
        return new ImgValidateCodeDTO(lineCaptcha.getCode(), ValidateConstants.CODE_PNG_IMG_BASE64 + lineCaptcha.getImageBase64(), authenticationProperties.getImageCode().getExpireIn(), ValidateConstants.IMG_TYPR_PERFIX + ":" + IdUtil.objectId());
    }
}
