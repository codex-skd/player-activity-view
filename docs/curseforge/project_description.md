<h1 align="center">&#128064; Player Activity View</h1>

<p align="center"><strong>See what other players are doing at a glance &mdash; typing, idling, browsing menus, crafting, and more.</strong></p>

<p align="center">
<img src="https://img.shields.io/badge/loader-NeoForge%20%2F%20Fabric-orange?style=plastic&logo=curseforge" alt="NeoForge / Fabric">
<img src="https://img.shields.io/badge/minecraft-1.21.1%20%7C%2026.1.2%20%7C%2026.2-blue?style=plastic" alt="Minecraft 1.21.1, 26.1.2 and 26.2">
<img src="https://img.shields.io/badge/side-client%20%2B%20server-brightgreen?style=plastic" alt="Client and Server">
<img src="https://img.shields.io/badge/license-All%20Rights%20Reserved-lightgrey?style=plastic" alt="All Rights Reserved">
</p>

<br>

---

<br>

<h2>&#10024; Overview</h2>

<table>
<tr>
<td width="65%">
<p>Player Activity View shows what other players are doing in real time &mdash; typing in chat, browsing an inventory, using a crafting table, or just idling. Animated particles and on-screen indicators tell you exactly what's happening, and a small tilted panel in front of each player mirrors the menu they have open. Every feature can be toggled client-side, and server owners can force settings for everyone through a synced config.</p>

<p>A from-scratch reimplementation inspired by <strong>WATUT (What Are They Up To)</strong> by <em>Corosus</em>, modernised for current Minecraft with an independent codebase and no dependency on CoroUtil. Not affiliated with or endorsed by the original author.</p>
</td>
<td width="35%" align="center">
<a href="https://codex.skdragons.com/" target="_blank"><img src="https://node-files.skdragons.com/uploads/MINECRAFT/Codex/logo_codex_stalking_dragons.png" alt="Codex Stalking Dragons" width="160"></a>
</td>
</tr>
</table>

<br>

<h2>&#127919; Features</h2>

<h3>&#128172; Typing Indicators</h3>
<p>See when a player is typing in chat, editing a book or writing on a sign &mdash; animated particles above their head plus an on-screen <em>"is typing&hellip;"</em> message.</p>

<h3>&#128421;&#65039; GUI Visualizer</h3>
<p>Shows exactly which interface a player has open: inventory, chest, crafting table, furnace, enchanting table, anvil, beacon, brewing stand, dispenser, grindstone, hopper, horse, loom, villager trading, command blocks and more.</p>

<h3>&#128250; Live Screen Mirror</h3>
<p>A preview of the player's open menu is captured, cropped and shown as a small tilted panel held in front of them, visible to nearby players.</p>

<h3>&#128564; Idle Detection</h3>
<p>Detects when players go idle after a configurable timeout, with an idle icon above the head and in the tab list.</p>

<h3>&#128230; Inventory &amp; Arm Animations</h3>
<p>Animated item particles fly between players and containers when items move; players using GUIs point, click and type instead of standing still.</p>

<h3>&#128274; Privacy &amp; Server-Synced Config</h3>
<p>Every feature toggles client-side, and a server-side config syncs to every client so server owners control what is broadcast.</p>

<br>

<h2>&#129521; Mod Structure</h2>

<table>
<tr><th align="left">Area</th><th align="left">What it provides</th></tr>
<tr><td>status model</td><td>Per-player activity state, its network sync and the idle timer.</td></tr>
<tr><td>screen mirror</td><td>Framebuffer capture, cropping and the tilted in-world panel render.</td></tr>
<tr><td>particles</td><td>The typing / inventory-transfer particle system.</td></tr>
<tr><td>arm adjustments</td><td>The GUI arm-pose animations and their per-item JSON tuning.</td></tr>
<tr><td>config</td><td>Client and server-synced config.</td></tr>
</table>

<br>

<h2>&#128203; Requirements</h2>

<table>
<tr><td><strong>Minecraft / loader / Java</strong></td><td>see <em>Available Versions</em> below</td></tr>
<tr><td><strong>Dependencies</strong></td><td>Fabric build: Fabric API. NeoForge build: none.</td></tr>
<tr><td><strong>Side</strong></td><td>Client and Server (client required for the visuals; server for the synced config)</td></tr>
</table>

<br>

<h2>&#128230; Available Versions</h2>

<table>
<tr><th align="left">Minecraft</th><th align="left">Loader</th><th align="left">Latest build</th><th align="left">Status</th></tr>
<tr><td>1.21.1</td><td>NeoForge 21.1.249+</td><td><code>0.0.0-beta.1</code></td><td>Beta</td></tr>
<tr><td>26.1.2</td><td>NeoForge 26.1.2+</td><td><code>1.0.2</code></td><td>Stable</td></tr>
<tr><td>26.2</td><td>NeoForge 26.2.0.32-beta+</td><td><code>1.1.0</code></td><td>Stable</td></tr>
<tr><td>26.2</td><td>Fabric Loader 0.19.3+ / Fabric API</td><td><code>1.0.1</code></td><td>Stable</td></tr>
</table>

<p><em>All builds share this CurseForge project. Pick the file that matches your loader and Minecraft version.</em></p>

<br>

<h2>&#127918; Commands</h2>

<p><code>/player_activity_view reloadJSON</code> &mdash; reload the arm-adjustment configuration from the JSON file.</p>

<br>

---

<br>

<h2>&#128591; Credits &amp; License</h2>

<p>Player Activity View is a from-scratch reimplementation by <strong>Stalking Dragons</strong>, inspired by <strong>WATUT (What Are They Up To)</strong> by <em>Corosus</em>. It shares no code with WATUT and does not depend on CoroUtil. Released as <strong>All Rights Reserved</strong>. Not affiliated with or endorsed by the original author.</p>

<br>
<br>

<p align="center">
  <a href="https://codex.skdragons.com/" target="_blank">
    <img src="https://node-files.skdragons.com/uploads/MINECRAFT/Codex/logo_codex_stalking_dragons.png" alt="Codex Stalking Dragons" width="200">
  </a>
  <br>
  <a href="https://codex.skdragons.com/">https://codex.skdragons.com/</a>
  <br>
  <em>Codex Stalking Dragons &mdash; Minecraft Modding</em>
</p>
