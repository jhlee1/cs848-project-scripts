package edu.uwaterloo.lee.joohan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToggleCounter {
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
        String fileName = String.format("downloaded_files/build_%s/init/all.js", buildId);
        Pattern pattern = Pattern.compile("pref\\(\".+\", .+\\);");

        return getNumOfToggles(fileName, pattern);
    }

    private static int countStaticPrefListH(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/init/StaticPrefList.h", buildId);
        Pattern pattern = Pattern.compile("PREF\\(");

        return getNumOfToggles(fileName, pattern) - 2; // Take off the ones in the commment
    }

    private static int countStaticPrefListYaml(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/init/StaticPrefList.yaml", buildId);
        Pattern pattern = Pattern.compile("- name: .+");

        return getNumOfToggles(fileName, pattern);
    }

    private static int countFirefoxJs(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/profile/firefox.js", buildId);
        Pattern pattern = Pattern.compile("pref\\(\".+\", .+\\);");

        return getNumOfToggles(fileName, pattern);
    }

    private static int countMobileJs(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/app/mobile.js", buildId);
        Pattern pattern = Pattern.compile("pref\\(\".+\", .+\\);");

        return getNumOfToggles(fileName, pattern);
    }

    private static int getNumOfToggles(String fileName, Pattern pattern) {
        int count = 0;

        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    count++;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return count;
    }

}
