# This is repository to analyze feature toggle data from firefox project

According to the tag, Nightly build exists From 57 and that's where I can find preference file to differentiate development toggles and release toggles  

Files to look at on each build
- 57~60
  - all.js
- 61~69
  - all.js
  - browser/app/profile/firefox.js
  - mobile/android/app/mobile.js
  - Static_PrefList.h
- 70 ~ 98
  - all.js
  - StaticPrefList.yaml
  - browser/app/profile/firefox.js
  - mobile/android/app/mobile.js





Tags I used to differentiate toggles

- Dev toggles
  - !defined(RELEASE_OR_BETA)
  - defined(NIGHTLY_BUILD)
  - @IS_NOT_NIGHTLY_BUILD@
  - @IS_NIGHTLY_BUILD@
- Release toggles
  - RELEASE_OR_BETA
  - EARLY_BETA_OR_EARLIER
  - MOZ_DEV_EDITION
  - 
- No if states




When the feature supports somes platfrom and is deactivate for other platforms, it is dev
```
# Is support for WebVR APIs enabled?
# Enabled by default in beta and release for Windows and OS X and for all
# platforms in nightly and aurora.
- name: dom.vr.enabled
  type: RelaxedAtomicBool
#if defined(XP_WIN) || defined(XP_DARWIN) || !defined(RELEASE_OR_BETA)
  value: true
#else
  value: false
#endif
  mirror: always
```

