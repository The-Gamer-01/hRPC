package com.hyx.annotation;

import com.hyx.spring.CustomScannerRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * RpcService扫描接口.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/9 22:07
 **/

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomScannerRegister.class)
@Documented
public @interface RpcScan {
    
    /**
     * 基本扫描路径.
     */
    String[] basePackage();
}
