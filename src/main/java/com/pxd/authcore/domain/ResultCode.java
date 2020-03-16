package com.pxd.authcore.domain;

public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAILURE(500, "系统出现问题,请联系开发商！"),
    UNAUTHORIZED(401, "未授权！"),
    FORBIDDEN(403, "没有权限，拒绝访问！"),
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
    PARAM_NOT_COMPLETE(10004, "参数缺失"),
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码,验证码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),
    USER_LOGIN_EMPTY(20006, "帐号或密码不能为空"),
    USER_YZM_ERROR(20007, "验证码错误"),
    USER_YZM_NOT_EXISTED(20008, "验证码已失效"),
    USER_HAS_SAMEEXISTED_USEID(20009, "已存在相同用户名，请用ID登录!"),
    SPECIFIED_QUESTIONED_USER_NOT_EXIST(30001, "某业务出现问题"),
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),
    RESULE_DATA_NONE(50001, "数据未找到"),
    DATA_IS_WRONG(50002, "数据有误"),
    DATA_ALREADY_EXISTED(50003, "数据已存在"),
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),
    PERMISSION_NO_ACCESS(70001, "无访问权限");

    private Integer code;
    private String message;

    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        ResultCode[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ResultCode item = var1[var3];
            if (item.name().equals(name)) {
                return item.message;
            }
        }

        return name;
    }

    public static Integer getCode(String name) {
        ResultCode[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ResultCode item = var1[var3];
            if (item.name().equals(name)) {
                return item.code;
            }
        }

        return null;
    }

    public String toString() {
        return this.name();
    }
}
