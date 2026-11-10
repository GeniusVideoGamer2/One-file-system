# GameXWIN OS 1.0 — experimental kernel

This branch adds a **separate independent x86 32-bit hobby OS prototype**, not a complete desktop operating system. The existing GameXOS files remain untouched.

## Build

On Ubuntu x86_64 install `gcc binutils grub-pc-bin grub-common xorriso mtools`, then run `bash build.sh`. Output: `dist/GameXWIN-1.0.iso`. Boot-test with `qemu-system-i386 -m 256 -cdrom dist/GameXWIN-1.0.iso`.

The kernel currently displays a VGA text boot message only. It does not write to disks.

## Planned, not implemented

Aether UI, GameXStore, recovery, installer, GPT/MBR partition inspection, UEFI, Wine, Android/APK and Google Play Store. The five appearance palettes in `themes/palettes.json` are design tokens, not a graphical theme engine. Play Store requires Android compatibility and applicable Google licensing.

The GitHub Actions workflow `.github/workflows/gamexwin-iso.yml` builds the prototype ISO and uploads it as an artifact if successful. Test in QEMU before attempting physical hardware.
