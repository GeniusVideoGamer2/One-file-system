# GameXOS 17 Phoenix — ISO build workspace

This repository is a workspace for an Android x86_64 GameXOS 17 Phoenix build. **It does not yet contain an Android OS image or a bootable ISO.**

## Goal
- Android 17 AOSP where a validated PC-compatible target exists; Android 16 fallback only with explicit version reporting
- UEFI x86_64 boot, real Android init and framework, Phoenix desktop launcher, Turkish UI
- Wine is an optional compatibility component, not a replacement for Android
- No changes to Windows, EFI partitions, internal disks, or USB drives

## GitHub Actions
Open **Actions → GameXOS ISO readiness → Run workflow**. The job inspects the repository and uploads a readiness report. It does **not** fabricate a bootable ISO from missing Android components.

A real ISO build needs a compatible Android kernel, ramdisk, Android system/vendor/product images or a documented alternative boot architecture, an appropriate bootloader and tested build scripts. Do not mark a build successful until a VM boots into Android UI.

## Next step
Import the GameXOS source project and identify a supported Android x86_64 build target. Add reproducible build instructions, then implement and test an ISO packaging stage. Keep large Android source trees and binaries out of ordinary Git commits.
