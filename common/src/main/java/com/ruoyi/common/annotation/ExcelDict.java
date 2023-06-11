package com.ruoyi.common.annotation;

import java.lang.annotation.*;

/**
 * @author bin
 * @version 1.0.0
 * @since 2023/6/10
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ExcelDicts.class)
public @interface ExcelDict {
    String key();

    String value();
}
