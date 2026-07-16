<h1 align="center">Player Activity View</h1>
<p align="center"><strong>See what other players are doing at a glance</strong></p>

<hr>

<h2>Overview</h2>

<p>Player Activity View lets you see what other players are doing at a glance — whether they're typing in chat, browsing their inventory, using a crafting table, or just standing around idling. No more wondering if someone is AFK or just quiet.</p>

<br>

<h2>Features</h2>

<h3>Typing Indicators</h3>
<p>See when a player is typing in chat, editing a book, or writing on a sign, with animated particles above their head and a <em>"is typing..."</em> message displayed on your screen.</p>

<h3>GUI Visualizer</h3>
<p>Shows exactly what interface a player is using: inventory, chest, crafting table, furnace, enchanting table, anvil, beacon, brewing stand, dispenser, grindstone, hopper, horse, loom, villager trading, command blocks, and more.</p>

<h3>Idle Detection</h3>
<p>Automatically detects when players go idle (configurable timeout, default 5 minutes) and shows an idle icon above their head and in the tab player list.</p>

<h3>Inventory Animations</h3>
<p>Watches items being transferred between players and containers, showing animated item particles moving between them (configurable).</p>

<h3>Arm Animations</h3>
<p>Players seen using GUIs will show arm movements: pointing, clicking, typing on keyboards — bringing life to otherwise static third-person views.</p>

<h3>Privacy Controls</h3>
<p>Every feature can be toggled client-side or server-side. Don't want others to see your GUI? Disable it in config.</p>

<h3>Server-Synced Config</h3>
<p>Server owners can control what information is broadcast to all players.</p>

<br>

<h2>How It Works</h2>

<p>The mod sends lightweight data packets between the client and server containing information about the player's current screen state, mouse position, and activity status. Other players within range (configurable, default 10 blocks) can see this information displayed as particles above the player's head.</p>

<br>

<h2>Configuration</h2>

<p>Three config files are generated on first run:</p>
<ul>
<li><code>player_activity_view-common.toml</code> — Idle timeout and chat announcements</li>
<li><code>player_activity_view-client.toml</code> — Client-side visual toggles, privacy settings, particle scaling</li>
<li><code>player_activity_view-server.toml</code> — Server-controlled settings synced to all clients</li>
</ul>

<br>

<h2>Commands</h2>
<ul>
<li><code>/player_activity_view reloadJSON</code> — Reloads the arm adjustment JSON config file</li>
</ul>

<br>

<h2>Requirements</h2>
<table>
<tr><td><strong>Minecraft</strong></td><td>26.1.2</td></tr>
<tr><td><strong>NeoForge</strong></td><td>26.1.2.78+</td></tr>
</table>

<br>

<hr>

<p align="center">
Originally inspired by WATUT (What Are They Up To) by Corosus. This is a complete ground-up rewrite and modernization for current NeoForge versions, with no dependency on CoroUtil.<br><br>
<strong>Player Activity View</strong>
</p>
