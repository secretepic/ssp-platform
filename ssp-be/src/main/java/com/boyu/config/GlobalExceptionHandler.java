package com.boyu.config;

import com.boyu.common.BsException;
import com.boyu.servlet.BsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理全局异常
     */
    @ExceptionHandler(Exception.class)
    public BsResponse handleException(Exception e) {
        log.error("全局异常捕获", e);
        if (e instanceof BsException) {
            BsResponse error = BsResponse.error();
            if (((BsException) e).getCode() != null) {
                error.put("code", ((BsException) e).getCode());
            }
            error.put("message", e.getMessage());
            error.put("success", false);
            return error;
        }
        return BsResponse.error();
    }
}
