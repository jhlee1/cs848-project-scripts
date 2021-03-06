package edu.uwaterloo.lee.joohan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] argv) {
        Map<Integer, Integer> countOnEachRelease = ToggleCounter.getTotalOnEachRelease();
        System.out.println(
                countOnEachRelease.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toList())
        );


        Map<String, ToggleInfo> toggles = ToggleLifeCycleTracker.getLifeCycleDevToggles();

        double devLifespan = toggles.values()
                .stream()
                .map(info -> info.getLifeSpan(ToggleStatus.DEV))
                .filter(i -> i > 0)
                .mapToInt(Integer::intValue)
                .average()
                .orElseThrow(() -> new AssertionError("Error while getting lifespan of dev toggles."));

        double releaseLifespan = toggles.values()
                .stream()
                .map(info -> info.getLifeSpan(ToggleStatus.RELEASE))
                .filter(i -> i > 0)
                .mapToInt(Integer::intValue)
                .average()
                .orElseThrow(() -> new AssertionError("Error while getting lifespan of dev toggles."));

        System.out.println("Dev lifespan:" + devLifespan + " Release life span: " + releaseLifespan);

        Map<Integer, Long> devToggleNumOnEachBuild = new HashMap<>();
        Map<Integer, Long> releaseToggleNumOnEachBuild = new HashMap<>();

        for (int i = 57; i < 99; i++) {
            final int buildId = i;
            long devCount = toggles.values()
                    .stream()
                    .filter(toggleInfo -> ToggleStatus.DEV == toggleInfo.getStatus(buildId))
                    .count();
            long releaseCount = toggles.values()
                    .stream()
                    .filter(toggleInfo -> ToggleStatus.RELEASE == toggleInfo.getStatus(buildId))
                    .count();
            releaseToggleNumOnEachBuild.put(buildId, releaseCount);
            devToggleNumOnEachBuild.put(buildId, devCount);
        }

        System.out.println(devToggleNumOnEachBuild.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList()));
        System.out.println(releaseToggleNumOnEachBuild.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList()));


        System.out.println(ToggleCounter.getLifespan());

    }
}
