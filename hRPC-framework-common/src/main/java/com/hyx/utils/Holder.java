package com.hyx.utils;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className Holder
 * @description 帮助持有类，其dubbo中的实现主要是保证容器中存储的是泛型，比较方便；
 * 否则直接Object会很不方便维护
 * @date 2022/3/31 0:40
 **/

public class Holder<T> {
    private volatile T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}
