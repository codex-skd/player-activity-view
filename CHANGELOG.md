# Changelog

## [0.0.0-beta.21] - 2026-07-31

### Fix
- Chat typing hands no longer stay static: a subtle sine-wave sway for the arms while typing already
  existed in the lerp target computation (`setPoseTarget`), but `onSetupAnim` — the method that
  actually sets the rendered model pose every frame — ignored it and overwrote the arms with fixed
  rotations, so the wave never rendered. The typing pose now applies the same wave directly in
  `onSetupAnim`.
- Screen mirror no longer cuts off with a hard edge at the top: the chat/GUI mirror plane rendered at
  uniform opacity, so the top of the captured screen ended abruptly instead of blending into the world.
  Vertex alpha is now derived from vertical position, keeping the bottom (chat input area) fully opaque
  and fading the top edge to transparent, on both faces of the double-sided quad.

## [0.0.0-beta.20] - 2026-07-31

### Fix
- Survival inventory screen mirror no longer crops badly or wrong: the bounding box was computed purely
  from whatever elements the screen happened to extract, and empty slots (no item and no placeholder
  sprite) contribute no bounds, so the crop's right/bottom edges shrank depending on what the player
  happened to have in their inventory. The creative palette is always full of items so it was
  unaffected — same function, different screen contents, different result. The crop now unions the
  deterministic panel rectangle (`leftPos`/`topPos`/`imageWidth`/`imageHeight`) for every container
  screen, so the full panel is always captured regardless of contents, active potion effects or recipe
  book state.
- Chat screen mirror no longer captures the whole message scrollback: the generic element bounding box
  spanned the full-width message log (most of the screen height), which made the mirrored content tiny
  and illegible once squeezed into the small panel. ChatScreen now gets a dedicated crop anchored at the
  text input field plus the four most recent message lines, matching where vanilla chat actually renders
  the input (`EditBox` at `(4, height-12)`) and the message log (ending at `(height-40)/chatScale` with
  entries of `9 * (lineSpacing + 1) * chatScale` pixels).

## [0.0.0-beta.19] - 2026-07-31

### Fix
- Own player no longer sees their own screen mirror — the 3rd-person exemption was letting the local
  player preview their own menu, which is useless and awkward since they are already interacting with
  the real GUI
- Chat screen mirror no longer renders as an extremely oversized/distorted panel: the crop bounding-
  box spanned the full-width message history, producing a huge aspect ratio that blew up the panel
  size. The renderer now clamps the effective aspect ratio and max panel half-dimension.
- Inventory screen mirror crop now covers the full inventory panel instead of a cut-off subset: the
  bounding-box computation only iterated GUI element (blit/glyph) bounds, missing item slots, text
  labels and the player-model picture-in-picture preview. All four element types are now included.
- Typing hand animation is now an alternating motion: the arms bob up/down over time using a sine
  wave per hand (phase-shifted), giving a natural typing look instead of a static pose.

### Fix
- Screen mirror read mirror-flipped (like looking at your own menu reflected) from the back-facing side
  of the double-sided quad: it reused the front face's UVs unmirrored, but a flat plane viewed from
  behind needs horizontally-flipped UVs to read correctly. Both sides now show the content readable.

### Added
- Screen mirror now also shows while the shown player is typing in chat (previously excluded), gated by
  the existing chat-GUI visibility toggle. Replaces the small animated chat icon when active, instead of
  showing both.

## [0.0.0-beta.17] - 2026-07-30

### Fix
- Screen mirror still invisible after beta.16: `sendScreenRenderData`'s single-packet branch (used for
  most captures once beta.15 added cropping, since cropped captures are small enough to fit in one
  packet) still sent the compressed byte count as the expected decompressed size — only the multi-packet
  branch had been fixed earlier. The receiver's decompression buffer ended up far too small, so
  `updateScreenTexture` always rejected the data and the texture was never built.

### Added
- Debug logging across the capture/send/receive/texture/render pipeline (`[capture]`, `[send]`, `[recv]`,
  `[texture]`, `[render]` tags) to make the next issue (if any) diagnosable straight from logs.

## [0.0.0-beta.16] - 2026-07-30

