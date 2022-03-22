package edu.uwaterloo.lee.joohan;

import java.util.HashMap;
import java.util.Map;

public class DevToggleCounter {
    public static Map<Integer, Integer> getTotalOnEachRelease() {
        Map<Integer, Integer> buildIdAndToggleCount = new HashMap<>();

        for (int i = 57; i < 99; i++) {
            final int countFromAll = countAllJs(i);
            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += countFromAll);
            buildIdAndToggleCount.putIfAbsent(i, countFromAll);

            final int countFromFirefox = countFirefoxJs(i);
            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += countFromFirefox);
            buildIdAndToggleCount.putIfAbsent(i, countFromFirefox);

            final int countFromMobile = countMobileJs(i);
            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += countFromMobile);
            buildIdAndToggleCount.putIfAbsent(i, countFromMobile);
        }

        for (int i = 61; i < 70; i++) {
            int count = countStaticPrefListH(i);
            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += count);
            buildIdAndToggleCount.putIfAbsent(i, count);
        }

        for (int i = 70; i < 99; i++) {
            int count = countStaticPrefListYaml(i);
            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += count);
            buildIdAndToggleCount.putIfAbsent(i, count);
        }

        return buildIdAndToggleCount;
    }

    private static int countAllJs(int buildId) {



    }

    private static int countFirefoxJs(int buildId) {


    }
    private static int countMobileJs(int buildId) {


    }
    private static int countStaticPrefListH(int buildId) {


    }

    private static int countStaticPrefListYaml(int buildId) {


    }
}
