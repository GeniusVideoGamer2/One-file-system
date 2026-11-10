#!/usr/bin/env bash
set -euo pipefail
iso="${1:?Usage: inspect-iso.sh path/to/Bliss.iso}"
echo "Checking ISO structure and possible Android system images..."
xorriso -indev "$iso" -find / -type f -print | grep -Ei '(/system|bootanimation|splash|logo|grub|isolinux|initrd|kernel)' | head -n 100 || true
echo "Inspection only. Branding embedded in compressed Android system images is NOT removed."
