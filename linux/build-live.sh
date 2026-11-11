#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
command -v lb >/dev/null || { echo "Install live-build first"; exit 1; }
python3 tools/generate-gx-icons.py
rm -rf work
mkdir -p work
cd work
lb config --mode debian --distribution trixie --security false --architectures amd64 --binary-images iso-hybrid --debian-installer none --archive-areas "main contrib non-free-firmware" --bootappend-live "boot=live components quiet splash" --iso-application "GameXWIN Linux Aether" --iso-publisher "GameXWIN Community"
mkdir -p config/package-lists config/includes.chroot
# Ubuntu-packaged live-build may generate obsolete security suites; replace with Debian 13 names.
find config -type f \( -name '*.list' -o -name '*.sources' \) -exec sed -i -e 's#security.debian.org/ trixie/updates#security.debian.org/debian-security trixie-security#g' -e 's#trixie/updates#trixie-security#g' {} +
cp ../packages.list.chroot config/package-lists/gamexwin.list.chroot
cp -a ../overlay/. config/includes.chroot/
chmod 755 config/includes.chroot/usr/local/bin/gamexwin-*
sudo lb build
mkdir -p ../dist
cp live-image-amd64.hybrid.iso ../dist/GameXWIN-Linux-Aether-amd64.iso
sha256sum ../dist/*.iso
