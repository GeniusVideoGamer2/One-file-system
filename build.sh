#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
mkdir -p build dist iso/boot/grub
as --32 kernel/boot.s -o build/boot.o
gcc -m32 -ffreestanding -fno-pie -fno-stack-protector -fno-builtin -O2 -Wall -Wextra -c kernel/kernel.c -o build/kernel.o
ld -m elf_i386 -T kernel/linker.ld -nostdlib -o build/gamexwin.elf build/boot.o build/kernel.o
grub-file --is-x86-multiboot build/gamexwin.elf
cp build/gamexwin.elf iso/boot/gamexwin.elf
cp boot/grub/grub.cfg iso/boot/grub/grub.cfg
grub-mkrescue -o dist/GameXWIN-1.0.iso iso
printf 'Built: dist/GameXWIN-1.0.iso\n'
