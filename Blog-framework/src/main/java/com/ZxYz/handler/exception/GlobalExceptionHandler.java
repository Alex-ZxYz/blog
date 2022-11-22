package com.ZxYz.handler.exception;


import com.ZxYz.domain.ResponseResult;
import com.ZxYz.enums.AppHttpCodeEnum;
import com.ZxYz.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult<?> systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现异常: {}",e.getMsg());
        //获取异常返回
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult<?> exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现异常: {}",e.getMessage());
        //获取异常返回
        return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), e.getMessage());
    }
}
