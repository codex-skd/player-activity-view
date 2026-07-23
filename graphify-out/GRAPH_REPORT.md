# Graph Report - .  (2026-07-23)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 618 nodes · 1205 edges · 26 communities (23 shown, 3 thin omitted)
- Extraction: 90% EXTRACTED · 10% INFERRED · 0% AMBIGUOUS · INFERRED: 125 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `f5384cc8`
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

## God Nodes (most connected - your core abstractions)
1. `PlayerStatus` - 83 edges
2. `PlayerStatusManagerClient` - 43 edges
3. `ScreenData` - 34 edges
4. `PlayerGuiState` - 32 edges
5. `PlayerActivity` - 22 edges
6. `PlayerStatusManagerServer` - 20 edges
7. `ParticleRotating` - 19 edges
8. `SpriteSetPlayer` - 16 edges
9. `ParticleEngineCustom` - 14 edges
10. `PlayerActivityNetworking` - 13 edges

## Surprising Connections (you probably didn't know these)
- `PlayerStatus` --references--> `InventorySnapshot`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerStatus.java → src/main/java/com/skd/playeractivityview/InventorySnapshot.java
- `PlayerActivity` --references--> `PlayerStatusManagerClient`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerActivity.java → src/main/java/com/skd/playeractivityview/PlayerStatusManagerClient.java
- `PlayerActivity` --references--> `PlayerStatusManagerServer`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerActivity.java → src/main/java/com/skd/playeractivityview/PlayerStatusManagerServer.java
- `PlayerStatus` --references--> `ScreenData`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerStatus.java → src/main/java/com/skd/playeractivityview/client/screen/ScreenData.java
- `PlayerStatusManager` --references--> `PlayerStatus`  [EXTRACTED]
  src/main/java/com/skd/playeractivityview/PlayerStatusManager.java → src/main/java/com/skd/playeractivityview/PlayerStatus.java

## Import Cycles
- None detected.

## Communities (26 total, 3 thin omitted)

### Community 0 - "Community 0"
Cohesion: 0.06
Nodes (11): Lerpables, ModelPartData, Particle, PlayerStatus, CompoundTag, GuiGraphicsExtractor, Override, Pair (+3 more)

### Community 1 - "Community 1"
Cohesion: 0.07
Nodes (18): AbstractContainerMenu, FakePlayerHelper, Player, InventorySnapshot, ItemStack, BlockPos, Player, Vec3 (+10 more)

### Community 2 - "Community 2"
Cohesion: 0.07
Nodes (33): CustomPacketPayload, PayloadRegistrar, RegistryFriendlyByteBuf, PacketBase, CompoundTag, CustomPacketPayload, FriendlyByteBuf, Override (+25 more)

### Community 3 - "Community 3"
Cohesion: 0.06
Nodes (33): BlockHitResult, BlockState, FMLCommonSetupEvent, Identifier, InteractionResult, PlayerList, PlayerLoggedInEvent, RegisterPayloadHandlersEvent (+25 more)

### Community 4 - "Community 4"
Cohesion: 0.06
Nodes (26): Layer, Particle, QuadParticleRenderState, Quaternionf, SingleQuadParticle, ClientLevel, Override, ParticleRenderType (+18 more)

### Community 5 - "Community 5"
Cohesion: 0.07
Nodes (32): canPreventIdleInGui(), get(), isPointingGui(), isSoundMakerGui(), isTypingGui(), CompoundTag, PlayerChatState, CHAT_FOCUSED (+24 more)

### Community 6 - "Community 6"
Cohesion: 0.10
Nodes (15): RandomSource, SpriteSet, TextureAtlas, ModParticles, ClientLevel, Override, ParticleAnimated, ClientLevel (+7 more)

### Community 7 - "Community 7"
Cohesion: 0.10
Nodes (18): CommandDispatcher, CommandSourceStack, Key, RegisterClientCommandsEvent, RegisterParticleProvidersEvent, CommandReloadConfig, ClientEvents, Post (+10 more)

