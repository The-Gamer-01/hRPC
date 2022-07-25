package com.hyx.extension;

import com.hyx.utils.Holder;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 黄乙轩
 * @version 1.0
 * @className ExtensionLoader
 * @description SPI的ExtensionLoader
 * @date 2022/3/31 0:36
 **/

@Slf4j
public class ExtensionLoader<T> {

    private static final ConcurrentHashMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();

    private static final ConcurrentMap<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<Class<?>, Object>();
    /**
     * 路径扫描前缀
     */
    private static final String RPC_INTERNAL_DIRECTORY = "META-INF/hrpc/";

    /**
     * 读取的对象
     */
    private final Class<?> type;

    private ExtensionLoader(Class<?> type) {
        this.type = type;
    }

    /**
     * 实例缓存
     */
    private final ConcurrentHashMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();

    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();

    /**
     * 获取ExtensionLoader
     * @param type 需要的Class
     * @param <S> 对应的Class
     * @return
     */
    public static <S> ExtensionLoader<S> getExtensionLoader(Class<S> type) {
        //type不能为空
        if(type == null) {
            throw new IllegalArgumentException("Extension type == null");
        }
        //@SPI修饰的是接口
        if(!type.isInterface()) {
            throw new IllegalArgumentException("Extension type(" + type + ") is not interface");
        }
        //判断是否被@SPI修饰
        if(!withExtensionAnnotation(type)) {
            throw new IllegalArgumentException("Extension type(" + type + ") is not extension, because WITHOUT @" + SPI.class.getSimpleName());
        }
        //获取clazz对应的Extension
        ExtensionLoader<S> loader = (ExtensionLoader<S>) EXTENSION_LOADERS.get(type);
        if(loader == null) {
            //第一次加载
            EXTENSION_LOADERS.putIfAbsent(type, new ExtensionLoader<>(type));
            loader = (ExtensionLoader<S>) EXTENSION_LOADERS.get(type);
        }
        return loader;
    }

    /**
     * 判断是否被@SPI修饰
     * @param type 需要判断的Class
     * @return
     */
    private static <T> boolean withExtensionAnnotation(Class<T> type) {
        return type.isAnnotationPresent(SPI.class);
    }

    /**
     * 获取实例
     * @param name 需要获取的实例的映射名
     * @return 所需实例
     */
    public T getExtension(String name) {
        if(name == null || name.length() == 0) {
            throw new IllegalArgumentException("Extension name == null");
        }
        //从缓存中取出Holder
        Holder<Object> holder = cachedInstances.get(name);
        //为空说明缓存中不存在，需要重新加载
        if(holder == null) {
            cachedInstances.putIfAbsent(name, new Holder<>());
            holder = cachedInstances.get(name);
        }
        //取出持有对象中的实例
        Object instance = holder.get();
        //双检锁创建实例
        if(instance == null) {
            synchronized (holder) {
                instance = holder.get();
                if(instance == null) {
                    instance = createExtension(name);
                    holder.set(instance);
                }
            }
        }
        return (T) instance;
    }

    private T createExtension(String name) {
        Class<?> clazz = getExtensionClasses().get(name);
        try {
            T instance = (T) EXTENSION_INSTANCES.get(clazz);
            if(instance == null) {
                EXTENSION_INSTANCES.putIfAbsent(clazz, clazz.newInstance());
                instance = (T) EXTENSION_INSTANCES.get(clazz);
            }
            return instance;
        } catch (Throwable t) {
            throw new IllegalStateException(name + "创建失败");
        }
    }

    private Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classes = cachedClasses.get();
        if(classes == null) {
            synchronized (cachedInstances) {
                classes = cachedClasses.get();
                if(classes == null) {
                    classes = loadExtensionClasses();
                    cachedClasses.set(classes);
                }
            }
        }
        return classes;
    }

    private Map<String, Class<?>> loadExtensionClasses() {
        Map<String, Class<?>> extensionClasses = new HashMap<>();
        loadDirectory(extensionClasses, RPC_INTERNAL_DIRECTORY);
        return extensionClasses;
    }

    private void loadDirectory(Map<String, Class<?>> extensionClasses, String dir) {
        String fileName = dir + type.getName();
        try {
            Enumeration<URL> urls;
            ClassLoader classLoader = findClassLoader();
            if(classLoader != null) {
                urls = classLoader.getResources(fileName);
            } else {
                urls = ClassLoader.getSystemResources(fileName);
            }
            if(urls != null) {
                while(urls.hasMoreElements()) {
                    URL resourceURL = urls.nextElement();
                    loadResource(extensionClasses, classLoader, resourceURL);
                }
            }
        } catch (Throwable t) {
            log.error("Exception when load extension class(interface: " +
                    type + ", description file: " + fileName + ")");
        }
    }

    private void loadResource(Map<String, Class<?>> extensionClasses, ClassLoader classLoader, URL resourceURL) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceURL.openStream()));
            try {
                String line;
                while((line = reader.readLine()) != null) {
                    line = line.trim();
                    if(line.length() > 0) {
                        try {
                            String name = null;
                            String val = null;
                            int index = line.indexOf('=');
                            if(index > 0) {
                                name = line.substring(0, index).trim();
                                val = line.substring(index + 1).trim();
                            }
                            if(val.length() > 0) {
                                loadClass(extensionClasses, resourceURL, Class.forName(val, true, classLoader), name);
                            }
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                reader.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    private static ClassLoader findClassLoader() {
        return ExtensionLoader.class.getClassLoader();
    }

    private void loadClass(Map<String, Class<?>> extensionClasses, URL resourceURL, Class clazz, String name) {
        try {
            //检查clazz是否有空构造函数
            clazz.getConstructor();
            Class<?> c = extensionClasses.get(name);
            if(c == null) {
                extensionClasses.put(name, clazz);
            } else if(c != clazz) {

            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
