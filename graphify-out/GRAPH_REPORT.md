# Graph Report - 26.2  (2026-08-02)

## Corpus Check
- 80 files · ~67,867 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 740 nodes · 1371 edges · 57 communities (32 shown, 25 thin omitted)
- Extraction: 91% EXTRACTED · 9% INFERRED · 0% AMBIGUOUS · INFERRED: 124 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `740212b0`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Rendering Utilities
- Client Event Handlers
- Particle Effects
- Server Configuration Sync
- Player Activity Management
- Mod Configurations
- Sprite Set Handling
- Player GUI State
- Command Execution
- Player Status Tracking
- Custom Packet Payloads
- Status Management
- Entity Rendering
- Client Player Status
- Server-Sent NBT Packets
- Particle Textures
- Client GUI Rendering
- Player Status Manager
- Animation Handling
- Screen Particle Renderer
- ByteBuffer Processing
- Shader Blurring
- Build Tools
- Crafting Particle Texture 1
- Crafting Particle Texture 2
- Dispenser Particle Texture
- Enchanting Table Particle Texture
- Escape Menu Particle Texture 0
- Escape Menu Particle Texture 1
- Escape Menu Particle Texture 2
- Furnace Particle Texture
- Grindstone Particle Texture
- Hopper Particle Texture
- Horse Particle Texture
- Idle Particle Texture
- Inventory Particle Texture 0
- Inventory Particle Texture 1
- Inventory Particle Texture 2
- Loom Particle Texture
- Mouse Particle Texture
- Shulker Box Particle Texture
- Sign Particle Texture
- Villager Particle Texture
- Flujo de trabajo — Player Activity View (Fabric)
- ParticleItem.java
- ServerConfigSyncHelper
- Player Activity View
- ScreenExtractRenderStateWithTooltipMixin.java
- Changelog
- .tickPlayerClient
- CLAUDE.md — player_activity_view (26.2)
- Project Description

## God Nodes (most connected - your core abstractions)
1. `PlayerStatus` - 77 edges
2. `PlayerStatusManagerClient` - 46 edges
3. `ScreenData` - 37 edges
4. `PlayerGuiState` - 33 edges
5. `PlayerStatusManagerServer` - 20 edges
6. `ParticleRotating` - 19 edges
7. `PlayerStatusClient` - 18 edges
8. `Version 0.0.0-beta.1 Changelog` - 18 edges
9. `PlayerActivity` - 17 edges
10. `SpriteSetPlayer` - 16 edges

## Surprising Connections (you probably didn't know these)
- `Chat Typing 0 Particle Texture` ----> `Version 0.0.0-beta.1 Changelog`  [EXTRACTED]
  src/main/resources/assets/player_activity_view/textures/particle/chat_typing_0.png → docs/curseforge/versions/0.0.0-beta.1.md
- `Chat Typing 1 Particle Texture` ----> `Version 0.0.0-beta.1 Changelog`  [EXTRACTED]
  src/main/resources/assets/player_activity_view/textures/particle/chat_typing_1.png → docs/curseforge/versions/0.0.0-beta.1.md
- `Chat Typing 2 Particle Texture` ----> `Version 0.0.0-beta.1 Changelog`  [EXTRACTED]
  src/main/resources/assets/player_activity_view/textures/particle/chat_typing_2.png → docs/curseforge/versions/0.0.0-beta.1.md
- `Chat Typing 3 Particle Texture` ----> `Version 0.0.0-beta.1 Changelog`  [EXTRACTED]
  src/main/resources/assets/player_activity_view/textures/particle/chat_typing_3.png → docs/curseforge/versions/0.0.0-beta.1.md
- `Chat Typing 4 Particle Texture` ----> `Version 0.0.0-beta.1 Changelog`  [EXTRACTED]
  src/main/resources/assets/player_activity_view/textures/particle/chat_typing_4.png → docs/curseforge/versions/0.0.0-beta.1.md

