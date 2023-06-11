package com.ruoyi.common.exception.user;

import com.ruoyi.common.exception.ResultException;

/**
 * 用户密码不正确或不符合规范异常类
 *
 * @author ruoyi
 */
public class UserPasswordNotMatchException extends ResultException {

    public UserPasswordNotMatchException() {
        super(401, "用户不存在/密码错误");
    }
}
