# Graph Report - .  (2026-08-02)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 690 nodes · 1329 edges · 45 communities (22 shown, 23 thin omitted)
- Extraction: 91% EXTRACTED · 9% INFERRED · 0% AMBIGUOUS · INFERRED: 124 edges (avg confidence: 0.8)
- Token cost: 1,508 input · 481 output

## Graph Freshness
- Built from commit: `03161399`
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

## God Nodes (most connected - your core abstractions)
1. `PlayerStatus` - 77 edges
2. `PlayerStatusManagerClient` - 46 edges
3. `ScreenData` - 37 edges
4. `PlayerGuiState` - 33 edges
5. `PlayerStatusManagerServer` - 20 edges
6. `ParticleRotating` - 19 edges
7. `Version 0.0.0-beta.1 Changelog` - 19 edges
8. `PlayerStatusClient` - 18 edges
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

## Communities (45 total, 23 thin omitted)

### Community 0 - "Rendering Utilities"
Cohesion: 0.05
Nodes (21): Accessor, DynamicTexture, Minecraft, NativeImage, ScreenRectangle, ByteBuffer, Logger, RenderHelper (+13 more)

### Community 1 - "Client Event Handlers"
Cohesion: 0.06
Nodes (37): ClientModInitializer, KeyEvent, MouseButtonInfo, ClientEvents, ExtractPingIconInjectMixin, CallbackInfo, GuiGraphicsExtractor, Inject (+29 more)

### Community 2 - "Particle Effects"
Cohesion: 0.06
Nodes (26): Camera, Layer, Particle, QuadParticleRenderState, Quaternionf, SingleQuadParticle, ClientLevel, Override (+18 more)

### Community 3 - "Server Configuration Sync"
Cohesion: 0.07
Nodes (18): AbstractContainerMenu, CompoundTag, SuppressWarnings, ServerConfigSyncHelper, FakePlayerHelper, Player, InventorySnapshot, ItemStack (+10 more)

### Community 4 - "Player Activity Management"
Cohesion: 0.07
Nodes (30): BlockHitResult, BlockState, ContainerInput, InteractionResult, MinecraftServer, ModInitializer, PlayerList, GameRendererPreloadUiShaderMixin (+22 more)

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
Nodes (18): CommandDispatcher, CommandSourceStack, EquipmentSlot, FabricClientCommandSource, ParticleEngine, CallbackInfo, HumanoidRenderState, Level (+10 more)

### Community 9 - "Player Status Tracking"
Cohesion: 0.10
Nodes (3): ModelPartData, CompoundTag, PlayerStatus

### Community 10 - "Custom Packet Payloads"
Cohesion: 0.13
Nodes (15): CustomPacketPayload, PacketBase, CompoundTag, CustomPacketPayload, FriendlyByteBuf, Override, Player, StreamCodec (+7 more)

### Community 11 - "Status Management"
Cohesion: 0.14
Nodes (3): CompoundTag, Override, Pair

### Community 12 - "Entity Rendering"
Cohesion: 0.14
Nodes (15): Entity, HumanoidModel, EntityRenderStateTrackerMixin, CallbackInfoReturnable, EntityRenderState, Inject, Mixin, CallbackInfo (+7 more)

### Community 13 - "Client Player Status"
Cohesion: 0.17
Nodes (7): LevelRenderContext, Pose, Particle, PlayerStatusClient, DynamicScreenRenderer, Logger, VertexConsumer

### Community 14 - "Server-Sent NBT Packets"
Cohesion: 0.19
Nodes (13): CompoundTag, CustomPacketPayload, FriendlyByteBuf, Override, StreamCodec, Type, PacketNBTFromServer, CompoundTag (+5 more)

### Community 15 - "Particle Textures"
Cohesion: 0.10
Nodes (20): Project Description, Chat Typing 0 Particle Texture, Chat Typing 1 Particle Texture, Chat Typing 2 Particle Texture, Chat Typing 3 Particle Texture, Chat Typing 4 Particle Texture, Chat Typing 5 Particle Texture, Chest 0 Particle Texture (+12 more)

### Community 16 - "Client GUI Rendering"
Cohesion: 0.17
Nodes (4): GuiGraphicsExtractor, Logger, Player, PlayerStatusManagerClient

### Community 17 - "Player Status Manager"
Cohesion: 0.20
Nodes (3): Player, Vec3, PlayerStatusManager

### Community 19 - "Screen Particle Renderer"
Cohesion: 0.24
Nodes (5): ScreenParticleRenderer, CallbackInfo, Inject, Mixin, PostChainResizeMixin

### Community 22 - "Build Tools"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

## Knowledge Gaps
- **63 isolated node(s):** `NONE`, `CHAT_SCREEN`, `INVENTORY`, `CRAFTING`, `ESCAPE` (+58 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **23 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `PlayerStatus` connect `Player Status Tracking` to `Rendering Utilities`, `Server Configuration Sync`, `Player GUI State`, `Status Management`, `Client Player Status`, `Client GUI Rendering`, `Player Status Manager`, `Animation Handling`?**
  _High betweenness centrality (0.208) - this node is a cross-community bridge._
- **Why does `PlayerStatusManagerClient` connect `Client GUI Rendering` to `Client Event Handlers`, `Command Execution`, `Player Status Tracking`, `Status Management`, `Client Player Status`, `Player Status Manager`, `Animation Handling`, `Shader Blurring`?**
  _High betweenness centrality (0.155) - this node is a cross-community bridge._
- **Why does `ParticleRotating` connect `Particle Effects` to `Command Execution`, `Sprite Set Handling`?**
  _High betweenness centrality (0.069) - this node is a cross-community bridge._
- **What connects `NONE`, `CHAT_SCREEN`, `INVENTORY` to the rest of the system?**
  _63 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Rendering Utilities` be split into smaller, more focused modules?**
  _Cohesion score 0.05150905432595573 - nodes in this community are weakly interconnected._
- **Should `Client Event Handlers` be split into smaller, more focused modules?**
  _Cohesion score 0.055523085914669784 - nodes in this community are weakly interconnected._
- **Should `Particle Effects` be split into smaller, more focused modules?**
  _Cohesion score 0.0593990216631726 - nodes in this community are weakly interconnected._