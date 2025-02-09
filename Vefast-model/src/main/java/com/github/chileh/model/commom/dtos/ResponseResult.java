package com.github.chileh.model.commom.dtos;

import com.github.chileh.model.commom.enums.AppHttpCodeEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;
    private Instant timestamp;

    // 提供受保护的无参构造器（满足子类/反序列化需求）
    protected ResponseResult() {
        this.timestamp = Instant.now();
    }

    // 私有化全参构造器
    private ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now();
    }

    // region 成功响应
    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return create(AppHttpCodeEnum.SUCCESS, data);
    }

    public static <T> ResponseResult<T> success(T data, String customMessage) {
        ResponseResult<T> result = create(AppHttpCodeEnum.SUCCESS, data);
        result.setMessage(customMessage);
        return result;
    }
    // endregion

    // region 错误响应
    public static <T> ResponseResult<T> error(AppHttpCodeEnum codeEnum) {
        return create(codeEnum, null);
    }

    public static <T> ResponseResult<T> error(AppHttpCodeEnum codeEnum, String customMessage) {
        ResponseResult<T> result = create(codeEnum, null);
        result.setMessage(customMessage);
        return result;
    }

    public static <T> ResponseResult<T> error(int code, String message) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    // endregion

    private static <T> ResponseResult<T> create(AppHttpCodeEnum codeEnum, T data) {
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(codeEnum.getCode());
        result.setMessage(codeEnum.getMessage());
        result.setData(data);
        return result;
    }
}
