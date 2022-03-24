package edu.uwaterloo.lee.joohan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

        Map<Integer, Integer> countDevOnEachRelease = DevToggleCounter.getTotalOnEachRelease();
        System.out.println(
                countDevOnEachRelease.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toList())
        );

        Map<Integer, Integer> countReleaseOnEachRelease = ReleaseToggleCounter.getTotalOnEachRelease();
        System.out.println(
                countReleaseOnEachRelease.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toList())
        );
        Map<String, ToggleInfo> toggles = ToggleLifeCycleTracker.getLifeCycleDevToggles();

        System.out.println(new ArrayList<>(toggles.entrySet()));



    }
}
