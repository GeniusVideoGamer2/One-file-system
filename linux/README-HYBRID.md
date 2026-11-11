# GameXWIN OS 1.0 Aether Hybrid — integration contract

## Target
One bootable Linux ISO with XFCE real desktop, GX branding, Plank dock, Wine, and an Android environment with Google Play where licensing and device compatibility permit.

## Architecture
- Host: Debian 13 amd64 XFCE live OS (existing `linux/build-live.sh`).
- Windows programs: Debian Wine packages, with per-application compatibility limits.
- Android: **not yet integrated**. Bliss OS is a complete Android-x86 operating system, not a package installable into Debian. A VM/container integration needs an Android image, runtime, display/audio/input bridges, and hardware support. Do not assume Bliss can run inside Waydroid or that GApps can be redistributed.
- Boot: live-build ISO hybrid with GRUB; custom GX splash/boot animation not yet installed.
- Branding: original SVG wallpaper and Aether session configuration; no upstream Bliss logo removal has happened.

## Acceptance tests
1. GitHub Actions builds a nonempty ISO and uploads artifact.
2. QEMU boots ISO to XFCE session; desktop, Thunar, dock, networking work.
3. Wine launches a tested Windows application.
4. Android runtime launches an Android app inside Linux desktop, including Play Store only when legitimately available.
5. GX boot branding appears at each configured boot stage.
6. USB boot verified separately on supported UEFI hardware.

Current status: steps 1–6 are NOT fully verified. A single working hybrid ISO must not be advertised until tests pass.
