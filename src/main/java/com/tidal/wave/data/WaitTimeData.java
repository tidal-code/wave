package com.tidal.wave.data;

import java.util.HashMap;
import java.util.Map;

public class WaitTimeData {
    private static final ThreadLocal<Map<String, String>> waitTime = ThreadLocal.withInitial(HashMap::new);

    public static void setWaitTime(WaitTime dataEnum, String value) {
        waitTime.get().put(dataEnum.getKey(), value);
    }


    public static String getWaitTime(WaitTime dataEnum) {
        return waitTime.get().get(dataEnum.getKey());
    }
}
