package edu.uwaterloo.lee.joohan;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] argv) {
//        Map<Integer, Integer> countOnEachRelease = ToggleCounter.getTotalOnEachRelease();
//        System.out.println(
//                countOnEachRelease.entrySet()
//                        .stream()
//                        .sorted(Map.Entry.comparingByKey())
//                        .collect(Collectors.toList())
//        );

//        Set<String> nightlybuildStrings = new HashSet<>();
//        for (int i = 70; i < 99; i++) {
//            nightlybuildStrings.addAll(DevToggleCounter.getUniqueNightlyBuildLine(i));
//        }

        DevToggleCounter.getTotalOnEachRelease();

//        System.out.println(String.join("\n", nightlybuildStrings));




    }
}
