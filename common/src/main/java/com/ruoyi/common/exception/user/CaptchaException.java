package com.ruoyi.common.exception.user;

import com.ruoyi.common.exception.ResultException;

/**
 * 验证码错误异常类
 *
 * @author ruoyi
 */
public class CaptchaException extends ResultException {
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super(401, "验证码错误");
    }
}