## Import Cycles
- None detected.

## Communities (57 total, 25 thin omitted)

### Community 0 - "Rendering Utilities"
Cohesion: 0.06
Nodes (16): Accessor, DynamicTexture, Minecraft, NativeImage, ScreenRectangle, ByteBuffer, Logger, RenderHelper (+8 more)

### Community 1 - "Client Event Handlers"
Cohesion: 0.07
Nodes (31): ClientModInitializer, LevelRenderContext, Pose, ExtractPingIconInjectMixin, CallbackInfo, GuiGraphicsExtractor, Inject, Mixin (+23 more)

### Community 2 - "Particle Effects"
Cohesion: 0.07
Nodes (20): Camera, Layer, QuadParticleRenderState, Quaternionf, SingleQuadParticle, ClientLevel, Override, ParticleRenderType (+12 more)

### Community 3 - "Server Configuration Sync"
Cohesion: 0.08
Nodes (17): AbstractContainerMenu, FakePlayerHelper, Player, InventorySnapshot, ItemStack, Player, Vec3, PlayerStatusManager (+9 more)

### Community 4 - "Player Activity Management"
Cohesion: 0.07
Nodes (30): BlockHitResult, BlockState, ContainerInput, InteractionResult, MinecraftServer, ModInitializer, PlayerList, GameRendererPreloadUiShaderMixin (+22 more)

### Community 5 - "Mod Configurations"
Cohesion: 0.11
Nodes (12): JsonObject, ConfigClient, ConfigCommon, ServerSyncedConfig, BooleanValue, Builder, ConfigValue, DoubleValue (+4 more)

### Community 6 - "Sprite Set Handling"
Cohesion: 0.09
Nodes (15): RandomSource, SpriteSet, TextureAtlas, ModParticles, ClientLevel, Override, ParticleAnimated, ClientLevel (+7 more)

### Community 7 - "Player GUI State"
Cohesion: 0.07
Nodes (31): canPreventIdleInGui(), get(), isPointingGui(), isSoundMakerGui(), isTypingGui(), PlayerChatState, CHAT_FOCUSED, CHAT_TYPING (+23 more)

### Community 8 - "Command Execution"
Cohesion: 0.13
Nodes (11): CommandDispatcher, CommandSourceStack, EquipmentSlot, FabricClientCommandSource, CommandReloadConfig, Adjustment, CustomArmCorrections, ItemStack (+3 more)

### Community 9 - "Player Status Tracking"
Cohesion: 0.08
Nodes (4): ModelPartData, BlockPos, CompoundTag, PlayerStatus

### Community 10 - "Custom Packet Payloads"
Cohesion: 0.08
Nodes (28): CustomPacketPayload, PacketBase, CompoundTag, CustomPacketPayload, FriendlyByteBuf, Override, Player, StreamCodec (+20 more)

### Community 11 - "Status Management"
Cohesion: 0.17
Nodes (4): CompoundTag, Logger, Pair, PlayerStatusManagerClient

### Community 12 - "Entity Rendering"
Cohesion: 0.23
Nodes (9): Entity, EntityRenderStateTrackerMixin, CallbackInfoReturnable, EntityRenderState, Inject, Mixin, EntityRenderStateTracker, EntityRenderState (+1 more)

### Community 13 - "Client Player Status"
Cohesion: 0.19
Nodes (4): Particle, PlayerStatusClient, Player, Vec3

### Community 14 - "Server-Sent NBT Packets"
Cohesion: 0.11
Nodes (16): KeyEvent, MouseButtonInfo, ClientEvents, CallbackInfo, Inject, Mixin, KeyPressMixin, CallbackInfo (+8 more)

### Community 15 - "Particle Textures"
Cohesion: 0.11
Nodes (19): Chat Typing 0 Particle Texture, Chat Typing 1 Particle Texture, Chat Typing 2 Particle Texture, Chat Typing 3 Particle Texture, Chat Typing 4 Particle Texture, Chat Typing 5 Particle Texture, Chest 0 Particle Texture, Chest 1 Particle Texture (+11 more)

