package edu.uwaterloo.lee.joohan;

import edu.uwaterloo.lee.joohan.AllJsReader;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] argv) {
        Map<Integer, Integer> buildIdAndToggleCount = new HashMap<>();

        AllJsReader allJsReader = new AllJsReader();
        StaticPrefListHReader staticPrefListHReader = new StaticPrefListHReader();

        for (int i = 57; i < 99; i++) {
            int count = allJsReader.getNumOfToggles(i);
            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += count);
            buildIdAndToggleCount.putIfAbsent(i, count);
        }

        for (int i = 61; i < 70; i++) {
            int result = staticPrefListHReader.getNumOfToggles(i);
            buildIdAndToggleCount.putIfAbsent()
            System.out.println("Count: " + result);
        }

    }
}
