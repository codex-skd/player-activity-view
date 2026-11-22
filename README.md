# Player Activity View

See what other players are doing at a glance — typing, idling, browsing menus, crafting, and more — shown live on their in-game model.

This mod is a fork of WATUT (What Are They Up To) by Corosus. This is a complete ground-up rewrite and modernization for current NeoForge versions, with no dependency on CoroUtil.

## Features

- **Typing indicators** — see when a player is typing in chat, editing a book, or writing on a sign, with animated particles above their head and an on-screen "is typing..." message.
- **GUI visualizer** — shows exactly what interface a player is using: inventory, chest, crafting table, furnace, enchanting table, anvil, beacon, brewing stand, dispenser, grindstone, hopper, horse, loom, villager trading, command blocks, and more.
- **Live screen mirror** — a preview of the player's open menu is captured, cropped and shown as a small tilted panel held in front of them, visible to nearby players.
- **Idle detection** — automatically detects when players go idle with a configurable timeout. Shows an idle icon above their head and in the tab player list.
- **Inventory animations** — animated item particles fly between players and containers when items are transferred.
- **Arm animations** — players using GUIs show arm movements (pointing, clicking, typing), bringing life to otherwise static third-person views.
- **Privacy controls** — every feature can be toggled client-side or server-side.
- **Server-synced config** — server owners can control what information is broadcast to all players through a server-side config that syncs to every client.
- **12 languages** — English, Spanish (Spain and Mexico), Portuguese (Brazil), French, German, Italian, Russian, Polish, Japanese, Korean, Simplified Chinese and Traditional Chinese. On NeoForge the labels are shown in the in-game configuration screen; the Forge and Fabric builds have no configuration screen (edit the config files instead).

## Requirements

| | |
|---|---|
| Minecraft | 26.1.2 |
| NeoForge | 26.1.2.78+ |

## Available builds

| Minecraft | Loader | Version |
|---|---|---|
| 1.21.1 | NeoForge 21.1.259+ | 1.0.4 |
| 1.21.1 | Forge 52.1.16+ | 0.0.0-beta.1 |
| 1.21.1 | Fabric Loader 0.19.5+ / Fabric API | 0.0.0-beta.1 |
| 26.1.2 | NeoForge 26.1.2.78+ | 1.0.5 |
| 26.1.2 | Forge 64.1.4+ | 0.0.0-beta.1 |
| 26.1.2 | Fabric Loader 0.19.5+ / Fabric API | 0.0.0-beta.5 |
| 26.2 | NeoForge 26.2.0.57+ | 1.1.3 |
| 26.2 | Forge 65.1.4+ | 0.0.0-beta.1 |
| 26.2 | Fabric Loader 0.19.3+ / Fabric API | 1.0.5 |
| 26.3 | NeoForge 26.3.0.26-beta+ | 0.0.0-beta.1 |
| 26.3 | Forge 66.0.11+ | 0.0.0-beta.1 |
| 26.3 | Fabric Loader 0.19.5+ / Fabric API | 0.0.0-beta.1 |

All builds are published on the same CurseForge project.

## Commands

`/player_activity_view reloadJSON` — reload arm adjustment configuration from the JSON file.

## Building from source

```
./gradlew build
```

The built jar is placed in `build/libs/`.

## Credits

Originally inspired by **WATUT** (What Are They Up To) by *Corosus*.
