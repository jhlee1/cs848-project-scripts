package edu.uwaterloo.lee.joohan;

import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] argv) {
        Map<Integer, Integer> countOnEachRelease = ToggleCounter.getTotalOnEachRelease();

        System.out.println(countOnEachRelease.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList()));




    }
}
