package com.hncu.ex.handler;

/**
 * @Author caimeisahng
 * @Date 2025/11/2 20:07
 * @Version 1.0
 * 自定义异常处理类
 */
public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
