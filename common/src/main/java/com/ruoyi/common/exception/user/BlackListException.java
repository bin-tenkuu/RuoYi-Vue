package com.ruoyi.common.exception.user;

import com.ruoyi.common.exception.ResultException;

/**
 * 黑名单IP异常类
 *
 * @author ruoyi
 */
public class BlackListException extends ResultException {

    public BlackListException() {
        super(403, "很遗憾，访问IP已被列入系统黑名单");
    }
}
