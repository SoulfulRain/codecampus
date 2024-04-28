package com.rainsoul.auth.context;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 登录上下文对象，用于存储和管理登录相关的信息
 */
public class LoginContextHolder {

    // 使用InheritableThreadLocal以支持线程间的上下文传递
    private static final InheritableThreadLocal<Map<String, Object>> THREAD_LOCAL
            = new InheritableThreadLocal<>();

    // 设置指定key对应的值
    public static void set(String key, Object val) {
        Map<String, Object> map = getThreadLocalMap();
        map.put(key, val);
    }

    // 获取指定key对应的值
    public static Object get(String key){
        Map<String, Object> threadLocalMap = getThreadLocalMap();
        return threadLocalMap.get(key);
    }

    // 获取登录ID
    public static String getLoginId(){
        return (String) getThreadLocalMap().get("loginId");
    }

    // 清除上下文中的信息
    public static void remove(){
        THREAD_LOCAL.remove();
    }

    // 获取当前线程的上下文Map，若不存在则创建并设置
    public static Map<String, Object> getThreadLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (Objects.isNull(map)) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }
}
