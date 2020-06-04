
## \[ Remove China Apps - Reverse Engineering ⛏👷🔧️👷🔧 \]

![alt text](./app_logo.png "Remove China App Logo")


> Source code of [Remove China Apps](https://play.google.com/store/apps/details?id=com.chinaappsremover), an Android app that claims to identify China-made apps on your Android phone and remove them, that has gone viral in India.

Added the functionality to refresh and cache the chinese app list from the [`china_apps.json`](https://github.com/Bloody-Badboy/Remove-China-Apps/blob/master/china_apps.json) file.
You can create pull request and update the [`china_apps.json`](https://github.com/Bloody-Badboy/Remove-China-Apps/blob/master/china_apps.json) to blacklist more chinese apps.

### Requirements
- Latest Android SDK tools
- Latest Android platform tools
- AndroidX

### Build
    ./gradlew assembleDebug
