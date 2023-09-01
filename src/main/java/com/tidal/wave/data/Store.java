package com.tidal.wave.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Store {
    private static final ThreadLocal<Map<String, Object>> data = ThreadLocal.withInitial(ConcurrentHashMap::new);

    private static final String testDataKey = "FrTWXyBbXyA";


    @SuppressWarnings("unchecked")
    public static void addTestData(String key, String value) {
        Object testData = data.get().get(testDataKey);
        if (testData == null) {
            data.get().put(testDataKey, new ConcurrentHashMap<String, String>());
        }
        ((Map<String, String>) data.get().get(testDataKey)).put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static String getTestData(String key) {
        return  ((Map<String, String>) data.get().get(testDataKey)).get(key);
    }

    public static void objectType(String key, Object object){
        data.get().put(key, object);
    }

    public static Object getTypeObject(String key){
        return data.get().get(key);
    }

    public static void clean(){
        data.remove();
    }


}
