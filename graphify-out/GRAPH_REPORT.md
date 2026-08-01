# Graph Report - 26.2  (2026-08-01)

## Corpus Check
- 74 files · ~67,548 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 681 nodes · 943 edges · 171 communities (24 shown, 147 thin omitted)
- Extraction: 85% EXTRACTED · 15% INFERRED · 0% AMBIGUOUS · INFERRED: 141 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `fa347255`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Community 0
- Community 1
- Community 2
- Community 3
- Community 4
- Community 5
- Community 6
- Community 7
- Community 8
- Community 10
- Community 11
- Community 12
- Community 14
- Community 15
- Community 16
- Community 17
- Community 18
- Community 19
- Community 20
- Community 21
- Community 22
- Community 23
- Community 24
- Community 25
- ShaderInstanceBlur
- CLAUDE.md — player_activity_view (26.1.2)
- 0.0.0-beta.2 — Rebranding & Package Update
- gradlew
- ModelPartData
- SetupAnimInjectMixin.java
- Config
- ExtractPingIconInjectMixin
- 0.0.0-beta.3.md
- 0.0.0-beta.4.md
- 0.0.0-beta.5.md
- 0.0.0-beta.6.md
- Adjustment
- ConfigClient
- ConfigCommon
- HeldItemArmAdjustment
- HeldItemArmAdjustmentLists
- GuiExtractRenderStateMixin.java
- ModelPartData
- BooleanValue
- Builder
- ConfigValue
- DoubleValue
- IntValue
- ModConfigSpec
- BooleanValue
- Builder
- IntValue
- ModConfigSpec
- ItemStack
- BooleanValue
- Builder
- ConfigValue
- IntValue
- ModConfigSpec
- CompoundTag
- BooleanValue
- Builder
- DoubleValue
- IntValue
- ModConfigSpec
- Player
- ItemStack
- CallbackInfo
- Inject
- Mixin
- Player
- BlockPos
- CallbackInfoReturnable
- Inject
- ItemStack
- Level
- Mixin
- Player
- CallbackInfoReturnable
- EntityRenderState
- Inject
- Mixin
- CallbackInfo
- GuiGraphicsExtractor
- Inject
- Mixin
- PlayerInfo
- CallbackInfo
- Inject
- Mixin
- ResourceProvider
- Mixin
- CallbackInfo
- Inject
- Mixin
- CallbackInfo
- GuiGraphicsExtractor
- Inject
- Mixin
- CallbackInfo
- GuiGraphicsExtractor
- Inject
- Mixin
- CallbackInfo
- HumanoidRenderState
- Inject
- Mixin
- TextureAtlas
- CompoundTag
- CustomPacketPayload
- FriendlyByteBuf
- Override
- Player
- StreamCodec
- Type
- CompoundTag
- CustomPacketPayload
- FriendlyByteBuf
- Override
- Player
- StreamCodec
- Type
- ClientLevel
- Override
- ClientLevel
- Override
- ParticleRenderType
- ClientLevel
- ItemStack
- Override
- ParticleRenderType
- ClientLevel
- Override
- TextureAtlasSprite
- ClientLevel
- ClientLevel
- Override
- TextureAtlasSprite
- CompoundTag
- Level
- Player
- Vec3
- CompoundTag
- Level
- Override
- Player
- StreamCodec
- Type
- Vec3
- BlockPos
- CompoundTag
- Particle
- Player
- Vec3
- BlockPos
- CompoundTag
- ItemStack
- Level
- Logger
- Override
- Pair
- Player
- EntityRenderState
- Override
- TextureAtlasSprite
- README.md

## God Nodes (most connected - your core abstractions)
1. `PlayerStatus` - 79 edges
2. `PlayerStatusManagerClient` - 43 edges
3. `PlayerGuiState` - 32 edges
4. `ScreenData` - 31 edges
5. `PlayerActivity` - 19 edges
6. `PlayerStatusManagerServer` - 19 edges
7. `ParticleRotating` - 19 edges
8. `RenderHelper` - 15 edges
9. `CurseForge — Variables del proyecto` - 14 edges
10. `PlayerActivityNetworking` - 12 edges

## Surprising Connections (you probably didn't know these)
- `PlayerStatus` --references--> `InventorySnapshot`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerStatus.java → src/main/java/com/skd/playeractivityview/InventorySnapshot.java
- `PlayerActivity` --references--> `PlayerStatusManagerServer`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerActivity.java → src/main/java/com/skd/playeractivityview/PlayerStatusManagerServer.java
- `PlayerActivity` --references--> `PlayerStatusManagerClient`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerActivity.java → src/main/java/com/skd/playeractivityview/PlayerStatusManagerClient.java
- `PlayerStatusManagerClient` --inherits--> `PlayerStatusManager`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerStatusManagerClient.java → src/main/java/com/skd/playeractivityview/PlayerStatusManager.java
- `PlayerStatusManagerClient` --references--> `ShaderInstanceBlur`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerStatusManagerClient.java → src/main/java/com/skd/playeractivityview/ShaderInstanceBlur.java

## Import Cycles
- None detected.

