package com.pxd.authcore.handle.imgcode;

import com.pxd.authcore.handle.ValidateCodeDTO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 图片验证码DTO
 * @author: pxd
 * @create: 2019-03-22 10:26
 **/
@Data
public class ImgValidateCodeDTO extends ValidateCodeDTO {

    //private BufferedImage image;

    private String base64Img;

    public ImgValidateCodeDTO(String code, String base64Img, int expireIn, String codeId) {
        super(code, expireIn, codeId);
        this.base64Img = base64Img;
    }

    public ImgValidateCodeDTO(String code, String base64Img, LocalDateTime expireTime, String codeId) {
        super(code, expireTime, codeId);
        this.base64Img = base64Img;
    }
}
