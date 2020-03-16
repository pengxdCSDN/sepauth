package com.pxd.authcore.handle.context;

import com.pxd.authcore.handle.ValidateCodeHandle;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 获取处理验证码的上下文
 * @author: pxd
 * @create: 2019-03-22 16:43
 **/
@Component
public class ValidateCodeHandleContext {

    private final Map<String, ValidateCodeHandle> validateCodeHandleMap = new ConcurrentHashMap<>();

    public ValidateCodeHandleContext(Map<String, ValidateCodeHandle> validateCodeHandleMap) {
        // this.validationProvessorStrategyMap.clear();
        validateCodeHandleMap.forEach((k, v) -> this.validateCodeHandleMap.put(k, v));
    }


    public ValidateCodeHandle getCodeHandle(String typename) {
        return this.getValidateCodeHandleMap().get(typename);
    }


    public Map<String, ValidateCodeHandle> getValidateCodeHandleMap() {
        return this.validateCodeHandleMap;
    }


}