### Community 16 - "Client GUI Rendering"
Cohesion: 0.16
Nodes (8): HumanoidModel, ParticleEngine, CallbackInfo, GuiGraphicsExtractor, HumanoidRenderState, Level, PlayerInfo, Screen

### Community 17 - "Player Status Manager"
Cohesion: 0.12
Nodes (15): Changelog, Claves parseables por el script genérico, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, IDs de `gameVersions` para 26.2 (Fabric), Parámetros del upload (+7 more)

### Community 19 - "Screen Particle Renderer"
Cohesion: 0.25
Nodes (5): ScreenParticleRenderer, CallbackInfo, Inject, Mixin, PostChainResizeMixin

### Community 22 - "Build Tools"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 45 - "Flujo de trabajo — Player Activity View (Fabric)"
Cohesion: 0.15
Nodes (12): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Player Activity View (Fabric), Flujo por tarea, Idioma (+4 more)

### Community 46 - "ParticleItem.java"
Cohesion: 0.31
Nodes (6): Particle, ClientLevel, ItemStack, Override, ParticleRenderType, ParticleItem

### Community 47 - "ServerConfigSyncHelper"
Cohesion: 0.33
Nodes (3): CompoundTag, SuppressWarnings, ServerConfigSyncHelper

### Community 48 - "Player Activity View"
Cohesion: 0.25
Nodes (7): Building from source, Commands, Credits, Features, License, Player Activity View, Requirements

### Community 49 - "ScreenExtractRenderStateWithTooltipMixin.java"
Cohesion: 0.39
Nodes (5): CallbackInfo, GuiGraphicsExtractor, Inject, Mixin, ScreenExtractRenderStateWithTooltipMixin

### Community 50 - "Changelog"
Cohesion: 0.33
Nodes (5): [0.0.0-beta.1] - 2026-08-02, [0.0.0-beta.2] - 2026-08-02, Changelog, Fix, Port

### Community 52 - "CLAUDE.md — player_activity_view (26.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — player_activity_view (26.2), Prioridad de instrucciones, Workflow del mod

## Knowledge Gaps
- **96 isolated node(s):** `NONE`, `CHAT_SCREEN`, `INVENTORY`, `CRAFTING`, `ESCAPE` (+91 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **25 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PlayerStatus` connect `Player Status Tracking` to `Rendering Utilities`, `Client Event Handlers`, `Server Configuration Sync`, `Player GUI State`, `Status Management`, `Client Player Status`, `Client GUI Rendering`, `Animation Handling`, `.tickPlayerClient`?**
  _High betweenness centrality (0.180) - this node is a cross-community bridge._
- **Why does `PlayerStatusManagerClient` connect `Status Management` to `Client Event Handlers`, `Server Configuration Sync`, `Player Status Tracking`, `Client Player Status`, `Client GUI Rendering`, `Animation Handling`, `.tickPlayerClient`, `Shader Blurring`?**
  _High betweenness centrality (0.134) - this node is a cross-community bridge._
- **Why does `ParticleRotating` connect `Particle Effects` to `Client GUI Rendering`, `Sprite Set Handling`?**
  _High betweenness centrality (0.060) - this node is a cross-community bridge._
- **What connects `NONE`, `CHAT_SCREEN`, `INVENTORY` to the rest of the system?**
  _96 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Rendering Utilities` be split into smaller, more focused modules?**
  _Cohesion score 0.060285563194077206 - nodes in this community are weakly interconnected._
- **Should `Client Event Handlers` be split into smaller, more focused modules?**
  _Cohesion score 0.06802721088435375 - nodes in this community are weakly interconnected._
- **Should `Particle Effects` be split into smaller, more focused modules?**
  _Cohesion score 0.07399577167019028 - nodes in this community are weakly interconnected._