# Graph Report - 26.1.2  (2026-07-30)

## Corpus Check
- 87 files · ~71,442 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 744 nodes · 1036 edges · 184 communities (36 shown, 148 thin omitted)
- Extraction: 87% EXTRACTED · 13% INFERRED · 0% AMBIGUOUS · INFERRED: 137 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `dc357aa9`
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
- 0.0.0-beta.3.md
- 0.0.0-beta.4.md
- 0.0.0-beta.5.md
- 0.0.0-beta.6.md
- 0.0.0-beta.7.md
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

## God Nodes (most connected - your core abstractions)
1. `PlayerStatus` - 80 edges
2. `PlayerStatusManagerClient` - 43 edges
3. `ScreenData` - 34 edges
4. `PlayerGuiState` - 32 edges
5. `PlayerActivity` - 22 edges
6. `PlayerStatusManagerServer` - 19 edges
7. `ParticleRotating` - 19 edges
8. `Flujo de trabajo — Player Activity View (NeoForge)` - 14 edges
9. `RenderHelper` - 13 edges
10. `Changelog` - 13 edges

## Surprising Connections (you probably didn't know these)
- `PlayerStatus` --references--> `InventorySnapshot`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerStatus.java → src/main/java/com/skd/playeractivityview/InventorySnapshot.java
- `PlayerActivity` --references--> `PlayerStatusManagerServer`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerActivity.java → src/main/java/com/skd/playeractivityview/PlayerStatusManagerServer.java
- `PlayerActivity` --references--> `PlayerStatusManagerClient`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerActivity.java → src/main/java/com/skd/playeractivityview/PlayerStatusManagerClient.java
- `PlayerStatus` --references--> `Lerpables`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerStatus.java → src/main/java/com/skd/playeractivityview/math/Lerpables.java
- `PlayerStatusManagerClient` --references--> `PlayerStatus`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerStatusManagerClient.java → src/main/java/com/skd/playeractivityview/PlayerStatus.java

## Import Cycles
- None detected.

## Communities (184 total, 148 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.13
Nodes (10): ParticleEngine, CallbackInfo, GuiGraphicsExtractor, Level, Logger, Player, PlayerInfo, Screen (+2 more)

### Community 1 - "Community 1"
Cohesion: 0.07
Nodes (6): AbstractContainerMenu, ServerConfigSyncHelper, InventorySnapshot, PlayerStatusManager, PlayerStatusManagerServer, SuppressWarnings

### Community 2 - "Community 2"
Cohesion: 0.11
Nodes (8): CustomPacketPayload, PacketBase, PacketNBTFromClient, PacketNBTFromServer, PlayerActivityNetworking, PlayerActivityNetworkingNeoForge, PayloadRegistrar, RegistryFriendlyByteBuf

### Community 3 - "Community 3"
Cohesion: 0.23
Nodes (3): RegisterPayloadHandlersEvent, ResourceProvider, PlayerActivity

### Community 4 - "Community 4"
Cohesion: 0.08
Nodes (11): Camera, ParticleDynamic, ParticleStatic, ParticleStaticPartial, Layer, QuadParticleRenderState, Quaternionf, SingleQuadParticle (+3 more)

### Community 5 - "Community 5"
Cohesion: 0.05
Nodes (40): 1. Desarrollo, 2. Copiar a instancia de pruebas, 3. Probar en instancia, 4. Preparar versión para CurseForge, 5. Release estable, 6. Actualizar Knowledge Graph (Graphify), Archivos de CurseForge, Archivos que pasan a GitHub (+32 more)

### Community 6 - "Community 6"
Cohesion: 0.07
Nodes (13): ModParticles, ParticleAnimated, ParticleStaticLoD, SpriteSetPlayer, RandomSource, SpriteSet, Mod, ModContainer (+5 more)

### Community 7 - "Community 7"
Cohesion: 0.21
Nodes (7): ServerSyncedConfig, Key, RegisterClientCommandsEvent, RegisterParticleProvidersEvent, ClientEvents, Post, SubscribeEvent

### Community 8 - "Community 8"
Cohesion: 0.06
Nodes (13): DynamicTexture, NativeImageAccessorMixin, ScreenExtractRenderStateWithTooltipMixin, Minecraft, NativeImage, ScreenRectangle, ByteBuffer, Logger (+5 more)

