package edu.uwaterloo.lee.joohan;

import com.google.common.math.Stats;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ToggleCounter {

    public static Map<Integer, List<Double>> getLifespan() {
        // On each number of release, it has 5th, average, and 95th percentile
        Map<Integer, List<Double>> lifespanAndProbabilities = new HashMap<>();
        Map<String, ToggleLifespanInfo> toggleAndLifespan = new HashMap<>();

        for (int i = 57; i < 61; i++) {
            final int currentRelease = i;
            getFromAllJs(i).forEach(s -> {
                toggleAndLifespan.computeIfPresent(s,
                        (key, val) -> {
                            val.addRelease(currentRelease);
                            return val;
                        });
                toggleAndLifespan.putIfAbsent(s, new ToggleLifespanInfo(s, currentRelease));
            });

            getFromFirefoxJs(i).forEach(s -> {
                toggleAndLifespan.computeIfPresent(s,
                        (key, val) -> {
                            val.addRelease(currentRelease);
                            return val;
                        });
                toggleAndLifespan.putIfAbsent(s, new ToggleLifespanInfo(s, currentRelease));
            });

            getFromMobileJs(i).forEach(s -> {
                toggleAndLifespan.computeIfPresent(s,
                        (key, val) -> {
                            val.addRelease(currentRelease);
                            return val;
                        });
                toggleAndLifespan.putIfAbsent(s, new ToggleLifespanInfo(s, currentRelease));
            });

        }

        for (int i = 61; i < 70; i++) {
            final int currentRelease = i;

            getFromAllJs(i).forEach(s -> {
                toggleAndLifespan.computeIfPresent(s,
                        (key, val) -> {
                            val.addRelease(currentRelease);
                            return val;
                        });
                toggleAndLifespan.putIfAbsent(s, new ToggleLifespanInfo(s, currentRelease));
            });

            getFromFirefoxJs(i).forEach(s -> {
                toggleAndLifespan.computeIfPresent(s,
                        (key, val) -> {
                            val.addRelease(currentRelease);
                            return val;
                        });
                toggleAndLifespan.putIfAbsent(s, new ToggleLifespanInfo(s, currentRelease));
            });

            getFromMobileJs(i).forEach(s -> {
                toggleAndLifespan.computeIfPresent(s,
                        (key, val) -> {
                            val.addRelease(currentRelease);
                            return val;
                        });
                toggleAndLifespan.putIfAbsent(s, new ToggleLifespanInfo(s, currentRelease));
            });

            getStaticPrefListH(i).forEach(s -> {
                toggleAndLifespan.computeIfPresent(s,
                        (key, val) -> {
                            val.addRelease(currentRelease);
                            return val;
                        });
                toggleAndLifespan.putIfAbsent(s, new ToggleLifespanInfo(s, currentRelease));
            });

            getFromDevtools(i).forEach(s -> {
                toggleAndLifespan.computeIfPresent(s,
                        (key, val) -> {
                            val.addRelease(currentRelease);
                            return val;
                        });
                toggleAndLifespan.putIfAbsent(s, new ToggleLifespanInfo(s, currentRelease));
            });
        }

        for (int i = 70; i < 99; i++) {
            final int currentRelease = i;

            getFromAllJs(i).forEach(s -> {
                toggleAndLifespan.computeIfPresent(s,
                        (key, val) -> {
                            val.addRelease(currentRelease);
                            return val;
                        });
                toggleAndLifespan.putIfAbsent(s, new ToggleLifespanInfo(s, currentRelease));
            });


            getFromFirefoxJs(i).forEach(s -> {
                toggleAndLifespan.computeIfPresent(s,
                        (key, val) -> {
                            val.addRelease(currentRelease);
                            return val;
                        });
                toggleAndLifespan.putIfAbsent(s, new ToggleLifespanInfo(s, currentRelease));
            });

            getFromMobileJs(i).forEach(s -> {
                toggleAndLifespan.computeIfPresent(s,
                        (key, val) -> {
                            val.addRelease(currentRelease);
                            return val;
                        });
                toggleAndLifespan.putIfAbsent(s, new ToggleLifespanInfo(s, currentRelease));
            });

            getStaticPrefListYaml(i).forEach(s -> {
                toggleAndLifespan.computeIfPresent(s,
                        (key, val) -> {
                            val.addRelease(currentRelease);
                            return val;
                        });
                toggleAndLifespan.putIfAbsent(s, new ToggleLifespanInfo(s, currentRelease));
            });
        }

        for (int i = 0; i < 43; i++) {
            final int numOfRelease = i;
            List<Double> probabilities = new ArrayList<>();

            for (int j = 57; j < 99; j++) {
                final int currentRelease = j;

                double totalNumOfToggleOnCurrentRelease = toggleAndLifespan.entrySet().stream()
                        .filter(e -> e.getValue().getStartingRelease() == currentRelease)
                        .count();

                double numOfToggles = toggleAndLifespan.entrySet().stream()
                        .filter(e -> e.getValue().getStartingRelease() == currentRelease)
                        .filter(e -> e.getValue().getLifespan() == numOfRelease)
                        .map(Map.Entry::getKey)
                        .count();

                probabilities.add(numOfToggles / totalNumOfToggleOnCurrentRelease);
            }

            Stats stats = Stats.of(probabilities);

            double mean = stats.mean();
            double stdDev = stats.populationStandardDeviation();
            double fifthPercentile = mean - 2 * stdDev;
            double ninetyFifthPercentile = mean + 2 * stdDev;

            lifespanAndProbabilities.put(numOfRelease, new ArrayList<>());
            lifespanAndProbabilities.get(numOfRelease).add(mean);
            lifespanAndProbabilities.get(numOfRelease).add(fifthPercentile);
            lifespanAndProbabilities.get(numOfRelease).add(ninetyFifthPercentile);
        }

        return lifespanAndProbabilities;
    }


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
            prefs.addAll(getStaticPrefListH(i));
            prefs.addAll(getFromDevtools(i));

            buildIdAndToggleCount.put(i, prefs.size());
        }

        for (int i = 70; i < 99; i++) {
            Set<String> prefs = new HashSet<>();

            prefs.addAll(getFromAllJs(i));
            prefs.addAll(getFromFirefoxJs(i));
            prefs.addAll(getFromMobileJs(i));
            prefs.addAll(getStaticPrefListYaml(i));

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

    private static List<String> getStaticPrefListH(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/init/StaticPrefList.h", buildId);
        Pattern pattern = Pattern.compile("\".+\"");

        return getLines(fileName, pattern)
                .stream()
                .filter(s -> !s.replace(" ", "").startsWith("//"))
                .map(s -> {
                            s = s.replace(" ", "");
                            if (s.startsWith("PREF")) {
                                return s.replace("PREF(\"", "")
                                        .replace("\",.+", "")
                                        .replace("\"", "")
                                        .replace(",", "")
                            }
                            //TODO: NEED to fix to support VARCACHE_PREF
                        }
                )
                .collect(Collectors.toList());
    }

    private static List<String> getStaticPrefListYaml(int buildId) {
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

    private static List<String> getFromDevtools(int buildId) {
        String fileName = String.format("downloaded_files/build_%s/devtools/shared/preferences/devtools-shared.js", buildId);
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
