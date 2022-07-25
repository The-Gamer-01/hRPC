package com.hyx.factory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取单例对象的工厂类.
 * @author 黄乙轩
 * @version 1.0
 * @date 2022/5/9 22:34
 **/

public class SingletonFactory {
    /**
     * 存储类名与单例对象之间的映射.
     */
    private static final Map<String, Object> OBJECT_MAP = new ConcurrentHashMap<>();

    private SingletonFactory() {}

    public static <T> T getInstance(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException();
        }
        String key = clazz.toString();
        if (OBJECT_MAP.containsKey(key)) {
            return clazz.cast(OBJECT_MAP.get(key));
        } else {
            return clazz.cast(OBJECT_MAP.computeIfAbsent(key, k -> {
                try {
                    return clazz.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }));
        }
    }
}
