package com.hyx.annotation;

import com.hyx.spring.CustomScannerRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className RpcScan
 * @description RpcService扫描接口
 * @date 2022/5/9 22:07
 **/

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomScannerRegister.class)
@Documented
public @interface RpcScan {
    String[] basePackage();
}
