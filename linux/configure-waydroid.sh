#!/usr/bin/env bash
set -euo pipefail
# Run inside a Debian 13 system with apt, not automatically during ISO build.
if [ "$(id -u)" -ne 0 ]; then echo "Run as root"; exit 1; fi
if ! grep -q 'trixie' /etc/os-release; then echo "This helper targets Debian 13 (trixie)."; exit 1; fi
echo 'deb http://deb.debian.org/debian trixie-backports main contrib non-free-firmware' > /etc/apt/sources.list.d/gamexwin-backports.list
apt-get update
apt-get install -y -t trixie-backports waydroid
echo 'Waydroid package installed. Initialization needs working binder and Wayland.'
