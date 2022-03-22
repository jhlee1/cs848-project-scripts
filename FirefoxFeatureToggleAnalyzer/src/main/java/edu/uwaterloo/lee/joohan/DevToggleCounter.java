package edu.uwaterloo.lee.joohan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DevToggleCounter {
//    public static Map<Integer, Integer> getTotalOnEachRelease() {
//        Map<Integer, Integer> buildIdAndToggleCount = new HashMap<>();
//
//        for (int i = 57; i < 99; i++) {
//            final int countFromAll = countAllJs(i);
//            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += countFromAll);
//            buildIdAndToggleCount.putIfAbsent(i, countFromAll);
//
//            final int countFromFirefox = countFirefoxJs(i);
//            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += countFromFirefox);
//            buildIdAndToggleCount.putIfAbsent(i, countFromFirefox);
//
//            final int countFromMobile = countMobileJs(i);
//            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += countFromMobile);
//            buildIdAndToggleCount.putIfAbsent(i, countFromMobile);
//        }
//
//        for (int i = 61; i < 70; i++) {
//            int count = countStaticPrefListH(i);
//            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += count);
//            buildIdAndToggleCount.putIfAbsent(i, count);
//        }
//
//        for (int i = 70; i < 99; i++) {
//            int count = countStaticPrefListYaml(i);
//            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += count);
//            buildIdAndToggleCount.putIfAbsent(i, count);
//        }
//
//        return buildIdAndToggleCount;
//    }

//    private static int countAllJs(int buildId) {
//
//
//
//    }
//
//    private static int countFirefoxJs(int buildId) {
//
//
//    }
//    private static int countMobileJs(int buildId) {
//
//
//    }
//    private static int countStaticPrefListH(int buildId) {
//
//
//    }
//
//    private static int countStaticPrefListYaml(int buildId) {
//
//
//    }

    public static Set<String> getUniqueNightlyBuildLine(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/init/all.js", buildId);

        Set<String> result = new HashSet<>();

        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.toLowerCase().contains("nightly")) {
                    result.add(line);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return result;
    }
}