### Community 8 - "Community 8"
Cohesion: 0.09
Nodes (5): DynamicTexture, ByteBuffer, ParticleRenderType, Screen, ScreenData

### Community 9 - "Community 9"
Cohesion: 0.10
Nodes (19): EquipmentSlot, Adjustment, ConfigClient, BooleanValue, Builder, ConfigValue, DoubleValue, IntValue (+11 more)

### Community 10 - "Community 10"
Cohesion: 0.14
Nodes (15): Entity, HumanoidModel, EntityRenderStateTrackerMixin, CallbackInfoReturnable, EntityRenderState, Inject, Mixin, CallbackInfo (+7 more)

### Community 11 - "Community 11"
Cohesion: 0.13
Nodes (13): ByteBuffer, RenderHelper, BooleanValue, Builder, DoubleValue, IntValue, ModConfigSpec, ServerSyncedConfig (+5 more)

### Community 12 - "Community 12"
Cohesion: 0.20
Nodes (9): PreparableReloadListener, PreparationBarrier, SharedState, Camera, ClientLevel, Override, Particle, ParticleEngineCustom (+1 more)

### Community 13 - "Community 13"
Cohesion: 0.25
Nodes (5): ScreenParticleRenderer, CallbackInfo, Inject, Mixin, PostChainResizeMixin

### Community 14 - "Community 14"
Cohesion: 0.33
Nodes (3): CompoundTag, ServerConfigSyncHelper, SuppressWarnings

### Community 15 - "Community 15"
Cohesion: 0.43
Nodes (6): ContainerInput, AbstractContainerMenuDoClickMixin, CallbackInfo, Inject, Mixin, Player

### Community 16 - "Community 16"
Cohesion: 0.43
Nodes (6): DeltaTracker, GuiExtractRenderStateMixin, CallbackInfo, GuiGraphicsExtractor, Inject, Mixin

### Community 17 - "Community 17"
Cohesion: 0.43
Nodes (6): ExtractPingIconInjectMixin, CallbackInfo, GuiGraphicsExtractor, Inject, Mixin, PlayerInfo

### Community 19 - "Community 19"
Cohesion: 0.43
Nodes (4): Mod, ModContainer, Player, PlayerActivityClient

### Community 20 - "Community 20"
Cohesion: 0.40
Nodes (5): ConfigCommon, BooleanValue, Builder, IntValue, ModConfigSpec

### Community 22 - "Community 22"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

## Knowledge Gaps
- **24 isolated node(s):** `NONE`, `CHAT_SCREEN`, `INVENTORY`, `CRAFTING`, `ESCAPE` (+19 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **3 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PlayerStatusManagerClient` connect `Community 0` to `Community 1`, `Community 3`, `Community 7`, `Community 9`, `Community 12`, `Community 21`?**
  _High betweenness centrality (0.257) - this node is a cross-community bridge._
- **Why does `PlayerStatus` connect `Community 0` to `Community 8`, `Community 1`, `Community 5`?**
  _High betweenness centrality (0.225) - this node is a cross-community bridge._
- **Why does `PlayerActivity` connect `Community 3` to `Community 0`, `Community 1`, `Community 7`?**
  _High betweenness centrality (0.122) - this node is a cross-community bridge._
- **What connects `NONE`, `CHAT_SCREEN`, `INVENTORY` to the rest of the system?**
  _24 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Community 0` be split into smaller, more focused modules?**
  _Cohesion score 0.0554524840239126 - nodes in this community are weakly interconnected._
- **Should `Community 1` be split into smaller, more focused modules?**
  _Cohesion score 0.07080200501253132 - nodes in this community are weakly interconnected._
- **Should `Community 2` be split into smaller, more focused modules?**
  _Cohesion score 0.07272727272727272 - nodes in this community are weakly interconnected._