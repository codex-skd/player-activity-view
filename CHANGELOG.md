# Changelog

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
