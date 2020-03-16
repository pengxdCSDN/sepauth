package com.pxd.authcore.handle;

import cn.hutool.core.util.StrUtil;
import com.pxd.authcore.constant.ValidateConstants;
import com.pxd.authcore.exception.CodeAuthenticationException;
import com.pxd.authcore.handle.context.ValidateCodeGenerateContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Optional;


/**
 * @description: 验证码处理器的处理
 * @author: pxd
 * @create: 2019-03-22 10:18
 **/
public abstract class AbstractValidateCodeHandle<V extends ValidateCodeDTO> implements ValidateCodeHandle {


    private static Logger log = LoggerFactory.getLogger(AbstractValidateCodeHandle.class);

    @Autowired
    private ValidateCodeGenerateContext validateCodeGenerateContext;

    //生成验证码
    protected V generate(ServletWebRequest request) {
        String codeTypeGenerate = getValidateCodeGenerate(request);
        Optional<ValidateCodeGenerateStrategy> validateCodeGenerateStrategy = Optional.ofNullable(validateCodeGenerateContext.getValidationCodeGenerateStrategyMap().get(codeTypeGenerate));
        if (!validateCodeGenerateStrategy.isPresent()) {
            log.info("验证码生成器" + codeTypeGenerate + "不存在");
            throw new CodeAuthenticationException("验证码生成器" + codeTypeGenerate + "不存在");
        }
        return (V) validateCodeGenerateStrategy.get().generate(request);

    }

    //获取校验验证码的类型
    protected String getValidateCodeGenerate(ServletWebRequest request) {
        String codeType = request.getParameter("type");
        if (StrUtil.isBlank(codeType)) {
            codeType = ValidateConstants.IMG_TYPR_PERFIX + ValidateConstants.VALIDATE_CODE_GENERATE_SUFFIX;
        } else {
            codeType = codeType.toLowerCase() + ValidateConstants.VALIDATE_CODE_GENERATE_SUFFIX;
        }
        return codeType;
    }

}
