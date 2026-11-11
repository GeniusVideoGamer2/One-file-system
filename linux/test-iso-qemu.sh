#!/usr/bin/env bash
set -euo pipefail
ISO="${1:?Usage: bash linux/test-iso-qemu.sh path/to/image.iso}"
mkdir -p qemu-report
LOG="$PWD/qemu-report/qemu-serial.log"
: > "$LOG"
echo "Running a bounded QEMU x86_64 BIOS smoke test (120 seconds)."
echo "This does not prove that graphical XFCE has started."
set +e
timeout --signal=TERM --kill-after=5s 120s qemu-system-x86_64 \
  -machine pc -accel tcg -m 2048 -smp 2 \
  -cdrom "$ISO" -boot d -display none -vga std \
  -serial file:"$LOG" -monitor none -no-reboot -net none
rc=$?
set -e
echo "QEMU exit code: $rc"
echo "QEMU exit code 124 means timeout, not a successful boot."
echo "Serial output bytes: $(wc -c < "$LOG")"
if [ "$rc" -ne 0 ] && [ "$rc" -ne 124 ]; then
  echo "QEMU crashed or exited unexpectedly."
  exit 1
fi
echo "QEMU process survived; boot to desktop NOT VERIFIED."
