package edu.uwaterloo.lee.joohan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ReleaseToggleCounter {
    public static List<String> getFromDevtoolsJS(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/devtools/shared/preferences/devtools-shared.js", buildId);

        return getFromJS(fileName);
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

                if (!line.replace(" ", "").startsWith("//")) {
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

            if (line.contains("#ifdef RELEASE_OR_BETA") || line.contains("defined(RELEASE_OR_BETA)") || line.contains("#ifdef EARLY_BETA_OR_EARLIER")) {
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
                    int numOfIfs = 1;

                        while (numOfIfs > 0) {
                            if (lines.get(i).contains("#endif") || lines.get(i).contains("#else") || lines.get(i).contains("#elif")) {
                                numOfIfs--;
                            }

                            if (lines.get(i).contains("#if")) {
                                numOfIfs++;
                            }

                            if (lines.get(i).replace(" ", "").matches("\".+\",")) {
                                results.add(lines.get(i)
                                        .replace(" ", "")
                                        .replace("\"", "")
                                        .replace(",", ""));
                            }
                            i++;
                        }
                } else {
                    throw new AssertionError("Unexpected next line: " + lines.get(i));
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

        lines = lines.stream()
                .map(s -> s.replaceAll("//.+", ""))
                .collect(Collectors.toList());


        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (line.contains("defined(MOZ_DEV_EDITION)")
                    || line.contains("defined(RELEASE_OR_BETA)")
                    || line.contains("#ifdef EARLY_BETA_OR_EARLIER")
                    || line.contains("#ifdef RELEASE_OR_BETA")
                    || line.contains("#ifdef MOZ_DEV_EDITION")) {
                i++;

                if (lines.get(i).contains("pref(")) {

                    int numOfIfs = 1;

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

    public static List<String> getFromYaml(int buildId) {
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

            if (line.contains("@IS_NOT_RELEASE_OR_BETA@")
                    || line.contains("@IS_EARLY_BETA_OR_EARLIER@")
                    || line.contains("@IS_NOT_EARLY_BETA_OR_EARLIER@")
                    || line.contains("@IS_NIGHTLY_OR_DEV_EDITION@")) {
                for (int j = i; j >= 0; j--) {
                    if (lines.get(j).contains("- name:")) {
                        String toggleName = lines.get(j).replace("- name: ", "");
                        results.add(toggleName);
                        break;
                    }
                }
            }

            if (line.contains("#ifdef RELEASE_OR_BETA")
                    || line.contains("#ifdef EARLY_BETA_OR_EARLIER")
                    || line.contains("#ifdef MOZ_DEV_EDITION")
                    || line.contains("defined(RELEASE_OR_BETA)")
                    || line.contains("defined(EARLY_BETA_OR_EARLIER)")
                    || line.contains("defined(MOZ_DEV_EDITION)")) {
                i++;

                String tmp = lines.get(i).replace(" ", "");

                while (tmp.startsWith("#") && !(tmp.contains("#if") || tmp.contains("#endif") || tmp.contains("#else") || lines.get(i).contains("#elif"))) {
                    i++;
                    tmp = lines.get(i).replace(" ", "");
                }


                if (lines.get(i).contains("name:")) {
                    int numOfIfs = 1;

                    while (numOfIfs > 0) {
                        if (lines.get(i).contains("#endif") || lines.get(i).contains("#else") || lines.get(i).contains("#elif")) {
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
                    throw new AssertionError("Unexpected next line: " + lines.get(i));
                }
            }
        }

        return results;
    }
}