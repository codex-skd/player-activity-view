# Graph Report - 26.2  (2026-08-02)

## Corpus Check
- 76 files · ~67,848 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 687 nodes · 918 edges · 184 communities (25 shown, 159 thin omitted)
- Extraction: 85% EXTRACTED · 15% INFERRED · 0% AMBIGUOUS · INFERRED: 141 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `5cd421f1`
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
- ModelPartData
- 0.0.0-beta.2.md
- Logger
- Mixin
- CallbackInfo
- GuiGraphicsExtractor
- Inject
- Mixin
- Logger
- .PlayerActivity
- Particle
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
- ServerConfigSyncHelper
- ShaderInstanceBlur
- PostChainResizeMixin
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

## God Nodes (most connected - your core abstractions)
1. `PlayerStatus` - 75 edges
2. `PlayerStatusManagerClient` - 43 edges
3. `PlayerGuiState` - 32 edges
4. `ScreenData` - 28 edges
5. `PlayerActivity` - 19 edges
6. `PlayerStatusManagerServer` - 19 edges
7. `ParticleRotating` - 19 edges
8. `RenderHelper` - 14 edges
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

## Communities (184 total, 159 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.24
Nodes (4): Entity, EntityRenderStateTrackerMixin, EntityRenderStateTracker, WeakHashMap

### Community 1 - "Community 1"
Cohesion: 0.07
Nodes (5): AbstractContainerMenu, FakePlayerHelper, InventorySnapshot, PlayerStatusManager, PlayerStatusManagerServer

### Community 2 - "Community 2"
Cohesion: 0.11
Nodes (8): CustomPacketPayload, PacketBase, PacketNBTFromClient, PacketNBTFromServer, PlayerActivityNetworking, PlayerActivityNetworkingNeoForge, PayloadRegistrar, RegistryFriendlyByteBuf

### Community 3 - "Community 3"
Cohesion: 0.15
Nodes (12): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Player Activity View (NeoForge), Flujo por tarea, Idioma (+4 more)

### Community 4 - "Community 4"
Cohesion: 0.07
Nodes (13): Camera, ParticleRotating, DynamicScreenRenderer, ParticleDynamic, ParticleStatic, ParticleStaticPartial, Layer, Pose (+5 more)

### Community 5 - "Community 5"
Cohesion: 0.12
Nodes (15): Changelog, Claves parseables por el script genérico, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, IDs de `gameVersions` para 26.2, Parámetros del upload (+7 more)

### Community 6 - "Community 6"
Cohesion: 0.15
Nodes (3): PlayerActivityClient, SpriteInfo, ModParticles

### Community 7 - "Community 7"
Cohesion: 0.08
Nodes (13): DeltaTracker, ClientEvents, GuiExtractRenderStateMixin, Config, ServerSyncedConfig, ExtractPingIconInjectMixin, ScreenExtractBackgroundMixin, SetupAnimInjectMixin (+5 more)

### Community 8 - "Community 8"
Cohesion: 0.05
Nodes (10): Accessor, DynamicTexture, ScreenData, RenderHelper, AbstractContainerScreenAccessorMixin, NativeImageAccessorMixin, ScreenExtractRenderStateWithTooltipMixin, Minecraft (+2 more)

### Community 9 - "Community 9"
Cohesion: 0.14
Nodes (5): ParticleAnimated, ParticleStaticLoD, SpriteSetPlayer, RandomSource, SpriteSet

### Community 10 - "Community 10"
Cohesion: 0.29
Nodes (6): Building from source, Commands, Credits, Features, Player Activity View, Requirements

### Community 11 - "Community 11"
Cohesion: 0.20
Nodes (9): [0.0.0-beta.1] - 2026-08-01, [0.0.0-beta.2] - 2026-08-01, [0.0.0-beta.3] - 2026-08-02, [1.0.0] - 2026-08-02, Changelog, Fix, Fix, Port (+1 more)

### Community 12 - "Community 12"
Cohesion: 0.07
Nodes (31): canPreventIdleInGui(), get(), isPointingGui(), isSoundMakerGui(), isTypingGui(), PlayerChatState, CHAT_FOCUSED, CHAT_TYPING (+23 more)

### Community 13 - "Community 13"
Cohesion: 0.50
Nodes (3): CLAUDE.md — player_activity_view (26.2), Prioridad de instrucciones, Workflow del mod

### Community 21 - "Community 21"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 24 - "Community 24"
Cohesion: 0.08
Nodes (12): BlockHitResult, BlockState, ContainerInput, FMLCommonSetupEvent, PlayerActivity, AbstractContainerMenuDoClickMixin, BlockBehaviorUseMixin, GameRendererPreloadUiShaderMixin (+4 more)

### Community 30 - "ModelPartData"
Cohesion: 0.05
Nodes (16): Lerpables, PlayerStatus, ParticleEngine, CallbackInfo, CompoundTag, GuiGraphicsExtractor, HumanoidRenderState, Level (+8 more)

### Community 55 - ".PlayerActivity"
Cohesion: 0.21
Nodes (6): CommandDispatcher, CommandSourceStack, EquipmentSlot, CommandReloadConfig, CustomArmCorrections, Vector3f

## Knowledge Gaps
- **65 isolated node(s):** `NONE`, `CHAT_SCREEN`, `INVENTORY`, `CRAFTING`, `ESCAPE` (+60 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **159 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PlayerStatusManagerClient` connect `ModelPartData` to `Community 24`, `Community 1`, `ShaderInstanceBlur`, `Community 7`?**
  _High betweenness centrality (0.150) - this node is a cross-community bridge._
- **Why does `PlayerStatus` connect `ModelPartData` to `Particle`, `Community 1`, `Community 12`, `ModelPartData`?**
  _High betweenness centrality (0.106) - this node is a cross-community bridge._
- **Why does `PlayerActivity` connect `Community 24` to `Community 1`, `.PlayerActivity`, `ModelPartData`, `Community 7`?**
  _High betweenness centrality (0.074) - this node is a cross-community bridge._
- **What connects `NONE`, `CHAT_SCREEN`, `INVENTORY` to the rest of the system?**
  _65 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.07308970099667775 - nodes in this community are weakly interconnected._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.1053763440860215 - nodes in this community are weakly interconnected._
- **Should `Community 4` be split into smaller, more focused modules?**
  _Cohesion score 0.07051282051282051 - nodes in this community are weakly interconnected._