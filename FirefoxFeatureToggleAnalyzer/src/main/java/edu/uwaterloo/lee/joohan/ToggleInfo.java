package edu.uwaterloo.lee.joohan;

import java.util.HashMap;
import java.util.Map;

public class ToggleInfo {
    String name;
    Map<Integer, ToggleStatus> buildIdAndStatus;

    public ToggleInfo(String name) {
        this.name = name;
        buildIdAndStatus = new HashMap<>();
    }

    public void addBuildInfo(int buildId, ToggleStatus status) {
        buildIdAndStatus.put(buildId, status);
    }

    public int getLifeSpan(ToggleStatus status) {
        int max = buildIdAndStatus.entrySet().stream()
                .filter(e -> e.getValue().equals(status))
                .map(Map.Entry::getKey)
                .max(Integer::compareTo)
                .orElse(-1);

        if (max < 0) {
            return max;
        }

        int min = buildIdAndStatus.entrySet().stream()
                .filter(e -> e.getValue().equals(status))
                .map(Map.Entry::getKey)
                .min(Integer::compareTo)
                .orElse(-1);

        return max - min;
    }

    public ToggleStatus getStatus(int buildId) {
        return buildIdAndStatus.get(buildId);
    }


    @Override
    public String toString() {
        return "name: " + name + " status: " + buildIdAndStatus;
    }


}

