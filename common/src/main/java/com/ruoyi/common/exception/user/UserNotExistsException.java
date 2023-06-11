package com.ruoyi.common.exception.user;

import com.ruoyi.common.exception.ResultException;

/**
 * 用户不存在异常类
 *
 * @author ruoyi
 */
public class UserNotExistsException extends ResultException {

    public UserNotExistsException() {
        super(401, "用户不存在/密码错误");
    }
}
