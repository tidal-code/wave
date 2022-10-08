package com.tidal.wave.scenario;

import java.util.Collection;

public class ScenarioInfo {

    private static final ThreadLocal<String> scenarioName = new ThreadLocal<>();
    private static final ThreadLocal<Collection<String>> scenarioTags = new ThreadLocal<>();

    private ScenarioInfo() {
    }

    public static String getScenarioName() {
        return scenarioName.get().trim();
    }

    public static void setScenarioName(String name) {
        scenarioName.set(name);
    }

    public static Collection<String> getScenarioTags() {
        return scenarioTags.get();
    }

    public static void setScenarioTags(Collection<String> scenarioTags) {
        ScenarioInfo.scenarioTags.set(scenarioTags);
    }
}
