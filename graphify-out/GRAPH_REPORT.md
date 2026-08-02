# Graph Report - 26.1.2  (2026-08-02)

## Corpus Check
- 93 files · ~71,044 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 751 nodes · 965 edges · 204 communities (40 shown, 164 thin omitted)
- Extraction: 85% EXTRACTED · 15% INFERRED · 0% AMBIGUOUS · INFERRED: 141 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `0eacd85a`
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
- Community 9
- Community 10
- Community 11
- Community 12
- Community 13
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
- Adjustment
- ConfigClient
- ConfigCommon
- HeldItemArmAdjustment
- HeldItemArmAdjustmentLists
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
- CallbackInfo
- GuiGraphicsExtractor
- Inject
- Mixin
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
- 1.0.0.md
- ByteBuffer
- Identifier
- Screen
- Post
- Mixin
- ClientLevel
- Override
- Identifier
- Logger
- Mod
- ModContainer
- Post
- ResourceProvider
- Mod
- ModContainer
- Player
- Logger
- TextureAtlas
- TextureAtlasSprite

## God Nodes (most connected - your core abstractions)
1. `PlayerStatus` - 75 edges
2. `PlayerStatusManagerClient` - 43 edges
3. `PlayerGuiState` - 32 edges
4. `ScreenData` - 28 edges
5. `PlayerActivity` - 19 edges
6. `PlayerStatusManagerServer` - 19 edges
7. `ParticleRotating` - 19 edges
8. `Changelog` - 18 edges
9. `RenderHelper` - 14 edges
10. `CurseForge — Variables del proyecto` - 14 edges

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

## Communities (204 total, 164 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.05
Nodes (17): Lerpables, PlayerStatus, Particle, ParticleEngine, CallbackInfo, CompoundTag, GuiGraphicsExtractor, HumanoidRenderState (+9 more)

### Community 1 - "Community 1"
Cohesion: 0.07
Nodes (5): AbstractContainerMenu, FakePlayerHelper, InventorySnapshot, PlayerStatusManager, PlayerStatusManagerServer

### Community 2 - "Community 2"
Cohesion: 0.11
Nodes (8): CustomPacketPayload, PacketBase, PacketNBTFromClient, PacketNBTFromServer, PlayerActivityNetworking, PlayerActivityNetworkingNeoForge, PayloadRegistrar, RegistryFriendlyByteBuf

### Community 3 - "Community 3"
Cohesion: 0.12
Nodes (15): Changelog, Claves parseables por el script genérico, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, IDs de `gameVersions` para 26.1.2, Parámetros del upload (+7 more)

### Community 5 - "Community 5"
Cohesion: 0.15
Nodes (12): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Player Activity View (NeoForge), Flujo por tarea, Idioma (+4 more)

### Community 6 - "Community 6"
Cohesion: 0.20
Nodes (4): ParticleAnimated, SpriteSetPlayer, RandomSource, SpriteSet

### Community 7 - "Community 7"
Cohesion: 0.08
Nodes (13): DeltaTracker, ClientEvents, Config, ServerSyncedConfig, ExtractPingIconInjectMixin, GuiExtractRenderStateMixin, ScreenExtractBackgroundMixin, SetupAnimInjectMixin (+5 more)

### Community 8 - "Community 8"
Cohesion: 0.05
Nodes (10): Accessor, DynamicTexture, RenderHelper, ScreenData, AbstractContainerScreenAccessorMixin, NativeImageAccessorMixin, ScreenExtractRenderStateWithTooltipMixin, Minecraft (+2 more)

### Community 9 - "Community 9"
Cohesion: 0.48
Nodes (3): EquipmentSlot, CustomArmCorrections, Vector3f

### Community 10 - "Community 10"
Cohesion: 0.24
Nodes (4): Entity, EntityRenderStateTrackerMixin, EntityRenderStateTracker, WeakHashMap

### Community 11 - "Community 11"
Cohesion: 0.04
Nodes (48): [0.0.0-beta.10] - 2026-07-26, [0.0.0-beta.11] - 2026-07-26, [0.0.0-beta.12] - 2026-07-29, [0.0.0-beta.13] - 2026-07-29, [0.0.0-beta.14] - 2026-07-29, [0.0.0-beta.15] - 2026-07-30, [0.0.0-beta.16] - 2026-07-30, [0.0.0-beta.17] - 2026-07-30 (+40 more)

### Community 12 - "Community 12"
Cohesion: 0.07
Nodes (31): canPreventIdleInGui(), get(), isPointingGui(), isSoundMakerGui(), isTypingGui(), PlayerChatState, CHAT_FOCUSED, CHAT_TYPING (+23 more)

### Community 13 - "Community 13"
Cohesion: 0.14
Nodes (3): PlayerActivityClient, SpriteInfo, ModParticles

### Community 14 - "Community 14"
Cohesion: 0.12
Nodes (4): ScreenParticleRenderer, ServerConfigSyncHelper, PostChainResizeMixin, SuppressWarnings

### Community 15 - "Community 15"
Cohesion: 0.18
Nodes (3): ParticleRotating, Layer, SingleQuadParticle

### Community 16 - "Community 16"
Cohesion: 0.48
Nodes (3): Camera, QuadParticleRenderState, Quaternionf

### Community 17 - "Community 17"
Cohesion: 0.29
Nodes (6): Building from source, Commands, Credits, Features, Player Activity View, Requirements

### Community 19 - "Community 19"
Cohesion: 0.50
Nodes (3): CLAUDE.md — player_activity_view (26.1.2), Prioridad de instrucciones, Workflow del mod

### Community 20 - "Community 20"
Cohesion: 0.50
Nodes (3): 0.0.0-beta.2 — Rebranding & Package Update, Changes, Technical

### Community 22 - "Community 22"
Cohesion: 0.36
Nodes (4): DynamicScreenRenderer, Pose, SubmitCustomGeometryEvent, VertexConsumer

### Community 24 - "Community 24"
Cohesion: 0.07
Nodes (15): BlockHitResult, BlockState, CommandDispatcher, CommandSourceStack, ContainerInput, FMLCommonSetupEvent, PlayerActivity, CommandReloadConfig (+7 more)

### Community 29 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

## Knowledge Gaps
- **98 isolated node(s):** `NONE`, `CHAT_SCREEN`, `INVENTORY`, `CRAFTING`, `ESCAPE` (+93 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **164 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PlayerStatusManagerClient` connect `Community 0` to `Community 1`, `Community 7`, `Community 14`, `Community 24`, `ShaderInstanceBlur`?**
  _High betweenness centrality (0.125) - this node is a cross-community bridge._
- **Why does `PlayerStatus` connect `Community 0` to `Community 1`, `Community 12`, `Community 14`, `Community 22`, `ModelPartData`?**
  _High betweenness centrality (0.089) - this node is a cross-community bridge._
- **Why does `PlayerActivity` connect `Community 24` to `Community 0`, `Community 1`, `Community 7`?**
  _High betweenness centrality (0.062) - this node is a cross-community bridge._
- **What connects `NONE`, `CHAT_SCREEN`, `INVENTORY` to the rest of the system?**
  _98 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.05414141414141414 - nodes in this community are weakly interconnected._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.07399577167019028 - nodes in this community are weakly interconnected._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.1053763440860215 - nodes in this community are weakly interconnected._