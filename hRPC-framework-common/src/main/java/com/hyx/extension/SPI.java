package com.hyx.extension;

import java.lang.annotation.*;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className SPI
 * @description SPI注解
 * @date 2022/3/31 0:36
 **/

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {
    /**
     * 默认extension名称
     */
    String value() default "";
}
