# Graph Report - 26.2  (2026-08-01)

## Corpus Check
- 73 files · ~67,238 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 678 nodes · 933 edges · 172 communities (22 shown, 150 thin omitted)
- Extraction: 85% EXTRACTED · 15% INFERRED · 0% AMBIGUOUS · INFERRED: 141 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `a41e4767`
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
- `PlayerStatusManagerClient` --references--> `PlayerStatus`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerStatusManagerClient.java → src/main/java/com/skd/playeractivityview/PlayerStatus.java
- `PlayerStatusManagerClient` --inherits--> `PlayerStatusManager`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerStatusManagerClient.java → src/main/java/com/skd/playeractivityview/PlayerStatusManager.java

## Import Cycles
- None detected.

## Communities (172 total, 150 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.07
Nodes (16): EquipmentSlot, CustomArmCorrections, ParticleEngine, CallbackInfo, CompoundTag, GuiGraphicsExtractor, Level, Logger (+8 more)

### Community 1 - "Community 1"
Cohesion: 0.07
Nodes (6): AbstractContainerMenu, ServerConfigSyncHelper, InventorySnapshot, PlayerStatusManager, PlayerStatusManagerServer, SuppressWarnings

### Community 2 - "Community 2"
Cohesion: 0.11
Nodes (8): CustomPacketPayload, PacketBase, PacketNBTFromClient, PacketNBTFromServer, PlayerActivityNetworking, PlayerActivityNetworkingNeoForge, PayloadRegistrar, RegistryFriendlyByteBuf

### Community 3 - "Community 3"
Cohesion: 0.15
Nodes (12): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Player Activity View (NeoForge), Flujo por tarea, Idioma (+4 more)

### Community 4 - "Community 4"
Cohesion: 0.05
Nodes (14): Camera, ParticleRotating, ParticleAnimated, ParticleDynamic, ParticleStatic, ParticleStaticLoD, ParticleStaticPartial, SpriteSetPlayer (+6 more)

### Community 5 - "Community 5"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 6 - "Community 6"
Cohesion: 0.14
Nodes (3): PlayerActivityClient, SpriteInfo, ModParticles

### Community 7 - "Community 7"
Cohesion: 0.07
Nodes (16): CommandDispatcher, CommandSourceStack, DeltaTracker, ClientEvents, CommandReloadConfig, Config, ServerSyncedConfig, ExtractPingIconInjectMixin (+8 more)

### Community 8 - "Community 8"
Cohesion: 0.06
Nodes (13): Accessor, DynamicTexture, ScreenData, NativeImageAccessorMixin, ScreenExtractRenderStateWithTooltipMixin, Minecraft, NativeImage, ScreenRectangle (+5 more)

### Community 10 - "Community 10"
Cohesion: 0.24
Nodes (4): Entity, EntityRenderStateTrackerMixin, EntityRenderStateTracker, WeakHashMap

### Community 11 - "Community 11"
Cohesion: 0.50
Nodes (3): [0.0.0-beta.1] - 2026-08-01, Changelog, Port

### Community 12 - "Community 12"
Cohesion: 0.08
Nodes (27): canPreventIdleInGui(), get(), isPointingGui(), isSoundMakerGui(), isTypingGui(), PlayerGuiState, ANVIL, BEACON (+19 more)

### Community 21 - "Community 21"
Cohesion: 0.12
Nodes (15): Changelog, Claves parseables por el script genérico, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, IDs de `gameVersions` para 26.2, Parámetros del upload (+7 more)

### Community 22 - "Community 22"
Cohesion: 0.14
Nodes (7): ScreenParticleRenderer, PostChainResizeMixin, Pose, DynamicScreenRenderer, Logger, SubmitCustomGeometryEvent, VertexConsumer

### Community 24 - "Community 24"
Cohesion: 0.08
Nodes (12): BlockHitResult, BlockState, ContainerInput, FMLCommonSetupEvent, PlayerActivity, AbstractContainerMenuDoClickMixin, BlockBehaviorUseMixin, GameRendererPreloadUiShaderMixin (+4 more)

### Community 27 - "CLAUDE.md — player_activity_view (26.1.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — player_activity_view (26.2), Prioridad de instrucciones, Workflow del mod

### Community 30 - "ModelPartData"
Cohesion: 0.05
Nodes (11): FakePlayerHelper, Lerpables, ModelPartData, ParticleItem, PlayerChatState, CHAT_FOCUSED, CHAT_TYPING, NONE (+3 more)

### Community 183 - "README.md"
Cohesion: 0.29
Nodes (6): Building from source, Commands, Credits, Features, Player Activity View, Requirements

## Knowledge Gaps
- **62 isolated node(s):** `NONE`, `CHAT_SCREEN`, `INVENTORY`, `CRAFTING`, `ESCAPE` (+57 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **150 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PlayerStatus` connect `ModelPartData` to `Community 0`, `Community 1`, `Community 8`, `Community 12`, `Community 22`?**
  _High betweenness centrality (0.152) - this node is a cross-community bridge._
- **Why does `PlayerStatusManagerClient` connect `Community 0` to `Community 1`, `Community 7`, `Community 22`, `Community 24`, `ShaderInstanceBlur`, `ModelPartData`?**
  _High betweenness centrality (0.129) - this node is a cross-community bridge._
- **Why does `PlayerActivity` connect `Community 24` to `Community 0`, `Community 1`, `Community 7`?**
  _High betweenness centrality (0.071) - this node is a cross-community bridge._
- **What connects `NONE`, `CHAT_SCREEN`, `INVENTORY` to the rest of the system?**
  _62 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.07486338797814207 - nodes in this community are weakly interconnected._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.07053140096618357 - nodes in this community are weakly interconnected._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.1053763440860215 - nodes in this community are weakly interconnected._