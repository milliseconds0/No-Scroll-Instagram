# No-Scroll Instagram

An Android accessibility helper that sends Instagram's **Home** and **Reels** tab taps to Direct Messages instead.

> [!IMPORTANT]
> ## Start Instagram in Messages
> This app redirects the Home and Reels tabs after Instagram is already open; it cannot change where Instagram opens initially.
>
> Create and use Instagram's native **Messages** shortcut:
>
> 1. Long-press the Instagram icon in your launcher.
> 2. Long-press **Messages** in the shortcut menu, then drag it to your home screen if your launcher supports pinned shortcuts.
> 3. Use that Messages shortcut, not the normal Instagram icon whenever you want to start in Direct Messages.
>
> Instagram may rename or move this shortcut in future updates.

## What it does

Android does not allow a standard app to disable controls inside Instagram. No-Scroll Instagram instead uses Android's supported `AccessibilityService` mechanism to respond to those controls:

1. Tap **Home** or **Reels** in Instagram's bottom navigation.
2. The service opens Direct Messages immediately.

The service is limited to the official Instagram package (`com.instagram.android`). It does not use the network, collect content, or request Instagram credentials.

## Install from a release APK

1. Download the APK from the project's [release page](https://github.com/milliseconds0/No-Scroll-Instagram/releases/).
2. Open the app, then go to **Open Accessibility settings** > **Installed applications** > **No-Scroll Instagram guard**.
3. Enable the service and tap **Authorize**.
4. Complete the [Start Instagram in Messages](#start-instagram-in-messages) step above.

## Build from source

### Requirements

- Windows, macOS, or Linux
- [Android Studio](https://developer.android.com/studio), current stable release
- Android SDK Platform 35 and Android SDK Build-Tools 35.x
- JDK 17 (Android Studio's bundled JDK is sufficient)
- Gradle 8.7 or newer, used once to generate this repository's standard Gradle wrapper
- An Android phone or emulator running Android 8.0 (API 26) or newer
- The official Instagram Android app, installed and signed in

No Node.js, Python, backend, API key, database, or third-party Android runtime dependency is required.

### Android Studio

1. Install Android Studio and Gradle 8.7 or newer. In the Android Studio setup wizard, keep the Android SDK and Android SDK Build-Tools selected.
2. In **More Actions → SDK Manager**, install **Android 15.0 (API 35)** and an Android SDK Build-Tools 35.x version.
3. In this repository, run `gradle wrapper --gradle-version 8.7` once to create the standard Gradle wrapper.
4. Open this repository in Android Studio and let Gradle download the build plugins and AndroidX artifacts.
5. Connect a phone with USB debugging enabled, or start an emulator, then select it from the device picker.
6. Click **Run**.
7. Open **No-Scroll Instagram** on the device and enable **No-Scroll Instagram guard** in Accessibility settings.
8. Complete the [Start Instagram in Messages](#start-instagram-in-messages) step above.

### Command line

Install [Gradle 8.7+](https://gradle.org/install/) once, then generate the project wrapper and build the debug APK:

```powershell
gradle wrapper --gradle-version 8.7
.\gradlew.bat assembleDebug
```

The APK is created at `app/build/outputs/apk/debug/app-debug.apk`.

To install it with Android Platform Tools:

```powershell
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

After installing, enable the guard and create the Messages shortcut as described above.