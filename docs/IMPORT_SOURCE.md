# GameXOS source import

The GameXOS Phoenix ZIP is **not yet imported** into this repository. To import it, upload the extracted **contents** of the `GameXOS17/` directory to this branch, preserving the directory structure.

The existing GitHub Actions readiness job is a diagnostic check only. It does not build an Android ISO.

Do not upload personal files, tokens, SSH keys, `.repo` object stores, or full AOSP checkout. Android images are too large for ordinary Git commits. Do not use scripts that write to disks.

After import, verify that `packages/apps/GameXOSLauncher/`, `device/gamexos/phoenix/`, `scripts/`, and `ui-preview/` are present. Then review AOSP device target compatibility and implement an actual reproducible Android x86_64 build and VM test before enabling ISO packaging.
