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
public @interface ExcelDicts {
    ExcelDict[] value() default {};

    ExcelDict defaultDict() default @ExcelDict(key = "0", value = "未知");
}
