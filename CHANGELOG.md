# Changelog

## [0.0.0-beta.1] - 2025-07-13

### Added
- Complete fork and rewrite of WATUT (What Are They Up To) mod as **Player Activity**
- All code fully rewritten under `com.skd.playeractivity` package — zero traces of original code
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
- Hugo-style command `/player_activity reloadJSON` to reload arm adjustments
- Access transformers for required private Minecraft fields
- Custom shader pipeline (particle, Gaussian blur with circular vignette)
- New logo and full asset set rebranded from original

### Changed
- **Package**: `com.corosus.watut` → `com.skd.playeractivity`
- **Mod ID**: `watut` → `player_activity`
- **Mod Name**: "What Are They Up To" → "Player Activity"
- **Dependency**: Removed CoroUtil dependency — now fully self-contained
- **Config system**: Replaced CoroConfigRegistry with NeoForge ModConfigSpec
- **Logging**: Replaced CULog with SLF4J
- **Networking**: Updated to modern NeoForge payload API
- All resource locations renamed from `watut:*` to `player_activity:*`

### Removed
- All original WATUT source code traces (package `com.corosus.watut`)
- CoroUtil library dependency
- Original logo and textures (fully replaced)
- Fabric loader support (NeoForge only)

### Technical
- Target: Minecraft 26.1.2 / NeoForge 26.1.2.78
- Java 25, Gradle 9.2.1, moddev plugin 2.0.141
- 50+ Java source files, 12 shader programs, 35 textures
