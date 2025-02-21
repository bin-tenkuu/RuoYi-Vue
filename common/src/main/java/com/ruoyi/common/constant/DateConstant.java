package com.ruoyi.common.constant;

import java.time.format.DateTimeFormatter;

/**
 * @author bin
 * @since 2022/12/28
 */
public interface DateConstant {
    /**
     * 默认时间格式
     */
    String DATE_FORMAT = "yyyy-MM-dd";
    String TIME_FORMAT = "HH:mm:ss";
    String DATE_TIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;
    String SIMPLE_DATE_FORMAT = "yyyyMMdd";
    String SIMPLE_TIME_FORMAT = "HHmmss";
    String SIMPLE_DATE_TIME_FORMAT = SIMPLE_DATE_FORMAT + SIMPLE_TIME_FORMAT;

    /**
     * 默认时间格式化
     */
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    DateTimeFormatter SIMPLE_DATE_FORMATTER = DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT);
    DateTimeFormatter SIMPLE_TIME_FORMATTER = DateTimeFormatter.ofPattern(SIMPLE_TIME_FORMAT);
    DateTimeFormatter SIMPLE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(SIMPLE_DATE_TIME_FORMAT);

}
