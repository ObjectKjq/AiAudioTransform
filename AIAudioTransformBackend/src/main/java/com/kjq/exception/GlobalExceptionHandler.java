package com.kjq.exception;

import com.kjq.common.BaseResponse;
import com.kjq.common.ErrorCode;
import com.kjq.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public BaseResponse<?> businessExceptionHandler(ServiceException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        ErrorCode errorCode = ErrorCode.SYSTEM_ERROR;
        return ResultUtils.error(errorCode.getCode(), errorCode.getMessage());
    }
}
