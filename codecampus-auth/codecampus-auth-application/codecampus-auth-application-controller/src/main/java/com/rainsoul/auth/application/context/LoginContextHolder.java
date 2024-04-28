package com.rainsoul.auth.application.context;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 登录上下文对象
 *
 * 这个类用于在应用程序中保存登录相关的信息，并在线程之间进行传递。
 * 主要功能包括设置、获取和移除登录上下文中的数据。
 *
 * @author: ChickenWing
 * @date: 2023/11/26
 */
public class LoginContextHolder {

    // 使用InheritableThreadLocal来保存登录上下文信息，使其能在子线程中继承父线程的上下文
    private static final InheritableThreadLocal<Map<String, Object>> THREAD_LOCAL
            = new InheritableThreadLocal<>();

    /**
     * 设置登录上下文中的数据
     * @param key 数据键
     * @param val 数据值
     */
    public static void set(String key, Object val) {
        Map<String, Object> map = getThreadLocalMap();
        map.put(key, val);
    }

    /**
     * 获取登录上下文中指定键的数据
     * @param key 数据键
     * @return 数据值
     */
    public static Object get(String key){
        Map<String, Object> threadLocalMap = getThreadLocalMap();
        return threadLocalMap.get(key);
    }

    /**
     * 获取登录用户的ID
     * @return 登录用户的ID
     */
    public static String getLoginId(){
        return (String) getThreadLocalMap().get("loginId");
    }

    /**
     * 移除登录上下文
     */
    public static void remove(){
        THREAD_LOCAL.remove();
    }

    /**
     * 获取当前线程的登录上下文Map，如果不存在则创建一个新的Map并存储在ThreadLocal中
     * @return 登录上下文Map
     */
    public static Map<String, Object> getThreadLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (Objects.isNull(map)) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }
}
