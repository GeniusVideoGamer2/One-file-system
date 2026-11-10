# GameXWIN Android Edition (Bliss OS experiment)

This workflow fetches the exact Bliss OS 16.9.7 x86_64 GApps ISO specified by the project owner, verifies the accompanying SHA256, and uses xorriso to embed the GameXWIN design resource folder at /GameXWIN. It attempts to preserve the original boot configuration using xorriso's boot replay.

**Important limitations:** This is not yet a working GameXWIN Android desktop. It does not modify Android's system image, install an Aether Launcher APK, integrate Wine, or change Android's actual UI. No guarantee that the repacked ISO boots. The upstream image is from 2024. The GitHub workflow defaults to NOT uploading the modified GApps ISO because Google apps and other bundled components may have redistribution restrictions. Only enable upload if you have verified applicable rights.

GitHub Actions > GameXWIN Android - Bliss OS 16 experimental > Run workflow. Build report is uploaded even when ISO publication is disabled. For real UI implementation, develop and test a standalone launcher APK and accessibility-safe controls before integrating into an Android image.
