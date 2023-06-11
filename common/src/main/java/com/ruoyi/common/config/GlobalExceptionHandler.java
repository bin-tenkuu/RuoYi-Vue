package com.ruoyi.common.config;

import com.ruoyi.common.exception.ResultException;
import com.ruoyi.common.model.R;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

/**
 * @author bin
 * @version 1.0.0
 * @since 2023/6/10
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public R<String> doResultException(Throwable ex, HttpServletRequest request) {
        // 参数校验异常
        if (ex instanceof BindException) {
            val e = (BindException) ex;
            val exceptions = e.getBindingResult();
            // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
            if (exceptions.hasErrors()) {
                val fieldError = exceptions.getFieldError();
                if (fieldError != null) {
                    return R.fail(String.format("'%s.%s' %s",
                            fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage()
                    ));
                }
            }
            return R.fail(ex.getMessage());
        }
        // 自定义异常
        if (ex instanceof ResultException) {
            val e = (ResultException) ex;
            return R.fail(e.getCode(), e.getMessage());
        }
        String requestURI = request.getRequestURI();
        // 权限校验异常
        if (ex instanceof org.springframework.security.access.AccessDeniedException) {
            val e = (org.springframework.security.access.AccessDeniedException) ex;
            log.error("请求地址'{}',权限校验失败", requestURI, e);
            return R.fail(HttpStatus.FORBIDDEN.value(), "没有权限，请联系管理员授权");
        }
        // 请求方式不支持
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            val e = (HttpRequestMethodNotSupportedException) ex;
            log.warn("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
            return R.fail(e.getMessage());
        }
        if (ex instanceof AccessDeniedException) {
            return R.fail("拒绝访问文件");
        }
        if (ex instanceof NullPointerException) {
            log.error("空指针异常", ex);
            return R.fail("空指针异常");
        }
        if (ex instanceof IllegalArgumentException) {
            log.error("非法参数异常", ex);
            return R.fail("非法参数异常");
        }
        log.error("请求地址'{}',发生未知异常.", requestURI, ex);
        return R.fail(ex.getMessage());
    }

}
