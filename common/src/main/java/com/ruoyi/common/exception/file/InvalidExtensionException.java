package com.ruoyi.common.exception.file;

import com.ruoyi.common.exception.ResultException;

import java.util.Arrays;

/**
 * 文件上传 误异常类
 *
 * @author ruoyi
 */
public class InvalidExtensionException extends ResultException {

    public InvalidExtensionException(String[] allowedExtension, String extension, String filename) {
        super(415, "文件[" + filename + "]后缀[" + extension + "]不正确，请上传" + Arrays.toString(allowedExtension) + "格式");
    }

}
