package com.ruoyi.common.exception.user;

import com.ruoyi.common.exception.ResultException;

/**
 * 验证码失效异常类
 * 
 * @author ruoyi
 */
public class CaptchaExpireException extends ResultException {
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException()
    {
        super(401,"验证码已失效");
    }
}