### Community 9 - "Community 9"
Cohesion: 0.23
Nodes (6): CommandDispatcher, CommandSourceStack, EquipmentSlot, CommandReloadConfig, CustomArmCorrections, Vector3f

### Community 10 - "Community 10"
Cohesion: 0.24
Nodes (4): Entity, EntityRenderStateTrackerMixin, EntityRenderStateTracker, WeakHashMap

### Community 11 - "Community 11"
Cohesion: 0.05
Nodes (36): [0.0.0-beta.10] - 2026-07-26, [0.0.0-beta.11] - 2026-07-26, [0.0.0-beta.12] - 2026-07-29, [0.0.0-beta.13] - 2026-07-29, [0.0.0-beta.14] - 2026-07-29, [0.0.0-beta.15] - 2026-07-30, [0.0.0-beta.16] - 2026-07-30, [0.0.0-beta.17] - 2026-07-30 (+28 more)

### Community 12 - "Community 12"
Cohesion: 0.08
Nodes (27): canPreventIdleInGui(), get(), isPointingGui(), isSoundMakerGui(), isTypingGui(), PlayerGuiState, ANVIL, BEACON (+19 more)

### Community 15 - "Community 15"
Cohesion: 0.25
Nodes (4): ContainerInput, AbstractContainerMenuDoClickMixin, PlayerLoggedInEvent, Post

### Community 17 - "Community 17"
Cohesion: 0.13
Nodes (6): Lerpables, PlayerChatState, CHAT_FOCUSED, CHAT_TYPING, NONE, HumanoidRenderState

### Community 20 - "Community 20"
Cohesion: 0.13
Nodes (8): FMLCommonSetupEvent, GameRendererPreloadUiShaderMixin, ScreenExtractBackgroundMixin, PlayerList, Identifier, Logger, Mod, ModContainer

### Community 21 - "Community 21"
Cohesion: 0.14
Nodes (13): Changelog, CurseForge — Variables del proyecto, Descripcion del proyecto, Estructura del changelog (HTML), Flujo completo, Parámetros del upload, Proyecto, Rama (+5 more)

### Community 22 - "Community 22"
Cohesion: 0.33
Nodes (5): Pose, DynamicScreenRenderer, Logger, SubmitCustomGeometryEvent, VertexConsumer

### Community 24 - "Community 24"
Cohesion: 0.53
Nodes (4): BlockHitResult, BlockState, BlockBehaviorUseMixin, InteractionResult

### Community 27 - "CLAUDE.md — player_activity_view (26.1.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — player_activity_view (26.1.2), Paso 0 obligatorio, Prioridad de instrucciones

### Community 28 - "0.0.0-beta.2 — Rebranding & Package Update"
Cohesion: 0.50
Nodes (3): 0.0.0-beta.2 — Rebranding & Package Update, Changes, Technical

### Community 29 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

## Knowledge Gaps
- **107 isolated node(s):** `NONE`, `CHAT_SCREEN`, `INVENTORY`, `CRAFTING`, `ESCAPE` (+102 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **148 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PlayerStatus` connect `Community 14` to `Community 0`, `Community 1`, `Community 8`, `Community 12`, `Community 17`, `Community 18`, `Community 19`, `Community 22`, `Community 25`, `ModelPartData`?**
  _High betweenness centrality (0.128) - this node is a cross-community bridge._
- **Why does `PlayerStatusManagerClient` connect `Community 0` to `Community 1`, `Community 3`, `Community 7`, `Community 14`, `Community 17`, `Community 18`, `Community 19`, `ShaderInstanceBlur`?**
  _High betweenness centrality (0.096) - this node is a cross-community bridge._
- **Why does `ParticleRotating` connect `Community 4` to `Community 0`, `Community 6`?**
  _High betweenness centrality (0.067) - this node is a cross-community bridge._
- **What connects `NONE`, `CHAT_SCREEN`, `INVENTORY` to the rest of the system?**
  _107 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.12535612535612536 - nodes in this community are weakly interconnected._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.0696969696969697 - nodes in this community are weakly interconnected._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.1053763440860215 - nodes in this community are weakly interconnected._