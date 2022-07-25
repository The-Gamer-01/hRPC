package com.hyx.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;

/**
 * 普通扫描类.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/9 22:17
 **/

public class CustomScanner extends ClassPathBeanDefinitionScanner {

    public CustomScanner(BeanDefinitionRegistry registry, Class<? extends Annotation> annotation) {
        super(registry);
        super.addIncludeFilter(new AnnotationTypeFilter(annotation));
    }

    @Override
    public int scan(String... basePackages) {
        return super.scan(basePackages);
    }
}
