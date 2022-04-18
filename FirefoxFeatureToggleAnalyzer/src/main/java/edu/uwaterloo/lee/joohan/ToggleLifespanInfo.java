package edu.uwaterloo.lee.joohan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ToggleLifespanInfo {
    Set<Integer> releases;
    String name;

    public ToggleLifespanInfo(String name, int release) {
        this.name = name;
        releases = new HashSet<>();
        releases.add(release);
    }

    public void addRelease(int release) {
        releases.add(release);
    }

    public int getStartingRelease() {
        return releases.stream()
                .min(Integer::compareTo)
                .orElseThrow(() -> new AssertionError("No element exists."));
    }

    public int getLifespan() {
        int startAt = getStartingRelease();

        int endAt = releases.stream()
                .max(Integer::compareTo)
                .orElseThrow(() -> new AssertionError("No element exists."));

        if (releases.size() != 1 && !(endAt - startAt == releases.size() - 1)) {
//            String errorMessage = String.format("The toggle skipped a release name: %s, startAt: %s, endAt: %s, size: %s / list: %s", name, startAt, endAt, releases.size(), releases);
//            System.out.println(errorMessage);
//            throw new AssertionError(errorMessage);
        }

        return (endAt - startAt);
    }
}
