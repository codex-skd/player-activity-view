# Changelog

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
