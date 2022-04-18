package edu.uwaterloo.lee.joohan;

import java.util.HashMap;
import java.util.Map;

public class ToggleLifeCycleTracker {

    public static Map<String, ToggleInfo> getLifeCycleDevToggles() {
        Map<String, ToggleInfo> toggles = new HashMap<>();

        for (int i = 57; i < 99; i++) {
            int currentVersion = i;
            DevToggleCounter.getFromAllJS(i)
                    .forEach(toggle -> {
                        toggles.putIfAbsent(toggle, new ToggleInfo(toggle));
                        toggles.get(toggle).addBuildInfo(currentVersion, ToggleStatus.DEV);
                    });
            ReleaseToggleCounter.getFromAllJS(i)
                    .forEach(toggle -> {
                        toggles.putIfAbsent(toggle, new ToggleInfo(toggle));
                        toggles.get(toggle).addBuildInfo(currentVersion, ToggleStatus.RELEASE);
                    });

            DevToggleCounter.getFromBrowserJS(i)
                    .forEach(toggle -> {
                        toggles.putIfAbsent(toggle, new ToggleInfo(toggle));
                        toggles.get(toggle).addBuildInfo(currentVersion, ToggleStatus.DEV);
                    });
            ReleaseToggleCounter.getFromBrowserJS(i)
                    .forEach(toggle -> {
                        toggles.putIfAbsent(toggle, new ToggleInfo(toggle));
                        toggles.get(toggle).addBuildInfo(currentVersion, ToggleStatus.RELEASE);
                    });
            DevToggleCounter.getFromMobileJS(i)
                    .forEach(toggle -> {
                        toggles.putIfAbsent(toggle, new ToggleInfo(toggle));
                        toggles.get(toggle).addBuildInfo(currentVersion, ToggleStatus.DEV);
                    });
            ReleaseToggleCounter.getFromMobileJS(i)
                    .forEach(toggle -> {
                        toggles.putIfAbsent(toggle, new ToggleInfo(toggle));
                        toggles.get(toggle).addBuildInfo(currentVersion, ToggleStatus.RELEASE);
                    });
        }

        for (int i = 61; i < 70; i++) {
            int currentVersion = i;
            DevToggleCounter.getFromStaticPrefListH(i)
                    .forEach(toggle -> {
                        toggles.putIfAbsent(toggle, new ToggleInfo(toggle));
                        toggles.get(toggle).addBuildInfo(currentVersion, ToggleStatus.DEV);
                    });
            ReleaseToggleCounter.getFromStaticPrefListH(i)
                    .forEach(toggle -> {
                        toggles.putIfAbsent(toggle, new ToggleInfo(toggle));
                        toggles.get(toggle).addBuildInfo(currentVersion, ToggleStatus.RELEASE);
                    });

            DevToggleCounter.getFromDevtoolsJS(i)
                    .forEach(toggle -> {
                        toggles.putIfAbsent(toggle, new ToggleInfo(toggle));
                        toggles.get(toggle).addBuildInfo(currentVersion, ToggleStatus.DEV);
                    });

            ReleaseToggleCounter.getFromDevtoolsJS(i)
                    .forEach(toggle -> {
                        toggles.putIfAbsent(toggle, new ToggleInfo(toggle));
                        toggles.get(toggle).addBuildInfo(currentVersion, ToggleStatus.RELEASE);
                    });
        }

        for (int i = 70; i < 99; i++) {
            int currentVersion = i;
            DevToggleCounter.getFromPrefListYaml(i)
                    .forEach(toggle -> {
                        toggles.putIfAbsent(toggle, new ToggleInfo(toggle));
                        toggles.get(toggle).addBuildInfo(currentVersion, ToggleStatus.DEV);
                    });
            ReleaseToggleCounter.getFromYaml(i)
                    .forEach(toggle -> {
                        toggles.putIfAbsent(toggle, new ToggleInfo(toggle));
                        toggles.get(toggle).addBuildInfo(currentVersion, ToggleStatus.RELEASE);
                    });
        }

        return toggles;
    }
}
