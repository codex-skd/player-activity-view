# Changelog

## [0.0.0-beta.2] - 2026-08-01

### Fix
- Typing indicator ("X is typing...") no longer renders on screen: `GuiExtractRenderStateMixin` was
  left with the 26.1.2 signature `(GuiGraphicsExtractor, DeltaTracker)` but NeoForge 26.2.0.37-beta
  changed `Gui.extractRenderState` to `(DeltaTracker, boolean, boolean)` (it no longer receives the
  extractor — the HUD extraction now lives in `Hud.extractRenderState`). The mixin failed to apply,
  so `onGuiRender` never drew the typing overlay. Retargeted the mixin to `Hud.extractRenderState`.
- Inventory screen mirror no longer shows the whole window: in 26.2 the container background is
  extracted as a full-screen element, so the element bounding box spanned the entire window and the
  union with the deterministic panel rectangle could not shrink it. The crop now clamps to the
  `AbstractContainerScreen` panel rectangle (`leftPos`/`topPos`/`imageWidth`/`imageHeight`) instead
  of unioning, so the mirror shows just the inventory panel again.

## [0.0.0-beta.1] - 2026-08-01

### Port
- Full port to Minecraft 26.2 / NeoForge 26.2.0.32-beta from the `26.1.2` branch (v1.0.0). Build
  metadata updated (`gradle.properties`, `build.gradle`, moddev plugin 2.0.142) and 26.2 API
  accessors adopted:
  - `mc.screen` → `mc.gui.screen()` (screen field moved from `Minecraft` to `Gui`)
  - `mc.getMainRenderTarget()` → `mc.gameRenderer.mainRenderTarget()`
  - `mc.renderBuffers()` → `mc.gameRenderer.renderBuffers()`
  - `mc.gameRenderer.getMainCamera()` → `mc.gameRenderer.mainCamera()`
- Includes the complete feature set of the stable 26.1.2 release: typing indicators, GUI visualizer,
  live screen mirror, idle detection, inventory animations, arm animations, privacy controls and
  server-synced config.
