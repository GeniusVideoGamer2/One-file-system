#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
mkdir -p dist
ISO='Bliss-v16.9.7-x86_64-OFFICIAL-gapps-20241011.iso'
BASE="https://sourceforge.net/projects/blissos-x86/files/Official/BlissOS16/Gapps/Generic"
curl --fail --location --retry 4 --retry-delay 5 --output "$ISO" "$BASE/$ISO/download"
curl --fail --location --retry 4 --retry-delay 5 --output "$ISO.sha256" "$BASE/$ISO.sha256/download"
expected="$(grep -Eo '[a-fA-F0-9]{64}' "$ISO.sha256" | head -n1 | tr '[:upper:]' '[:lower:]')"
[ -n "$expected" ] || { echo 'No valid SHA256 checksum found'; exit 1; }
actual="$(sha256sum "$ISO" | cut -d' ' -f1)"
[ "$expected" = "$actual" ] || { echo 'Checksum mismatch'; exit 1; }
printf 'Original Bliss ISO SHA256: %s\n' "$actual" > dist/build-report.txt
echo 'Adding GameXWIN resource directory to ISO. Android system image itself is unchanged.' >> dist/build-report.txt
xorriso -indev "$ISO" -outdev dist/GameXWIN-Android-Bliss16.iso \
  -boot_image any replay \
  -map assets /GameXWIN \
  -commit
sha256sum dist/GameXWIN-Android-Bliss16.iso >> dist/build-report.txt
echo 'NOTE: Bootability of repacked ISO and GameXWIN Android app integration are NOT verified.' >> dist/build-report.txt
