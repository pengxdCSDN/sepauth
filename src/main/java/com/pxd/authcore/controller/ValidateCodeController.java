package com.pxd.authcore.controller;

import com.pxd.authcore.handle.imgcode.ImgValidateCodeDTO;
import com.pxd.authcore.handle.imgcode.ImgValidateCodeHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 验证码controller
 * @author: pxd
 * @create: 2019-03-25 10:10
 **/
@RestController
@RequestMapping(value = "/validate")
public class ValidateCodeController {

    @Autowired
    private ImgValidateCodeHandle imgValidateCodeHandle;

    @GetMapping(value = "/create")
    public ImgValidateCodeDTO create(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return imgValidateCodeHandle.create(new ServletWebRequest(request, response));
    }

}