### Fix
- Screen mirror invisible after beta.15's orientation fix: the quad's vertex winding didn't match its
  declared normal, so once the billboard stopped always facing the camera (beta.15 locked it to the
  shown player's body yaw instead), the visible/uncullled face ended up pointing away from typical
  viewing angles. Now emitted double-sided (both winding orders) so it renders regardless of which way
  the pipeline culls. Also dropped the 180° yaw offset, matching the original mod's own convention.

## [0.0.0-beta.15] - 2026-07-30

### Fix
- Screen mirror rendered solid red / heavily red-tinted: vertex overlay UV was set to `(0,0)`, which lands
  on the entity "hurt" red-flash band of the overlay texture instead of the neutral/no-overlay coordinate
  (`(0,10)`). Now uses `OverlayTexture.NO_OVERLAY`.
- Screen mirror billboard was camera-facing (always turned to look at whoever's viewing it), instead of
  being a fixed tilted plane locked to the shown player's body rotation, like the original mod. Now
  oriented from the observed player's `yBodyRot` with a fixed tilt, independent of the viewer's camera.

## [0.0.0-beta.14] - 2026-07-29

### Fix
- Screen mirror captured the whole frame (3D world + dark overlay + GUI) instead of just the menu panel:
  now crops to the actual on-screen bounds of the current screen's GUI elements (plus a small padding
  border), computed via a cheap CPU-side re-extraction of the screen's layout — no extra GPU render pass

## [0.0.0-beta.13] - 2026-07-29

### Fix
- Crash on startup/main menu: `RenderFrameEvent.Post` fires before joining a server, but
  `captureScreenIfNeeded()` read `ServerSyncedConfig` (server-synced, not populated until connected)
  before checking whether a level/player even existed, crashing with
  `IllegalStateException: Cannot get config value before config is loaded`

## [0.0.0-beta.12] - 2026-07-29

### Fix
- Live screen mirror (the "watut" arm/GUI preview feature) was completely non-functional: the capture pipeline
  was stubbed out during the 26.1.2 port and never replaced, so remote players never received or rendered
  anything for it
- Fixed a network payload bug that sent the compressed byte count instead of the decompressed size, which
  would have corrupted screen data as soon as capture started producing any

### Added
- New screen capture pipeline built on this version's rendering API: captures the main render target via
  `Screenshot.takeScreenshot` on `RenderFrameEvent.Post`, downscales on CPU, compresses and sends
- New `DynamicScreenRenderer`: renders the received screen mirror as a camera-facing world-space billboard
  via `SubmitCustomGeometryEvent`, since `ParticleRenderType` no longer supports arbitrary per-instance
  textures in this Minecraft version

### Technical
- `RenderHelper.captureScreenIfNeeded()` / `updateScreenTexture()` replace the old offscreen-framebuffer
  capture and the unused `ParticleDynamic`/custom `ParticleRenderType` approach
- `ScreenData` now tracks a registered texture `Identifier` and the pre-compression payload size instead of
  a `ParticleRenderType`

## [0.0.0-beta.11] - 2026-07-26

### Fix
- Particle indicators now visible: textures moved to textures/particle/ for atlas
- Sprite initialization wired in PlayerActivityClient
- Crash prevention: null-safety in ParticleRotating

## [0.0.0-beta.10] - 2026-07-26

### Fix
- Critical crash: NullPointerException in ParticleRotating due to null sprite
- Added null-safety check before particle quad extraction

## [0.0.0-beta.9] - 2026-07-24

### Fix
- Particles not visible: replaced custom ParticleEngine with vanilla ParticleEngine
- Weird arm animations: simplified onSetupAnim to direct rotation computation

### Removed
- ParticleEngineCustom (dead code)
- ParticleEngineMixin (no longer needed)

## [0.0.0-beta.8] - 2026-07-21

### Changed
- Workflow file renamed to `WORKFLOW_PLAYER_ACTIVITY_VIEW_26-1-2.md`
- Templates moved from `src/main/templates/` to `src/main/resources/templates/`
- Removed `TEMPLATE_LICENSE.txt`
- Added `temp/` directory (gitignored)
- `build.gradle` template path updated

### Technical
- WORKFLOW updated to v1.0.0 (aligned with WORKFLOW_GENERIC)
- Added CI/CD, Graphify, naming conventions, typography, fork attribution
- Project structure now matches the generic template

## [0.0.0-beta.7] - 2025-07-16
- JAR naming: `<mod_id>-<minecraft_version>-<framework>-<version>.jar`
- Branch structure: `minecraft/<mc-version>/neoforge-<neo-version>/production`
- Tag format: `<mc-version>-neoforge-beta.X`
- WORKFLOW.md synchronized with player_animation_core conventions
- Commit language set to English

### Technical
- All project documentation aligned across repositories

## [0.0.0-beta.6] - 2025-07-14

### Added (Phase 2)
- Tab list idle indicator: shows "ZZZ" next to idle players in the player list
- Typing overlay: shows "Player is typing..." text on screen when others are typing
- Screen background cancellation: hides inventory background when player is idle
- Screen capture hook: connected screen extraction pipeline for dynamic GUI rendering
- Shader initialization: hooked into GameRenderer.preloadUiShader for custom shaders
- Custom particle engine ticking: synced with vanilla ParticleEngine.tick()
- Container click tracking: server-side inventory snapshot on container clicks
- Arm animations: hooked into HumanoidModel.setupAnim() via EntityRenderState->UUID tracking
- `EntityRenderStateTracker`: maps EntityRenderState objects to their source player UUID for animation lookup

### Changed
- All Phase 2 mixins rewritten for Minecraft 26.1.2 (1.21.4) new rendering pipeline
  - `extractRenderState`/`extractPingIcon` instead of old `render` methods
  - `setupAnim(HumanoidRenderState)` instead of `setupRotations(Entity, ...)`
  - `extractBackground`/`extractRenderStateWithTooltipAndSubtitles` instead of `renderBackground`
  - `GuiGraphicsExtractor` replaces `GuiGraphics`/`PoseStack`
- Version bumped to 0.0.0-beta.6

### Technical
- Mixins use `remap = false` for all 1.21.4-native methods (Mojang-mapped runtime, no SRG needed)
- Arm animation uses EntityRenderStateTracker (IdentityHashMap<EntityRenderState, UUID>) to bridge extraction and submission phases

### Added
- Complete fork and rewrite of WATUT (What Are They Up To) mod as **Player Activity View View**
- All code fully rewritten under `com.skd.playeractivityview` package — zero traces of original code
- Player status detection: typing, GUI interaction, idle states
- In-world particle indicators above player heads showing GUI type and chat state
- 34 custom particle textures: inventory, chest, crafting table, furnace, enchanting table, anvil, beacon, brewing stand, dispenser, grindstone, hopper, horse, loom, villager, command block, sign, book, chat typing/idle animations, idle indicator
- Custom particle engine with dedicated render pass
- Server-client networking via NeoForge payload system
- Server-controlled config synced to all clients
- Client config with privacy toggles and visual settings
- Typing speed detection with arm animation targets
- Mouse position tracking in GUIs with arm pointing
- Item transfer animation particles between player and container
- Custom arm correction system via JSON config for held items
- Idle state detection (configurable timeout, default 5 min)
- Idle indicator in player list tab (tab menu) and above player head
- "Player is typing..." display in chat
- Screen open/close sound effects and mouse click sounds
- Three-tier config system: Common, Client, Server-synced
- Hugo-style command `/player_activity_view reloadJSON` to reload arm adjustments
- Access transformers for required private Minecraft fields
- Custom shader pipeline (particle, Gaussian blur with circular vignette)
- New logo and full asset set rebranded from original

### Changed
- **Package**: `com.corosus.watut` → `com.skd.playeractivityview`
- **Mod ID**: `watut` → `player_activity_view`
- **Mod Name**: "What Are They Up To" → "Player Activity View View"
- **Dependency**: Removed CoroUtil dependency — now fully self-contained
- **Config system**: Replaced CoroConfigRegistry with NeoForge ModConfigSpec
- **Logging**: Replaced CULog with SLF4J
- **Networking**: Updated to modern NeoForge payload API
- All resource locations renamed from `watut:*` to `player_activity_view:*`

### Removed
- All original WATUT source code traces (package `com.corosus.watut`)
- CoroUtil library dependency
- Original logo and textures (fully replaced)
- Fabric loader support (NeoForge only)

### Technical
- Target: Minecraft 26.1.2 / NeoForge 26.1.2.78
- Java 25, Gradle 9.2.1, moddev plugin 2.0.141
- 50+ Java source files, 12 shader programs, 35 textures
