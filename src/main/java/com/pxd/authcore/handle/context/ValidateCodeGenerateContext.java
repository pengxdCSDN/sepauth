package com.pxd.authcore.handle.context;

import com.pxd.authcore.handle.ValidateCodeGenerateStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 获取生成验证码的上下文
 * @author: pxd
 * @create: 2019-03-22 16:43
 **/
@Component
public class ValidateCodeGenerateContext {

    private final Map<String, ValidateCodeGenerateStrategy> validationCodeGenerateStrategyMap = new ConcurrentHashMap<>();

    public ValidateCodeGenerateContext(Map<String, ValidateCodeGenerateStrategy> validationProvessorStrategyMap) {
        // this.validationProvessorStrategyMap.clear();
        validationProvessorStrategyMap.forEach((k, v) -> this.validationCodeGenerateStrategyMap.put(k, v));
    }


    public ValidateCodeGenerateStrategy getCodeGenerater(String typename) {
        return this.getValidationCodeGenerateStrategyMap().get(typename);
    }


    public Map<String, ValidateCodeGenerateStrategy> getValidationCodeGenerateStrategyMap() {
        return this.validationCodeGenerateStrategyMap;
    }


}
