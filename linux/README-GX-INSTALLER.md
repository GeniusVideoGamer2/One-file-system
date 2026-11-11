# GX Installer safe preview

This is a **read-only** disk selection interface. It lists devices using lsblk and allows selection for display only. The Install button is disabled. It does not partition, format, mount, or write bootloader files. Real installation must not be enabled without independently verifying device identity, mounted partitions, EFI location, bootloader targets, backups, and installer isolation. For testing on a family PC, use a VM or Live mode, not a real disk installer.
