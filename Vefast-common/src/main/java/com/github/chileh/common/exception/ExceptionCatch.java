package com.github.chileh.common.exception;

import com.github.chileh.model.commom.dtos.ResponseResult;
import com.github.chileh.model.commom.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionCatch {

    /**
     * 处理不可控异常
     * @param e 异常对象
     * @return 响应结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public <T> ResponseResult<T> exception(Exception e){
        log.error("catch exception:{}",e.getMessage(), e);

        return ResponseResult.error(AppHttpCodeEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理可控异常  自定义异常
     * @param e 自定义异常对象
     * @return 响应结果
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public <T> ResponseResult<T> exception(CustomException e){
        log.error("catch exception: {}", e.getMessage(), e);
        return ResponseResult.error(e.getAppHttpCodeEnum());
    }
}
