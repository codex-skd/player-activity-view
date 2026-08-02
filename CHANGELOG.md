# Changelog

## [0.0.0-beta.1] - 2026-08-02

### Port
- First Fabric port of the NeoForge 26.2 build. Everything compiles against Minecraft 26.2 + Fabric API
  0.156.0 with Loom split environment source sets: server/common code lives in `src/main/java`, client-only
  code (particles, screen mirror, arm animation hooks, input mixins) in `src/client/java`.
- Networking reimplemented on Fabric `PayloadTypeRegistry` + `ClientPlayNetworking`/`ServerPlayNetworking`
  (same custom payload records as the NeoForge build; dispatch of serverbound packets stays in the server
  entrypoint, clientbound dispatch lives in the client source set).
- Config reimplemented without NeoForge `ModConfigSpec`: `config/spec/ModConfigSpec` persists the same
  keys to JSON files in the Fabric config dir (`player_activity_view-common/client/server.json`), keeping
  the `get()`/`set()` API so all consumers are unchanged.
- `SubmitCustomGeometryEvent` (NeoForge render hook) replaced with Fabric `LevelRenderEvents.COLLECT_SUBMITS`
  for the live screen mirror billboard. `RenderFrameEvent.Post`, client input and command registration
  replaced with Fabric events/mixins (`GameRenderer.render`, `MouseHandler.onButton`, `KeyboardHandler.keyPress`,
  `ClientCommandRegistrationCallback`).
- `PlayerStatus` split: the shared class is client-neutral; `PlayerStatusClient` (client source set) holds the
  particle handles and `ScreenData` of the mirror. The `accesstransformer.cfg` entries that were still needed
  are covered by existing accessor mixins.

## [0.0.0-beta.1] - 2026-08-02

### Port
- Same as above (initial Fabric port, single entry).
