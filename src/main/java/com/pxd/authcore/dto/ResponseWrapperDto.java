package com.pxd.authcore.dto;


import com.pxd.authcore.domain.ResultCode;

import java.io.Serializable;

public class ResponseWrapperDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean success;
    private String msg;
    private Object data;
    private Long total;
    private Integer code;

    public ResponseWrapperDto() {
    }


    public ResponseWrapperDto(String msg, ResultCode resultCode) {
        this.msg = msg;
        this.code = resultCode.code();
    }

    public ResponseWrapperDto(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public ResponseWrapperDto(boolean success, String msg, Object data, Long total) {
        this.success = success;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }


    public ResponseWrapperDto(boolean success, String msg, Object data, Long total, Integer code) {
        this.success = success;
        this.msg = msg;
        this.data = data;
        this.total = total;
        this.code = code;
    }

    public ResponseWrapperDto(boolean success, ResultCode resultCode) {
        this.success = success;
        this.code = resultCode.code();
        this.msg = resultCode.message();
    }

    public ResponseWrapperDto(boolean success, ResultCode resultCode, Object data) {
        this.success = success;
        this.code = resultCode.code();
        this.msg = resultCode.message();
        this.data = data;
    }

    public ResponseWrapperDto(boolean success, ResultCode resultCode, Object data, Long total) {
        this.success = success;
        this.code = resultCode.code();
        this.msg = resultCode.message();
        this.data = data;
        this.total = total;
    }

    public ResponseWrapperDto(boolean success, String msg, Integer code) {
        this.success = success;
        this.msg = msg;
        this.code = code;
    }

    public static long getSerialVersionUID() {
        return 1L;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResponseWrapperDto{code=" + this.code + ", success=" + this.success + ", msg='" + this.msg + '\'' + ", total=" + this.total + ", data=" + this.data + '}';
    }
}
