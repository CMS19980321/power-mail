package com.hncu.ex.handler;

import com.hncu.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author caimeisahng
 * @Date 2025/11/2 19:50
 * @Version 1.0
 * 全局异常处理类
 */

/**
 * @RestControllerAdvice注解表明这是一个全局的REST控制器异常处理器类，
 * 它能够处理所有带有@RestController注解的控制器抛出的异常。
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    @ExceptionHandler(BusinessException.class)
    public Result<String> businessException(BusinessException e){
        log.error(e.getMessage());
        return Result.fail(BusinessEnum.OPERATION_FAIL.getCode(),e.getMessage());
    }

    /**
     * @ExceptionHandler(RuntimeException.class)注解指定了此方法用
     * 于处理RuntimeException类型的异常。
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<String> runtimeException(RuntimeException e){
        log.error(e.getMessage());
        return Result.fail(BusinessEnum.SERVER_INNER_ERROR);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> accessDeniedException(AccessDeniedException e){
        log.error(e.getMessage());
        /**
         * 重新抛出异常。这可能意味着在捕获到AccessDeniedException时，不返回任何响应给用户，
         * 而是让Spring Security框架处理这个异常（通常会返回403 Forbidden响应）
         */
        throw e;

    }

}
