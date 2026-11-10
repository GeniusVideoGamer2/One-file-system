# GameXWIN Linux Aether Edition

This is a separate **Debian 13 (trixie) amd64 live ISO** build pipeline. It does not overwrite the independent-kernel experiment in the repository root.

## What is included
- KDE Plasma desktop (Aether foundation), SDDM, Dolphin, Konsole, NetworkManager, Firefox ESR.
- Wine and Winetricks from Debian repositories for supported Windows programs. Not all EXE files will run.
- An Android compatibility status command. Waydroid/APK execution is **not yet enabled**: kernel binder support, image sourcing, graphics compatibility and testing are required. No Google Play or GApps are bundled.
- Live ISO boot, not an installer. No partition changes are made.

## Build
Run on Ubuntu/Debian **x86_64** host with root/sudo access. Install `live-build debootstrap xorriso squashfs-tools grub-pc-bin grub-efi-amd64-bin mtools dosfstools`. Then `bash linux/build-live.sh`.

Output: `linux/dist/GameXWIN-Linux-Aether-amd64.iso` on successful build. Test in QEMU before any hardware use. The CI build has not yet been confirmed successful. Aether's visual theme and Android environment are pending further implementation.

Debian Live documentation: https://live-team.pages.debian.net/live-manual/html/live-manual.en.html
