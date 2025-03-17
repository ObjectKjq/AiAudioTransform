package com.kjq.common;

/**
 * 自定义状态码码
 */
public enum ErrorCode {

    PARAMS_ERROR(40000, "请求参数错误！"),
    PARAMS_INCONSISTENCY(4000_0001, "两次密码不一致！"),
    USERNAME_OR_PARAMS_ERROR(4000_0002, "用户名或密码错误！"),

    NOT_LOGIN_ERROR(40100, "未登录！"),
    NO_AUTH_ERROR(40101, "无权限！"),
    TOKEN_ERROR(40102, "未登录！"),
    NOT_FOUND_ERROR(40400, "请求数据不存在！"),
    FORBIDDEN_ERROR(40300, "禁止访问！"),
    SYSTEM_ERROR(50000, "系统异常！"),
    OPERATION_ERROR(50001, "操作失败！");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
