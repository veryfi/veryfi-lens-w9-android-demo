# Veryfi Lens Android — W-9 — settings

Source of truth: Veryfi Lens Android SDK **2.7.0.6**, commit `7f33726c`. Names below were read from that tree. Items not fully confirmed are marked **Unconfirmed**. Do not copy repository passwords, keystores, or Demo-only types into the host app.


Always construct a fresh `VeryfiLensSettings()`. Android has no `VeryfiLens*Settings` subclasses. Pass that instance to `configure`. The SDK mutates it.

```kotlin
import com.veryfi.lens.helpers.DocumentType
import com.veryfi.lens.helpers.VeryfiLensSettings

fun w9Settings() = VeryfiLensSettings().apply {
    ignoreRemoteSettings = true            // default false; dashboard lens_settings overwrite this object during configure
    backupDocsToGallery = false            // default true; copies every scan into the device gallery
    moreMenuIsOn = false                   // default true; stored user prefs then replace host capture flags
    moreSettingsMenuIsOn = false           // default true
    autoCaptureOtherIsOn = false           // default true; OTHER auto-captures even when autoCaptureIsOn is false
    showNoDocumentDetectedWarning = false  // default true
    dictateIsOn = false                    // default true; obsolete voice capture, not a partner feature
    locationServicesIsOn = false           // default false; true raises a runtime location request
    glareDetectionIsOn = false             // default true; the glare gate is a weak algorithmic check

    dataExtractionEngine = VeryfiLensSettings.ExtractionEngine.VeryfiCloudAPI
    documentTypes = arrayListOf(DocumentType.W9)
    showDocumentTypes = false
    autoCaptureIsOn = false
    stitchIsOn = false
    galleryIsOn = true
    showBrowserButton = true
    autoRotateIsOn = true
    cleanBorderIsOn = true
    returnStitchedPDF = true
}

```

## Defaults versus this recipe

Android initializers below are the property defaults in `VeryfiLensSettings`.

| Property | Android default | This recipe | Why |
|----------|-----------------|-------------|-----|
| `ignoreRemoteSettings` | false | true | Dashboard `lens_settings` would overwrite this object during configure |
| `backupDocsToGallery` | true | false | Default copies every scan into the device gallery |
| `moreMenuIsOn` | true | false | Stored user prefs would replace host capture flags on later configure calls |
| `moreSettingsMenuIsOn` | true | false | Settings sheet inside More |
| `autoCaptureOtherIsOn` | true | false | `OTHER` auto-captures even when `autoCaptureIsOn` is false |
| `showNoDocumentDetectedWarning` | true | false | Skip the no-document alert |
| `dictateIsOn` | true | false | Obsolete voice control; default is on |
| `glareDetectionIsOn` | true | false | Android default glare gate is a weak check |
| `locationServicesIsOn` | false | false | Leave off unless the host needs lat/lng on the package |

| `documentTypes` | null | `W9` | Only type allowed here |
| `stitchIsOn` | true | false | Single form unless requested |
| `showBrowserButton` | false | true | PDF and file import from the camera |
| `autoRotateIsOn` | false | true | Rotate the form |
| `cleanBorderIsOn` | false | true | Clean borders |
| `returnStitchedPDF` | false | true | Stitched PDF path on success |
| `fraudDetectionIsOn` | false | false | W-9 detector forces fraud off |


Same crop-model request as other document types in `getRequiredModels` (`CropDocument` for `W9`). Field-detection models are not included. Fraud is hard-disabled in `W9DetectorMode`.

## Suggestions

- Use `uploadImages(uris)` when the host receives a W-9 PDF from a share intent.
- Size alternative: `veryfi-lens-remote-models-sdk` allows `W9`. Lite does not.
- Compare installed app size.

