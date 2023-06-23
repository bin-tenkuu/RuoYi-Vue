package com.ruoyi.common.util;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.constant.Constants;

import java.util.*;

/**
 * 字符串工具类
 *
 * @author ruoyi
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 是否为http(s)://开头
     *
     * @param link 链接
     * @return 结果
     */
    public static boolean ishttp(String link) {
        return StrUtil.startWithAny(link, Constants.HTTP, Constants.HTTPS);
    }

    /**
     * 数字左边补齐0，使之达到指定长度。注意，如果数字转换为字符串后，长度大于size，则只保留 最后size个字符。
     *
     * @param num  数字对象
     * @param size 字符串指定长度
     * @return 返回数字的字符串格式，该字符串为指定长度。
     */
    public static String padl(final Number num, final int size) {
        final String s = num.toString();
        if (size <= 0) {
            return "";
        }
        char[] chars = new char[size];
        if (s != null) {
            final int len = s.length();
            if (s.length() <= size) {
                Arrays.fill(chars, 0, size - len, '0');
                s.getChars(0, len, chars, size - len);
            } else {
                return s.substring(len - size, len);
            }
        } else {
            Arrays.fill(chars, '0');
        }
        final StringBuilder sb = new StringBuilder(size);
        if (s != null) {
            final int len = s.length();
            sb.append(String.valueOf('0').repeat(size - len));
            sb.append(s);
        } else {
            sb.append(String.valueOf('0').repeat(size));
        }
        return sb.toString();
    }

}
