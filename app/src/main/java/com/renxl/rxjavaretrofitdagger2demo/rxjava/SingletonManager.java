package com.renxl.rxjavaretrofitdagger2demo.rxjava;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by renxl
 * On 2017/7/11 9:00.
 */

public class SingletonManager {
    private static final Map<String, Object> objMap = new HashMap<>();

    private SingletonManager() {
    }

    public static void putObj(String key, Object value) {
        if (objMap.containsKey(key)) return;
        objMap.put(key, value);
    }

    public static Object getObj(String key) {
        return objMap.get(key);
    }
}
