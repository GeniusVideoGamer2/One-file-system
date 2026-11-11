# GX Welcome first-boot wizard

GameXWIN boots as a Debian Live USB. The first XFCE login launches GX Welcome, which asks for language and appearance preferences and stores a per-user completion marker. It never partitions, formats, mounts, or installs to a disk. The legacy installer menu entry is hidden. The live ISO is **not** a full persistent USB installation: writing the ISO to a USB does not guarantee that changes survive reboot. Persistent storage requires a separate, tested configuration. No ISO build is triggered by commits to the design branch.
