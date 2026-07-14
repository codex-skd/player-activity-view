# Player Activity View

**Player Activity View** lets you see what other players are doing at a glance — whether they're typing in chat, browsing their inventory, using a crafting table, or just standing around idling. No more wondering if someone is AFK or just quiet.

## Features

- **Typing indicators** — See when a player is typing in chat, editing a book, or writing on a sign, with animated particles above their head and a "is typing..." message in your chat
- **GUI visualizer** — Shows exactly what interface a player is using: inventory, chest, crafting table, furnace, enchanting table, anvil, beacon, brewing stand, dispenser, grindstone, hopper, horse, loom, villager trading, command blocks, and more
- **Idle detection** — Automatically detects when players go idle (configurable timeout, default 5 minutes) and shows an idle icon above their head and in the tab player list
- **Inventory animations** — Watches items being transferred between players and containers, showing animated item particles moving between them (configurable)
- **Arm animations** — Players seen using GUIs will show arm movements: pointing, clicking, typing on keyboards — bringing life to otherwise static third-person views
- **Privacy controls** — Every feature can be toggled client-side or server-side. Don't want others to see your GUI? Disable it in config
- **Server-synced config** — Server owners can control what information is broadcast to all players

## How It Works

The mod sends lightweight data packets between the client and server containing information about the player's current screen state, mouse position, and activity status. Other players within range (configurable, default 10 blocks) can see this information displayed as particles above the player's head.

## Configuration

Three config files are generated on first run:

- **`player_activity_view-common.toml`** — Idle timeout and chat announcements
- **`player_activity_view-client.toml`** — Client-side visual toggles, privacy settings, particle scaling
- **`player_activity_view-server.toml`** — Server-controlled settings synced to all clients

## Commands

- `/player_activity_view reloadJSON` — Reloads the arm adjustment JSON config file

## Requirements

- Minecraft 1.21 (26.1.2)
- NeoForge 26.1.2.78+

## Credits

Originally inspired by WATUT (What Are They Up To) by Corosus. This is a complete ground-up rewrite and modernization for current NeoForge versions, with no dependency on CoroUtil.
