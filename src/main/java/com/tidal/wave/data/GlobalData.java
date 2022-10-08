package com.tidal.wave.data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class GlobalData {
    private static final ThreadLocal<Map<String, String>> dataMap = ThreadLocal.withInitial(HashMap::new);
    private static final ThreadLocal<Map<String, Object>> objectDataMap = ThreadLocal.withInitial(LinkedHashMap::new);

    //So as not to instantiate.
    private GlobalData() {
    }

    public static void addObjectData(String expOption, Object value) {
        objectDataMap.get().put(expOption, value);
    }

    public static Map<String, Object> getObjectData() {
        return objectDataMap.get();
    }



    public static void addData(String key, String value) {
        dataMap.get().put(key, value);
    }

    public static void addData(DataEnum dataEnum, String value) {
        dataMap.get().put(dataEnum.getKey(), value);
    }

    public static String getData(String key) {
        return dataMap.get().get(key);
    }

    public static String getData(DataEnum dataEnum) {
        return dataMap.get().get(dataEnum.getKey());
    }

    public static Map<String, String> getData() {
        return dataMap.get();
    }

    protected static void clean() {
        dataMap.remove();
        objectDataMap.remove();
    }

    protected static void cleanTestData() {
        dataMap.get().clear();
    }

}
