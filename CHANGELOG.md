# Changelog


## [1.0.1] - 2026-08-12

### Change

- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `player_activity_view-26.2-fabric-0.19.3-1.0.1.jar` (se añade la versión de cargador/NeoForge al nombre del archivo). Empaquetado y documentación; sin cambios de funcionalidad.

## [1.0.0] - 2026-08-02

### Release
- First stable release for Minecraft 26.2 on Fabric. Promoted from beta after 3 beta iterations: typing
  indicators, GUI visualizer, live screen mirror, idle detection, inventory animations, arm animations,
  privacy controls and server-synced config are all functional and stable.
- No code changes from `0.0.0-beta.3`.

## [0.0.0-beta.3] - 2026-08-02

### Fix
- **Screen mirror / GUI visualizer not updating**: the client ticked `tickGame()` before
  `tickPlayerClient()`, so `playerGuiState` was still `NONE` when `tickGame` evaluated the capture
  condition — `canRenderNewGUI` was true but `validGui` was false on the frame the GUI opened, and by
  the next tick `lastScreen == screen` made `canRenderNewGUI` false. The screen capture therefore only
  happened once per session. Reordered the client tick so `tickPlayerClient` runs before `tickGame`
  (matching NeoForge, where `PlayerTickEvent` precedes `ClientTickEvent.Post`), restoring live screen
  mirror updates.
- **NPE on chunked screen packets**: the partial-buffer guard for multi-packet screen data could hit
  `existing == null` when the `packetIndex=1` chunk arrived before `packetIndex=0`; the receiver now
  tolerates out-of-order chunks instead of throwing.

## [0.0.0-beta.2] - 2026-08-02

### Fix
- **Kick on join / ClassCastException**: `PayloadTypeRegistry.register()` was never invoked — only the
  networking instance was constructed, so the server dispatched `nbt_client` as `DiscardedPayload` and
  kicked players on join. The payload types are now registered in `onInitialize` and the clientbound
  receiver registers directly in `PlayerActivityClient.onInitializeClient()` (the previous late-set hook
  never ran because `ModInitializer.onInitialize` executes before `ClientModInitializer.onInitializeClient`).

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