## Communities (171 total, 147 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.08
Nodes (16): Entity, EquipmentSlot, CustomArmCorrections, EntityRenderStateTrackerMixin, SetupAnimInjectMixin, EntityRenderStateTracker, HumanoidModel, ParticleEngine (+8 more)

### Community 1 - "Community 1"
Cohesion: 0.07
Nodes (7): AbstractContainerMenu, ContainerInput, FakePlayerHelper, InventorySnapshot, AbstractContainerMenuDoClickMixin, PlayerStatusManager, PlayerStatusManagerServer

### Community 2 - "Community 2"
Cohesion: 0.11
Nodes (8): CustomPacketPayload, PacketBase, PacketNBTFromClient, PacketNBTFromServer, PlayerActivityNetworking, PlayerActivityNetworkingNeoForge, PayloadRegistrar, RegistryFriendlyByteBuf

### Community 3 - "Community 3"
Cohesion: 0.15
Nodes (12): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Player Activity View (NeoForge), Flujo por tarea, Idioma (+4 more)

### Community 4 - "Community 4"
Cohesion: 0.06
Nodes (15): Camera, ParticleRotating, ParticleDynamic, ParticleItem, ParticleStatic, ParticleStaticPartial, Layer, Pose (+7 more)

### Community 5 - "Community 5"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 6 - "Community 6"
Cohesion: 0.14
Nodes (3): PlayerActivityClient, SpriteInfo, ModParticles

### Community 7 - "Community 7"
Cohesion: 0.05
Nodes (18): CommandDispatcher, CommandSourceStack, FMLCommonSetupEvent, ClientEvents, PlayerActivity, CommandReloadConfig, Config, ServerSyncedConfig (+10 more)

### Community 8 - "Community 8"
Cohesion: 0.06
Nodes (13): Accessor, DynamicTexture, ScreenData, NativeImageAccessorMixin, ScreenExtractRenderStateWithTooltipMixin, Minecraft, NativeImage, ScreenRectangle (+5 more)

### Community 10 - "Community 10"
Cohesion: 0.14
Nodes (5): ParticleAnimated, ParticleStaticLoD, SpriteSetPlayer, RandomSource, SpriteSet

### Community 11 - "Community 11"
Cohesion: 0.33
Nodes (5): [0.0.0-beta.1] - 2026-08-01, [0.0.0-beta.2] - 2026-08-01, Changelog, Fix, Port

### Community 12 - "Community 12"
Cohesion: 0.07
Nodes (31): canPreventIdleInGui(), get(), isPointingGui(), isSoundMakerGui(), isTypingGui(), PlayerChatState, CHAT_FOCUSED, CHAT_TYPING (+23 more)

### Community 21 - "Community 21"
Cohesion: 0.12
Nodes (15): Changelog, Claves parseables por el script genérico, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, IDs de `gameVersions` para 26.2, Parámetros del upload (+7 more)

### Community 22 - "Community 22"
Cohesion: 0.13
Nodes (4): ScreenParticleRenderer, ServerConfigSyncHelper, PostChainResizeMixin, SuppressWarnings

### Community 24 - "Community 24"
Cohesion: 0.53
Nodes (4): BlockHitResult, BlockState, BlockBehaviorUseMixin, InteractionResult

### Community 27 - "CLAUDE.md — player_activity_view (26.1.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — player_activity_view (26.2), Prioridad de instrucciones, Workflow del mod

### Community 30 - "ModelPartData"
Cohesion: 0.06
Nodes (10): Lerpables, PlayerStatus, Particle, CompoundTag, Logger, Override, Pair, Player (+2 more)

### Community 45 - "GuiExtractRenderStateMixin.java"
Cohesion: 0.43
Nodes (6): DeltaTracker, GuiExtractRenderStateMixin, CallbackInfo, GuiGraphicsExtractor, Inject, Mixin

### Community 183 - "README.md"
Cohesion: 0.29
Nodes (6): Building from source, Commands, Credits, Features, Player Activity View, Requirements

## Knowledge Gaps
- **63 isolated node(s):** `NONE`, `CHAT_SCREEN`, `INVENTORY`, `CRAFTING`, `ESCAPE` (+58 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **147 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PlayerStatus` connect `ModelPartData` to `Community 1`, `Community 4`, `Community 8`, `Community 12`, `ModelPartData`?**
  _High betweenness centrality (0.152) - this node is a cross-community bridge._
- **Why does `PlayerStatusManagerClient` connect `ModelPartData` to `Community 0`, `Community 1`, `Community 7`, `Community 22`, `ShaderInstanceBlur`?**
  _High betweenness centrality (0.132) - this node is a cross-community bridge._
- **Why does `PlayerActivity` connect `Community 7` to `Community 1`, `ModelPartData`?**
  _High betweenness centrality (0.072) - this node is a cross-community bridge._
- **What connects `NONE`, `CHAT_SCREEN`, `INVENTORY` to the rest of the system?**
  _63 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.07862903225806452 - nodes in this community are weakly interconnected._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.06567992599444958 - nodes in this community are weakly interconnected._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.1053763440860215 - nodes in this community are weakly interconnected._