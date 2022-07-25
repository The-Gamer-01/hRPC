package com.hyx.extension;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SPI注解.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/3/31 0:36
 **/

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {
    /**
     * 默认extension名称.
     */
    String value() default "";
}
