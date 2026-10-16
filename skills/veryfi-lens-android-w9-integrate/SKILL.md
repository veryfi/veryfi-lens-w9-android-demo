---
name: veryfi-lens-android-w9-integrate
description: "Integrates the Veryfi Lens Android W-9 SDK (com.veryfi.lens:veryfi-lens-w9-sdk:2.7.0.6): Maven setup, VeryfiCloudAPI, VeryfiLens.configure, VeryfiLensSettings, delegate callbacks, showCamera, showGallery, showDocumentBrowser, and uploadImages for W-9 PDFs. Use when adding W-9 capture or upload on Android."
---

# Veryfi Lens Android — W-9

Walk a host Android app from Gradle to a working first scan. Change the fewest flags. Do not invent SDK APIs. Facts were read from the Android SDK at version `2.7.0.6` (commit `7f33726c`). Anything not confirmed there is marked **Unconfirmed**.

**Coordinate:** `com.veryfi.lens:veryfi-lens-w9-sdk:2.7.0.6`  
**Gradle flavor:** `lensW9`  
**Allowed document types:** `DocumentType.W9` only.

Public entry point: `com.veryfi.lens.VeryfiLens` with `com.veryfi.lens.VeryfiLensDelegate`. Settings type: `com.veryfi.lens.helpers.VeryfiLensSettings`.

This folder is complete by itself. Duplicate guidance across products is intentional.

## Pick a workflow

- **Quick** — the host already asked for this product. Ask nothing else. Apply the settings in [settings.md](settings.md), wire [implementation.md](implementation.md), and state the assumptions below.
- **Guided** — something material is missing. Ask only the unknown questions (at most four, one message), then continue.

### Quick workflow

```
veryfi-lens-android-w9-integrate quick:
- [ ] Confirm this artifact (install.md)
- [ ] Add the Maven repository and this one dependency
- [ ] Permissions (install.md)
- [ ] Credentials + settings from settings.md
- [ ] setDelegate, then configure { ok -> }, then showCamera() only when ok is true
- [ ] Handle the callbacks in callbacks.md
- [ ] Run the conflicts in troubleshooting.md
```

Assumptions when the host was specific about the product and silent on the rest: Lens camera plus file browser, `W9`, Cloud API, stitch off, gallery on, fraud not promised.

### Guided workflow

1. **Entry** — camera, gallery, or PDF via Files / `uploadImages(uris)`?
2. **Pages** — one W-9, or stitch?
3. **Extraction** — Cloud (default) or decoupled `None`?

Defaults: camera, gallery, browser button, Cloud API, stitch off.


## Rules

1. `DocumentType.W9` and `VeryfiCloudAPI`.
2. `W9DetectorMode` calls `initDocumentDetector` with `isFraudDetectionOn = false`.
3. PDFs use the `Uri` upload or `showDocumentBrowser()`.
4. `uploadImages` stays on this UI SDK.


## Do not

- Copy a repository password, keystore, or credential from the SDK repo or the Demo app. Maven access comes from Veryfi. Keep it in `~/.gradle/gradle.properties` or CI secrets.
- Depend on a second Lens UI flavor, or on `com.veryfi.lens.core:veryfi-lens-core-sdk` (email and obsolete dictation; adds `WRITE_CONTACTS` and `RECORD_AUDIO`).
- Treat Demo types as the client contract: `SettingsHolder`, Demo analyzers, `ImageProxyUtils`, `VeryfiLensHelper`.
- Set `dictateIsOn = true` or call `VeryfiLensCore.showDictation()`.
- Set `anyDocumentType` (deprecated local regex OCR). A new template id is `anyDocumentTemplate`, and only after Veryfi support confirms it.
- Set `useLegacyModels` (default true; no partner contract).
- Quote AAR size as the installed app size. Compare the final app with the SDK linked and removed. [SDK binary sizes](https://docs.veryfi.com/lens/mobile/settings/sdk_binary_sizes/).
- Treat `allowSubmittingScreenshots` (rejects imported screenshots) and `enableScreenshots` (device screenshots of Lens screens, default true) as one switch.
- Promise fraud detection on W-9.
- Use Lite for `W9`.
- Pass PDFs to `uploadImages(paths)`.


## What to hand back

1. `implementation "com.veryfi.lens:veryfi-lens-w9-sdk:2.7.0.6"`.
2. `w9Settings()`.
3. Cloud `data` handling.
4. Fraud called out as disabled for the W-9 detector.


## Files in this folder

- [install.md](install.md) — Maven coordinate, requirements, permissions, app size
- [implementation.md](implementation.md) — configure, delegate, and launch or camera lifecycle
- [settings.md](settings.md) — `VeryfiLensSettings` for this product, with Android defaults
- [callbacks.md](callbacks.md) — delegate payloads
- [troubleshooting.md](troubleshooting.md) — symptom, cause, fix
- [clarifications.md](clarifications.md) — Unconfirmed items, Any-Documents, fraud, field detection
- [checklist.md](checklist.md) — validation checklist before handing code to the host

