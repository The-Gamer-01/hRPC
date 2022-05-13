package com.hyx.annotation;

import java.lang.annotation.*;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className RpcReference
 * @description RPC引用，注入服务消费者
 * @date 2022/5/6 17:42
 **/

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {

    /**
     * 服务版本
     */
    String version() default "";

    /**
     * 服务组名称
     */
    String group() default "";
}
