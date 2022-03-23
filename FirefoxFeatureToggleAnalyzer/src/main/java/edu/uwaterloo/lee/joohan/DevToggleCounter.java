package edu.uwaterloo.lee.joohan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class DevToggleCounter {
    public static Map<Integer, Integer> getTotalOnEachRelease() {
        Map<Integer, Integer> buildIdAndToggleCount = new HashMap<>();
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
//        for (int i = 71; i < 72; i++) {
//            List<String> devToggles = getDevToggleFromPrefListYaml(i);
//            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += devToggles.size());
//            buildIdAndToggleCount.putIfAbsent(i, devToggles.size());
//        }

        String fileName = String.format("downloaded_files/build_%s/init/all.js", 70);

        List<String> tmp = DevToggleCounter.getDevToggleFromJS(fileName);
        System.out.println(tmp.stream().collect(Collectors.joining("\n")));

        return buildIdAndToggleCount;
    }

    private static int getDevToggleFromStaticPrefListH(int buildId) {


    }

    private static List<String> getDevToggleFromJS(String fileName) {
        List<String> lines = new ArrayList<>();
        List<String> results = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (!line.replaceAll(" ", "").startsWith("//")) {
                    lines.add(line);
                }

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (line.contains("defined(NIGHTLY_BUILD)") || line.contains("#ifdef NIGHTLY_BUILD")) {
                i++;
                int numOfIfs = 1;

                if (lines.get(i).contains("pref(")) {

                    while (numOfIfs > 0) {
                        if (lines.get(i).contains("#endif") || lines.get(i).contains("#else")) {
                            numOfIfs--;
                        }

                        if (lines.get(i).contains("#if")) {
                            numOfIfs++;
                        }

                        if (lines.get(i).contains("pref(")) {
                            String toggleName = lines.get(i)
                                    .replace("pref(\"", "")
                                    .replaceAll("\",.+\\);", "")
                                    .replace(" ", "");

                            results.add(toggleName);
                        }
                        i++;
                    }
                } else {
                    throw new AssertionError("Unexpected next line when I find #if defined" + lines.get(i));
                }
            }
        }

        return results;
    }



    // Tags considered Dev toggles: "defined(NIGHTLY_BUILD)", "#ifdef NIGHTLY_BUILD", "@IS_NIGHTLY_BUILD@", "@IS_NOT_NIGHTLY_BUILD@"
    private static List<String> getDevToggleFromPrefListYaml(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/init/StaticPrefList.yaml", buildId);

        List<String> lines = new ArrayList<>();
        List<String> results = new ArrayList<>();

        boolean startRecord = false;

        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("#-----------------------------------------------------")) {
                    startRecord = true;
                }

                if (startRecord) {
                    lines.add(line);
                }

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (line.contains("@IS_NIGHTLY_BUILD@") || line.contains("@IS_NOT_NIGHTLY_BUILD@")) {
                for (int j = i; j >= 0; j--) {
                    if (lines.get(j).contains("- name:")) {
                        String toggleName = lines.get(j).replace("- name: ", "");
                        results.add(toggleName);
                        break;
                    }
                }
            }

            if (line.contains("defined(NIGHTLY_BUILD)") || line.contains("#ifdef NIGHTLY_BUILD")) {
                i++;

                while (lines.get(i).startsWith("  #")) {
                    i++;
                }

                if (lines.get(i).contains("name:")) {
                    int numOfIfs = 1;

                    while (numOfIfs > 0) {
                        if (lines.get(i).contains("#endif") || lines.get(i).contains("#else")) {
                            numOfIfs--;
                        }

                        if (lines.get(i).contains("#if")) {
                            numOfIfs++;
                        }

                        if (lines.get(i).contains("- name:")) {
                            String toggleName = lines.get(i).replace("- name: ", "");
                            results.add(toggleName);
                        }
                        i++;
                    }
                } else if (lines.get(i).contains("value:")) {
                    for (int j = i; j >= 0; j--) {
                        if (lines.get(j).contains("- value:")) {
                            String toggleName = lines.get(j).replace("- name: ", "");
                            results.add(toggleName);
                            break;
                        }
                    }
                } else {
                    throw new AssertionError("Unexpected next line when I find #if defined" + lines.get(i));
                }
            }
        }

        return results;
    }
}
