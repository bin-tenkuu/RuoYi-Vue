package com.ruoyi.common.exception.user;

import com.ruoyi.common.exception.ResultException;

/**
 * 用户错误最大次数异常类
 *
 * @author ruoyi
 */
public class UserPasswordRetryLimitExceedException extends ResultException {

    public UserPasswordRetryLimitExceedException(int retryLimitCount, int lockTime) {
        super(401, String.format("密码输入错误%s次，帐户锁定%s分钟", retryLimitCount, lockTime));
    }
}
