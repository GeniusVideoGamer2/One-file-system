# GameXWIN Linux Aether Edition (XFCE, no KDE)

This branch includes an experimental Debian 13 amd64 Live ISO recipe using **XFCE** (not KDE), LightDM, Thunar, NetworkManager, Firefox ESR, Wine and Winetricks. The earlier standalone kernel experiment is retained in the repository root.

## Build

On Ubuntu x86_64 with sudo: install `live-build debootstrap xorriso squashfs-tools grub-pc-bin grub-efi-amd64-bin mtools dosfstools`, then run `bash linux/build-live.sh`.

Successful output: `linux/dist/GameXWIN-Linux-Aether-amd64.iso`. The GitHub Actions workflow uploads this ISO when its build succeeds.

## Limitations

The five GameXWIN color palettes are present; XFCE integration is a basic preference helper, not a complete custom Aether panel. Waydroid is **not** included in the live ISO; `linux/configure-waydroid.sh` is an optional Debian installer helper for systems with compatible kernels. Android and Google Play are not preconfigured. No disk installer is included. Test in a virtual machine before physical hardware.
