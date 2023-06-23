package com.ruoyi.common.exception.file;

import com.ruoyi.common.exception.ResultException;

/**
 * 文件名称超长限制异常类
 *
 * @author ruoyi
 */
public class FileNameLengthLimitExceededException extends ResultException {

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super(414, String.format("上传的文件名最长%s个字符", defaultFileNameLength));
    }
}
