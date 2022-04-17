package edu.uwaterloo.lee.joohan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DevToggleCounter {
    public static Map<Integer, Integer> getTotalOnEachRelease() {
        Map<Integer, Integer> buildIdAndToggleCount = new HashMap<>();

        for (int i = 57; i < 99; i++) {
            final int countFromAll = getFromAllJS(i).size();
            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += countFromAll);
            buildIdAndToggleCount.putIfAbsent(i, countFromAll);

            final int countFromFirefox = getFromBrowserJS(i).size();
            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += countFromFirefox);
            buildIdAndToggleCount.putIfAbsent(i, countFromFirefox);

            final int countFromMobile = getFromMobileJS(i).size();
            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += countFromMobile);
            buildIdAndToggleCount.putIfAbsent(i, countFromMobile);
        }

        for (int i = 61; i < 70; i++) {
            int count = getFromStaticPrefListH(i).size();
            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += count);
            buildIdAndToggleCount.putIfAbsent(i, count);
        }

        for (int i = 70; i < 99; i++) {
            int count = getFromPrefListYaml(i).size();
            buildIdAndToggleCount.computeIfPresent(i, (key, val) -> val += count);
            buildIdAndToggleCount.putIfAbsent(i, count);
        }

        return buildIdAndToggleCount;
    }



    public static List<String> getFromAllJS(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/init/all.js", buildId);

        return getFromJS(fileName);
    }

    public static List<String> getFromBrowserJS(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/profile/firefox.js", buildId);

        return getFromJS(fileName);
    }

    public static List<String> getFromMobileJS(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/app/mobile.js", buildId);

        return getFromJS(fileName);
    }

    public static List<String> getFromStaticPrefListH(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/init/StaticPrefList.h", buildId);

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

                if (lines.get(i).replace(" ", "").contains("#define")) {
                    i++;

                    while (!lines.get(i).replace(" ", "").contains("#undefPREF_VALUE")) {
                        if (lines.get(i).replace(" ", "").matches("\".+\",")) {
                            results.add(lines.get(i)
                                    .replace(" ", "")
                                    .replace("\"", "")
                                    .replace(",", ""));
                        }

                        i++;
                    }
                } else if (lines.get(i).contains("PREF(")) { // VARCHAE PREF
                    throw new AssertionError("Unexpected next line when I find #if defined" + lines.get(i));
                    // I am leaving it as assert because I don't think that I need to implement this one.
                } else {
                    throw new AssertionError("Unexpected next line when I find #if defined" + lines.get(i));
                }
            }
        }

        return results;
    }

    private static List<String> getFromJS(String fileName) {
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

                if (lines.get(i).contains("pref(") || lines.get(i).replace(" ", "").contains("#if")) {

                    while (numOfIfs > 0) {
                        if (lines.get(i).contains("#endif") || lines.get(i).contains("#else") || lines.get(i).contains("#elif")) {
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
    public static List<String> getFromPrefListYaml(int buildId) {
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
                    if (lines.get(j).replace(" ", "").contains("-name:")) {
                        String toggleName = lines.get(j).replace("- name: ", "");
                        results.add(toggleName);
                        break;
                    }
                }
            }

            if (line.contains("defined(NIGHTLY_BUILD)") || line.contains("#ifdef NIGHTLY_BUILD")) {
                i++;

                String tmp = lines.get(i).replace(" ", "");

                while (tmp.startsWith("#") && !(tmp.contains("#if") || tmp.contains("#endif") || tmp.contains("#else") || lines.get(i).contains("#elif"))) {
                    i++;
                    tmp = lines.get(i).replace(" ", "");
                }

                if (lines.get(i).contains("name:") || lines.get(i).replace(" ", "").contains("#if")) {
                    int numOfIfs = 1;

                    while (numOfIfs > 0) {
                        if (lines.get(i).contains("#endif") || lines.get(i).contains("#else") || lines.get(i).contains("#elif")) {
                            numOfIfs--;
                        }

                        if (lines.get(i).contains("#if")) {
                            numOfIfs++;
                        }

                        if (lines.get(i).replace(" ", "").contains("-name:")) {
                            String toggleName = lines.get(i).replace(" ", "").replace("-name: ", "");
                            results.add(toggleName);
                        }
                        i++;
                    }
                } else if (lines.get(i).contains("value:")) {
                    for (int j = i; j >= 0; j--) {
                        if (lines.get(j).replace(" ", "").contains("-value:")) {
                            String toggleName = lines.get(j).replace(" ", "").replace("-name:", "");
                            results.add(toggleName);
                            break;
                        }
                    }
                } else if (lines.get(i).length() <= 0) {
                    //Do nothing
                } else {
                    throw new AssertionError("Unexpected next line: " + lines.get(i).length());
                }
            }
        }

        return results;
    }
}
