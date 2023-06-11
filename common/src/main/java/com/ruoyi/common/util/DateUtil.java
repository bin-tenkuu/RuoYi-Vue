package com.ruoyi.common.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Locale;

import static java.time.temporal.ChronoField.*;

/**
 * @author bin
 * @version 1.0.0
 * @since 2023/6/10
 */
public class DateUtil {
    /**
     * yyyyMMddHHmmss
     */
    public static final DateTimeFormatter YYYYMMDDHHMMSS = new DateTimeFormatterBuilder()
            .appendValue(YEAR, 4)
            .appendValue(MONTH_OF_YEAR, 2)
            .appendValue(DAY_OF_MONTH, 2)
            .optionalStart()
            .parseLenient()
            .appendValue(HOUR_OF_DAY, 2)
            .appendValue(MINUTE_OF_HOUR, 2)
            .appendValue(SECOND_OF_MINUTE, 2)
            .parseStrict()
            .toFormatter(Locale.getDefault());
    /**
     * yyyy/MM/dd
     */
    public static final DateTimeFormatter YYYYMMDD = new DateTimeFormatterBuilder()
            .appendValue(YEAR, 4)
            .appendLiteral("/")
            .appendValue(MONTH_OF_YEAR, 2)
            .appendLiteral("/")
            .appendValue(DAY_OF_MONTH, 2)
            .toFormatter(Locale.getDefault());

    public static String dateTimeNow() {
        return parseDateToStr(YYYYMMDDHHMMSS, LocalDateTime.now());
    }

    public static String dateTimeNow(final DateTimeFormatter format) {
        return parseDateToStr(format, LocalDateTime.now());
    }

    public static String parseDateToStr(final String format, final LocalDateTime date) {
        return parseDateToStr(DateTimeFormatter.ofPattern(format), date);
    }

    public static String parseDateToStr(final DateTimeFormatter format, final LocalDateTime date) {
        return format.format(date);
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static String datePath() {
        return parseDateToStr(YYYYMMDD, LocalDateTime.now());
    }

    /**
     * 增加 LocalDateTime ==> Date
     */
    public static Date toDate(LocalDateTime time) {
        return Timestamp.valueOf(time);
    }

    /**
     * 增加 LocalDate ==> Date
     */
    public static Date toDate(LocalDate temporalAccessor) {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.MIN);
        return toDate(localDateTime);
    }
}
