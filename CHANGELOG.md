# Changelog

## [1.0.0] - 2026-08-02

### Release
- First stable release for Minecraft 26.2. Promoted from beta after 3 beta iterations: typing
  indicators, GUI visualizer, live screen mirror, idle detection, inventory animations, arm
  animations, privacy controls and server-synced config are all functional and stable.
- No code changes from `0.0.0-beta.3`.

## [0.0.0-beta.3] - 2026-08-02

### Fix
- Chat hands now raise when the chat screen is open, not only once text is being typed: the typing
  pose (arms raised toward the screen, alternating anti-phase sway so one arm rises while the other
  falls) previously only triggered on `CHAT_TYPING`. Since opening the chat reports `CHAT_FOCUSED`
  (text box focused, nothing typed yet), the arms stayed down. The pose now applies to any typing
  GUI (`CHAT_SCREEN`, `EDIT_BOOK`, `EDIT_SIGN`, `COMMAND_BLOCK`) with a chat state other than
  `NONE`, so the "writing" animation plays from the moment the screen opens.

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
