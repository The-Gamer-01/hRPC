package com.hyx.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RPC引用，注入服务消费者.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/6 17:42
 **/

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
public @interface RpcReference {

    /**
     * 服务版本.
     */
    String version() default "";

    /**
     * 服务组名称.
     */
    String group() default "";
}
