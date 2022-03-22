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