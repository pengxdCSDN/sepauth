package com.pxd.authcore.handle;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: 验证码信息DTO
 * @author: pxd
 * @create: 2019-03-22 10:23
 **/

@Data
public class ValidateCodeDTO implements Serializable {

    private String code;

    private String codeId;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;

    public ValidateCodeDTO(String code, int expireIn, String codeId) {
        this.code = code;
        this.codeId = codeId;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCodeDTO(String code, LocalDateTime expireTime, String codeId) {
        this.code = code;
        this.codeId = codeId;
        this.expireTime = expireTime;
    }

 /*   public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }*/


}
