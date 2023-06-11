package com.ruoyi.common.exception.file;

import com.ruoyi.common.exception.ResultException;
import com.ruoyi.common.util.MessageUtils;

/**
 * 文件名大小限制异常类
 *
 * @author ruoyi
 */
public class FileSizeLimitExceededException extends ResultException {

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super(413, String.format("上传的文件大小超出限制的文件大小！<br/>允许的文件最大大小是：%sMB！", defaultMaxSize));
    }
}
