package com.pxd.authcore.properties;

import lombok.Data;

/**
 * @description: 生成验证码的配置信息
 * @author: pxd
 * @create: 2019-03-22 16:20
 **/
@Data
public class ImageCodeProperties {


    //图形验证码的长
    private int width = 100;

    //图形验证码的宽
    private int height = 50;

    //验证码字符数
    private int codeCount = 5;

    //干扰元素个数
    private int lineCount = 150;

    //生效时间
    private int expireIn = 60;
}
