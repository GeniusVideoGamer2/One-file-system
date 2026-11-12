# GameXWIN Android PC — Liquid Glass prototype

This is an **Android launcher app prototype for x86_64 PCs**, not a bootable Android OS image. The existing Debian ISO is unchanged.

## Features
- Fullscreen landscape-oriented WebView desktop shell with local HTML/CSS
- GX menu, dock, windows, GX Store discovery with APKMirror search links
- No hidden APK downloads, no automatic installs, no unrestricted JavaScript bridge
- Designed for mouse/keyboard, usable as a launcher app after installation

## Build
Open `android-pc` in Android Studio with Android SDK 35 and JDK 17. Build the debug APK with Gradle 8.9 and Android Gradle Plugin 8.7.3. The Gradle wrapper is not bundled; Android Studio can set it up. No build has yet been verified.

## Limitations
Android-x86/Bliss OS integration and bootable USB image are **not** implemented. Android package install UI, persistence, Wi-Fi and settings are owned by the host OS. APKMirror is an external website, not an official public catalog API. Wine requires a separate Linux compatibility environment and is not integrated.
