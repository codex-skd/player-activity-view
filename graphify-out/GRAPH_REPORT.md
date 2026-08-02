# Graph Report - 26.2  (2026-08-02)

## Corpus Check
- 81 files · ~68,091 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 743 nodes · 1373 edges · 52 communities (30 shown, 22 thin omitted)
- Extraction: 91% EXTRACTED · 9% INFERRED · 0% AMBIGUOUS · INFERRED: 124 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `b031c445`
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
- Player Status Manager
- Screen Particle Renderer
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
- Player Activity View
- ScreenExtractRenderStateWithTooltipMixin.java
- Changelog
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

## Communities (52 total, 22 thin omitted)

### Community 0 - "Rendering Utilities"
Cohesion: 0.05
Nodes (18): Accessor, DynamicTexture, Minecraft, NativeImage, ScreenRectangle, ByteBufferProcessor, ByteBufferProcessorCallback, ByteBuffer (+10 more)

### Community 1 - "Client Event Handlers"
Cohesion: 0.09
Nodes (26): ClientModInitializer, ExtractPingIconInjectMixin, CallbackInfo, GuiGraphicsExtractor, Inject, Mixin, PlayerInfo, GuiExtractRenderStateMixin (+18 more)

### Community 2 - "Particle Effects"
Cohesion: 0.06
Nodes (26): Camera, Layer, Particle, QuadParticleRenderState, Quaternionf, SingleQuadParticle, ClientLevel, Override (+18 more)

### Community 3 - "Server Configuration Sync"
Cohesion: 0.07
Nodes (18): AbstractContainerMenu, FakePlayerHelper, Player, InventorySnapshot, ItemStack, BlockPos, Player, Vec3 (+10 more)

### Community 4 - "Player Activity Management"
Cohesion: 0.09
Nodes (19): ContainerInput, MinecraftServer, ModInitializer, PlayerList, GameRendererPreloadUiShaderMixin, CallbackInfo, Inject, Mixin (+11 more)

### Community 5 - "Mod Configurations"
Cohesion: 0.12
Nodes (12): JsonObject, ConfigClient, ConfigCommon, ServerSyncedConfig, BooleanValue, Builder, ConfigValue, DoubleValue (+4 more)

### Community 6 - "Sprite Set Handling"
Cohesion: 0.09
Nodes (15): RandomSource, SpriteSet, TextureAtlas, ModParticles, ClientLevel, Override, ParticleAnimated, ClientLevel (+7 more)

### Community 7 - "Player GUI State"
Cohesion: 0.07
Nodes (31): canPreventIdleInGui(), get(), isPointingGui(), isSoundMakerGui(), isTypingGui(), PlayerChatState, CHAT_FOCUSED, CHAT_TYPING (+23 more)

### Community 8 - "Command Execution"
Cohesion: 0.09
Nodes (16): EquipmentSlot, ParticleEngine, CallbackInfo, Level, PlayerInfo, Screen, Vec3, Adjustment (+8 more)

### Community 9 - "Player Status Tracking"
Cohesion: 0.05
Nodes (13): HumanoidModel, CompoundTag, GuiGraphicsExtractor, HumanoidRenderState, Logger, Override, Pair, Player (+5 more)

### Community 10 - "Custom Packet Payloads"
Cohesion: 0.08
Nodes (28): CustomPacketPayload, PacketBase, CompoundTag, CustomPacketPayload, FriendlyByteBuf, Override, Player, StreamCodec (+20 more)

### Community 11 - "Status Management"
Cohesion: 0.28
Nodes (11): BlockHitResult, BlockState, InteractionResult, BlockBehaviorUseMixin, BlockPos, CallbackInfoReturnable, Inject, ItemStack (+3 more)

### Community 12 - "Entity Rendering"
Cohesion: 0.23
Nodes (9): Entity, EntityRenderStateTrackerMixin, CallbackInfoReturnable, EntityRenderState, Inject, Mixin, EntityRenderStateTracker, EntityRenderState (+1 more)

### Community 13 - "Client Player Status"
Cohesion: 0.17
Nodes (7): LevelRenderContext, Pose, Particle, PlayerStatusClient, DynamicScreenRenderer, Logger, VertexConsumer

### Community 14 - "Server-Sent NBT Packets"
Cohesion: 0.09
Nodes (20): CommandDispatcher, CommandSourceStack, FabricClientCommandSource, KeyEvent, MouseButtonInfo, ClientEvents, CallbackInfo, Inject (+12 more)

### Community 15 - "Particle Textures"
Cohesion: 0.11
Nodes (19): Chat Typing 0 Particle Texture, Chat Typing 1 Particle Texture, Chat Typing 2 Particle Texture, Chat Typing 3 Particle Texture, Chat Typing 4 Particle Texture, Chat Typing 5 Particle Texture, Chest 0 Particle Texture, Chest 1 Particle Texture (+11 more)

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

### Community 48 - "Player Activity View"
Cohesion: 0.25
Nodes (7): Building from source, Commands, Credits, Features, License, Player Activity View, Requirements

### Community 49 - "ScreenExtractRenderStateWithTooltipMixin.java"
Cohesion: 0.39
Nodes (5): CallbackInfo, GuiGraphicsExtractor, Inject, Mixin, ScreenExtractRenderStateWithTooltipMixin

### Community 50 - "Changelog"
Cohesion: 0.25
Nodes (7): [0.0.0-beta.1] - 2026-08-02, [0.0.0-beta.2] - 2026-08-02, [0.0.0-beta.3] - 2026-08-02, Changelog, Fix, Fix, Port

### Community 52 - "CLAUDE.md — player_activity_view (26.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — player_activity_view (26.2), Prioridad de instrucciones, Workflow del mod

## Knowledge Gaps
- **97 isolated node(s):** `NONE`, `CHAT_SCREEN`, `INVENTORY`, `CRAFTING`, `ESCAPE` (+92 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **22 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PlayerStatus` connect `Player Status Tracking` to `Rendering Utilities`, `Server Configuration Sync`, `Client Player Status`, `Player GUI State`?**
  _High betweenness centrality (0.179) - this node is a cross-community bridge._
- **Why does `PlayerStatusManagerClient` connect `Player Status Tracking` to `Client Event Handlers`, `Server Configuration Sync`, `Command Execution`, `Client Player Status`, `Shader Blurring`?**
  _High betweenness centrality (0.133) - this node is a cross-community bridge._
- **Why does `ParticleRotating` connect `Particle Effects` to `Command Execution`, `Sprite Set Handling`?**
  _High betweenness centrality (0.059) - this node is a cross-community bridge._
- **What connects `NONE`, `CHAT_SCREEN`, `INVENTORY` to the rest of the system?**
  _97 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Rendering Utilities` be split into smaller, more focused modules?**
  _Cohesion score 0.05070422535211268 - nodes in this community are weakly interconnected._
- **Should `Client Event Handlers` be split into smaller, more focused modules?**
  _Cohesion score 0.08636977058029689 - nodes in this community are weakly interconnected._
- **Should `Particle Effects` be split into smaller, more focused modules?**
  _Cohesion score 0.0593990216631726 - nodes in this community are weakly interconnected._