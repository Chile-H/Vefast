package com.github.chileh.model.commom.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AppHttpCodeEnum {
    // region 2xx Success
    SUCCESS(200, "操作成功"),
    // endregion

    // region 4xx Client Errors
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "身份未认证"),
    FORBIDDEN(403, "无操作权限"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "不支持的请求方法"),

    // Authentication (407-410)
    NEED_LOGIN(407, "需要登录后操作"),
    LOGIN_FAILED(408, "登录失败"),
    INVALID_CREDENTIALS(409, "用户名或密码错误"),
    ACCOUNT_LOCKED(410, "账号已被锁定"),

    // Token (411-415)
    TOKEN_INVALID(411, "无效的访问令牌"),
    TOKEN_EXPIRED(412, "访问令牌已过期"),
    TOKEN_REQUIRED(413, "缺少访问令牌"),

    // Validation (416-420)
    PARAM_MISSING(416, "缺少必要参数"),
    PARAM_INVALID(417, "参数格式错误"),
    IMAGE_FORMAT_INVALID(418, "不支持的图片格式"),
    // endregion

    // region 5xx Server Errors
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂不可用"),
    // endregion

    // region Business Errors (6000+)
    DATA_CONFLICT(6001, "数据已存在"),
    DATA_NOT_FOUND(6002, "数据不存在"),
    OPERATION_LIMITED(6003, "操作频率过高"),
    // endregion
    ;

    private final int code;
    private final String message;

    AppHttpCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // 根据code查找枚举
    public static AppHttpCodeEnum getByCode(int code) {
        return Arrays.stream(values())
                .filter(e -> e.code == code)
                .findFirst()
                .orElse(INTERNAL_SERVER_ERROR);
    }
}