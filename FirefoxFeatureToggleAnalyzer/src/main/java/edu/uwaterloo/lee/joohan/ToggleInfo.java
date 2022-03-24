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
        if (buildIdAndStatus.containsKey(buildId)) {
            System.out.println(name + " " + buildId + " " + status);
        }


        buildIdAndStatus.put(buildId, status);
    }


}

