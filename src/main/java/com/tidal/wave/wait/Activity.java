package com.tidal.wave.wait;

public enum Activity {
    ALL_ACTIVITIES_CHECK("All Background Activities"),
    DOC_READY_CHECK("Doc Ready Check"),
    ANGULAR_CHECK("Angular Requests"),
    ANGULAR_5_CHECK("Angular 5 Requests"),
    JQUERY_LOAD_WAITER("JQUERY Load Waiter");

    private final String activityType;

    Activity(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityType() {
        return activityType;
    }
}
