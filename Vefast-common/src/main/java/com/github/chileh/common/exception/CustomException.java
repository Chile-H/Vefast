package com.github.chileh.common.exception;

import com.github.chileh.model.commom.enums.AppHttpCodeEnum;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final AppHttpCodeEnum appHttpCodeEnum;

    public CustomException(AppHttpCodeEnum appHttpCodeEnum){
        this.appHttpCodeEnum = appHttpCodeEnum;
    }
}
