package edu.uwaterloo.lee.joohan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ToggleCounter {
    public static Map<Integer, Integer> getTotalOnEachRelease() {
        Map<Integer, Integer> buildIdAndToggleCount = new HashMap<>();

        for (int i = 57; i < 61; i++) {
            Set<String> prefs = new HashSet<>();
            prefs.addAll(getFromAllJs(i));
            prefs.addAll(getFromFirefoxJs(i));
            prefs.addAll(getFromMobileJs(i));

            buildIdAndToggleCount.put(i, prefs.size());
        }

        for (int i = 61; i < 70; i++) {
            Set<String> prefs = new HashSet<>();

            prefs.addAll(getFromAllJs(i));
            prefs.addAll(getFromFirefoxJs(i));
            prefs.addAll(getFromMobileJs(i));
            prefs.addAll(countStaticPrefListH(i));

            buildIdAndToggleCount.put(i, prefs.size());
        }

        for (int i = 70; i < 99; i++) {
            Set<String> prefs = new HashSet<>();

            prefs.addAll(getFromAllJs(i));
            prefs.addAll(getFromFirefoxJs(i));
            prefs.addAll(getFromMobileJs(i));
            prefs.addAll(countStaticPrefListYaml(i));

            buildIdAndToggleCount.put(i, prefs.size());
        }

        return buildIdAndToggleCount;
    }

    private static List<String> getFromAllJs(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/init/all.js", buildId);
        Pattern pattern = Pattern.compile("pref\\(\".+\", .+\\);");

        return getLines(fileName, pattern).stream()
                .filter(s -> !s.replace(" ", "").startsWith("//"))
                .map(s -> s.replace(" ", "").replace("pref(\"", "").replaceAll("\",.+", ""))
                .collect(Collectors.toList());
    }

    private static List<String> countStaticPrefListH(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/init/StaticPrefList.h", buildId);
        Pattern pattern = Pattern.compile("\".+\"");

        return getLines(fileName, pattern)
                .stream()
                .filter(s -> !s.replace(" ", "").startsWith("//"))
                .map(s -> s.replace(" ", "")
                        .replace("PREF(\"", "")
                        .replace("\",.+", "")
                        .replace("\"", "")
                        .replace(",", "")
                )
                .collect(Collectors.toList());
    }

    private static List<String> countStaticPrefListYaml(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/init/StaticPrefList.yaml", buildId);
        Pattern pattern = Pattern.compile("name: .+");

        return getLines(fileName, pattern)
                .stream()
                .map(s -> s.replace(" ", "")
                        .replace("-name:", "")
                )
                .collect(Collectors.toList());
    }

    private static List<String> getFromFirefoxJs(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/profile/firefox.js", buildId);
        Pattern pattern = Pattern.compile("pref\\(\".+\", .+\\);");

        return getLines(fileName, pattern).stream()
                .filter(s -> !s.replace(" ", "").startsWith("//"))
                .map(s -> s.replace(" ", "").replace("pref(\"", "").replaceAll("\",.+", ""))
                .collect(Collectors.toList());
    }

    private static List<String> getFromMobileJs(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/app/mobile.js", buildId);
        Pattern pattern = Pattern.compile("pref\\(\".+\", .+\\);");

        return getLines(fileName, pattern).stream()
                .filter(s -> !s.replace(" ", "").startsWith("//"))
                .map(s -> s.replace(" ", "").replace("pref(\"", "").replaceAll("\",.+", ""))
                .collect(Collectors.toList());
    }

    private static List<String> getLines(String fileName, Pattern pattern) {
        List<String> toggles = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    toggles.add(line);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return toggles;
    }

}
