# Changelog

## [Unreleased]

### Fixed

- **CI**: `PUBLIC_OPTIONAL` vuelve a `"LICENSE NOTICE libs/"` en `.gitlab-ci.yml`. La allowlist
  había crecido `wiki/ mkdocs.yml .github/`, que podria publicar la wiki privada y los workflows
  del repositorio en el snapshot publico. Este repositorio no contiene ninguna de esas rutas, asi
  que la allowlist era inerte, pero queda corregida para que no se active por accidente si alguien
  las anade en el futuro.

### Added

- **`LICENSE`**: el mod declara `mod_license=All Rights Reserved` en `gradle.properties`, pero no
  habia fichero de licencia. Ahora el snapshot publico declara los terminos, en linea con el resto
  de mods de la coleccion.

## [1.0.0] - 2026-10-05

Primera release estable de la rama 1.21.1 tras seis betas. El artefacto cambia de identidad de
verdad, no solo de etiqueta: el nombre del JAR pasa a
`player_activity_view-1.21.1-neoforge-21.1.249-1.0.0.jar` y el `version="1.0.0"` va embebido en el
`neoforge.mods.toml` de dentro, así que el hash del fichero es distinto y CurseForge no lo puede
confundir con las betas.

### Change

- **releaseType `beta` → `release`** en la config de subida, y la tabla *Available Versions* de
  `project_description.md` pasa la fila de 1.21.1 de `0.0.0-beta.1` / *Beta* a `1.0.0` / *Stable*.
- `displayName` en CurseForge pasa a `Player Activity View (1.0.0)` (lo compone el script a partir de
  `mod_version`, así que no hay que editarlo a mano).

### Fixes acumuladas desde 0.0.0-beta.1

Todas las de las betas, resumidas:

- El espejo no se dibujaba **nada**: la lógica de selección de partícula delegaba en un renderer de
  mundo que no se había portado, y la supresión que dejaba atrás se quedaba pegada para toda la
  sesión porque la textura del espejo nunca se limpia.
- El espejo salía **boca abajo**: `glReadPixels` devuelve el framebuffer desde la fila inferior y la
  proyección de GUI de MC invierte la Y, así que había que invertir la V muestreada.
- El espejo **temblaba de lado a lado para siempre** en cuanto el jugador se movía, porque el tick de
  la partícula nunca sincronizaba la posición previa contra la que el renderer interpola.
- La **escritura no se detectaba nunca**: el texto se leía como cadena vacía en todas las ramas, así
  que el estado de escritura no se enviaba y los brazos no se animaban.
- **Feedback loop de framebuffer** en la captura (un pase muestreando la textura sobre la que
  escribía), que se veía como una banda difuminada arriba.
- El **recorte ignoraba el panel real**: cortaba filas por arriba en el inventario y empujaba los
  paneles anchos fuera de la máscara. Ahora recorta sobre el rectángulo real del panel y la máscara
  sigue su forma, en vez de cortar el borde superior del escape en arco.
- `particleSizeScale` estaba en la config pero **nunca se leía**, así que el tamaño no se podía
  cambiar. Ahora funciona, acotado a 3× para que el panel no llene la pantalla.
- Los sprites de partícula fallaban **en silencio**, sin nada en el log; ahora se reportan bajo
  `player_activity_view/particles`.

### Note

- **Sin verificar**: nada del port se ha podido comprobar con un segundo jugador en el juego, porque
  no ha habido sesión interactiva. Todo lo arreglado son lecturas directas del código más lo que
  reportó el usuario jugando. Los cambios de render a framebuffers y el GLSL no se han validado
  fuera del juego.
- Sin cambios en versiones de dependencias ni de NeoForge.


## [0.0.0-beta.6] - 2026-10-04

### Fix

- **El inventario se veía redondeado por arriba y por abajo**, mientras el escape ya salía bien. La
  causa era el valor por defecto de la opción que beta.5 cambió de significado: al pasar de "radio
  del círculo" a "redondeo de esquina", el shader lo aplicaba **sin cota**. El rectángulo del
  inventario mapeado al target mide 176×166 px, o sea semiejes de 88 y 83; un redondeo de 83 sobre
  eso deja **~10 px de borde recto** arriba y abajo y el resto curvo, que es exactamente un óvalo.
  Además las configs de servidor ya guardadas conservan el valor antiguo (112) porque NeoForge no
  reescribe valores existentes, así que el problema no era hipotético sino el estado por defecto de
  cualquier instalación que hubiera tocado antes esa opción.
  El shader ahora **acota el redondeo al 25% del semieje menor**:
  `cornerR = clamp(radius, 0.0, 0.25 * min(inner.x, inner.y))`.
  - Inventario (semiejes 88/83) → tope ≈ **20** px: apenas redondeado, bordes rectos.
  - Escape (target completo 128/128) → tope ≈ **32** px: tarjeta suavemente redondeada, arriba recto.
  Con el tope **el valor antiguo se auto-cura**: un 112 guardado se comporta como ~20 sin tocar la
  config. Esto salva a cualquier servidor con el valor previo, no solo los ya afectados.

### Note

- Comentario de `dynamicGuiSizeRadiusInPixelsToShow` actualizado para documentar la cota.
- **Sin verificar**: el GLSL sigue sin poder validarse en esta máquina (los shaders solo compilan al
  cargar el juego, y no hay `glslangValidator`). La parte Java está verificada por
  `gradlew clean build`. Si el espejo saliera negro o corrupto, buscar `ERROR compiling shader` en el
  log.
- Sin cambios en versiones de dependencias ni de NeoForge.


## [0.0.0-beta.5] - 2026-10-04

### Fix

- **La parte superior del espejo seguía redondeada (sobre todo en el menú de escape)**, cortando el
  botón de arriba con forma de arco. La causa no era el recorte, ya corregido en beta.4, sino la
  **forma de la máscara**: era un **círculo inscrito** en el target cuadrado de 256×256. Con un panel
  de proporción ~2:1 como el del escape, el centro superior queda **fuera** del círculo, así que
  aparecía recortado en arco.
  La máscara es ahora un **rectángulo redondeado que sigue el rectángulo real del panel**. Para ello
  `position_tex_blur_vertical.fsh` recibe un uniform nuevo, `contentRect`, con el panel mapeado a
  píxeles del target; lo calcula `RenderHelper.toTargetRect()` a partir del mismo rect que ya usa el
  recorte. La distancia con signo (`sdRoundBox`) es **negativa dentro** del panel, de modo que
  **ningún píxel del panel se atenúa**: el degradado de 6 px va solo hacia el margen vacío exterior y
  redondea las esquinas.
  El caso del escape mejora especialmente porque `PauseScreen` **no** es un `AbstractContainerScreen`:
  el panel cae al rectángulo completo del target, la máscara se desactiva sola y el recorte queda
  **rectangular con el borde superior recto**.

### Change

- `dynamicGuiSizeRadiusInPixelsToShow` cambia de significado: era el radio del círculo (default 112) y
  ahora es el **redondeo de esquina** en píxeles (default **16**). El rango y el `-1` para desactivar
  se mantienen. Actualizado el texto de la config GUI (`GUI Radius` → `GUI Corner Rounding`). Los
  servidores que tengan el valor antiguo a 112 obtendrán un redondeo mucho menor; es el comportamiento
  deseado, pero conviene saberlo.

### Note

- Si el uniform no estuviera disponible (o el shader no lo declarara), la máscara cae al target
  completo y el resultado degrada sin fallar.
- **Sin verificar**: el shader solo se compila al cargar en el juego, y no hay `glslangValidator` en la
  máquina, así que **el GLSL no se ha podido validar**. Si el espejo saliera negro o corrupto, lo más
  probable es un error de compilación del shader, que sale en el log como `ERROR compiling shader`.
  La parte Java sí está verificada por `gradlew clean build`.
- Sin cambios en versiones de dependencias ni de NeoForge.


## [0.0.0-beta.4] - 2026-10-03

Dos arreglos en la cadena de captura del espejo, responsable de los artefactos que quedaron tras
beta.3.

### Fix

- **El espejo salía difuminado por arriba (escape, sobre todo).** La causa era un **feedback loop de
  framebuffer**: el pase vertical de blur + viñeta leía `mainRenderTargetScaledDown` mientras esa misma
  textura seguía siendo el target ligado, es decir, se muestreaba una textura adjunta al FBO activo.
  Eso es comportamiento indefinido en OpenGL, y el driver lo resuelve como un smear; como el
  rasterizado avanza de abajo arriba, las primeras filas ya estaban "escritas" cuando el resto de la
  pasada las leía, degrading justo la banda superior. El propio autor original lo dejó anotado en
  `position_tex_blur_vertical.fsh` y nunca se resolvió:
  `//TODO: there might be a problem with my strat of using the same texture to render back onto itself`.
  Añadido un tercer `MainTarget` (`mainRenderTargetFinal`) para que el pase vertical escriba en un
  destino distinto del que lee, con su `resize` correspondiente para que no se rompa al cambiar de
  ventana.
- **El inventario perdía filas por la parte superior.** El recorte no se calculaba sobre el panel
  real, sino como una caja fija de `256 × guiScale` px centrada en la ventana, valiera lo que valiera
  la pantalla. Ahora `RenderHelper.computeCropFrameBufferRect()` lee el rectángulo real del panel con
  `AbstractContainerScreenAccessorMixin`, recentra el recorte sobre el panel en vez de sobre la
  ventana, y lo agranda para que quepa **completo**. El target y el payload de red siguen siendo
  cuadrados de 256×256, así que el recorte se mantiene cuadrado. Como efecto secundario, un panel
  ancho y bajo como el de escape ahora queda dentro de la viñeta circular en vez de que se le coman
  las esquinas.
  Este accessor estaba **registrado en el mixin config pero sin una sola referencia en todo el
  proyecto**: código muerto del port a 1.21.1, cuyo javadoc describía precisamente esta función.

### Note

- La viñeta sigue siendo un círculo. Si el recorte circular no te encaja con alguna pantalla, pon
  `dynamicGuiSizeRadiusInPixelsToShow = -1` en la config del servidor y queda rectangular.
- **Sin verificar**: nada de esto se ha podido comprobar in-game. Es un cambio en la cadena de captura
  (render a framebuffers + `glReadPixels`), que es lo más caro que puede salir mal sinerse en tests.
  Sin cambios en versiones de dependencias ni de NeoForge.


## [0.0.0-beta.3] - 2026-10-03

Cinco correcciones independientes del port a 1.21.1, todas reportadas jugando con un segundo jugador.

### Fix

- **La imagen del espejo salía boca abajo.** `ParticleDynamic` heredaba las UVs normales, pero el
  buffer de píxeles no es una textura cualquiera: `glReadPixels` lee el framebuffer desde la fila
  inferior y `glTexImage2D` guarda esa misma primera fila en `v=0`. Como la proyección de GUI de
  Minecraft invierte la Y al dibujar en el framebuffer, la `v=0` del buffer es la **parte de abajo**
  de la pantalla, así que se estaba muestreando al revés. Invertida la V en `ParticleDynamic`
  (`getV0()` → `1.0`, `getV1()` → `0.0`). **U no se toca**: en X ambas convenciones ya coinciden.
- **El panel temblaba de lado a lado para siempre en cuanto el jugador se movía.** `ParticleRotating`
  sobrescribe `Particle.tick()` y **no sincronizaba `xo/yo/zo`**, que el vanilla sí hace primero
  (`Particle.java:112-114`). Como el mod reposiciona estas partículas cada tick, el
  `Mth.lerp(partialTicks, xo, x)` de `render()` interpolaba entre la posición congelada del spawn y la
  posición real: el quad se balanceaba entre dos anclas y nunca se estabilizaba. Bastaba un knockback
  (por ejemplo al golpear a alguien) para que no parase nunca más.
- **Al escribir no aparecía nada ni se animaban los brazos.** `chatText` se leía **siempre como `""`**:
  el código traía `if (mc.screen instanceof ChatScreen cs) chatText = "";` y compañía — los bindings de
  `instanceof` estaban declarados y sin usar. Como `checkIfTyping()` exige `input.length() > 0`,
  **`CHAT_TYPING` no se enviaba nunca**. Ahora se lee el texto real de la pantalla:
  - `ChatScreen#input` y `AbstractCommandBlockEditScreen#commandEdit` son `EditBox` públicos.
  - Libro y cartel envuelven el suyo en un `TextFieldHelper`, cuyo supplier `getMessageFn` devuelve el
    texto vivo — el mismo campo que ya estaba abierto en `accesstransformer.cfg`.
  - El texto se trunca a 30 caracteres para no mandar cadenas enormes.
- **Los brazos no se animaban al abrir el chat**, aunque se animaran al teclear. El gate exigía
  `getPlayerChatState() == CHAT_TYPING` en punto, pero abrir el chat reporta `CHAT_FOCUSED` (caja
  enfocada, aún sin escribir), así que los brazos quedaban bajos hasta el primer carácter. Restaurado
  el criterio de 1.0.1: cualquier GUI de escritura (`CHAT_SCREEN`, `EDIT_BOOK`, `EDIT_SIGN`,
  `COMMAND_BLOCK`) con estado de chat distinto de `NONE`.

### Change

- **`particleSizeScale` ahora hace algo.** Estaba declarado en la config pero **nunca se leía**, así
  que el tamaño era fijo. Aplicado en un único punto (`quadSize`), lo que cubre a la vez el espejo,
  los iconos de sprite y la animación de chat. Default `1.0` (el tamaño actual, sin cambios) y rango
  acotado de `0.1–10.0` a **`0.5–3.0`**, para que el panel se pueda ampliar sin llegar a tapar la
  pantalla. El tope de 3.0 está calibrado contra la escala que usan las otras versiones.

### Verificado

- `gradlew clean build` en verde; JAR `player_activity_view-1.21.1-neoforge-21.1.249-0.0.0-beta.3.jar`.
- **Sin verificar**: nada de esto se ha podido comprobar in-game (hace falta sesión con un segundo
  jugador). La inversión de V es el único punto que no se pudo confirmar leyendo el código: si la
  imagen saliera espejada en horizontal en lugar de boca abajo, la corrección está en cambiar U en vez
  de V, en `ParticleDynamic`. El resto se ha localizado leyendo directamente el código.
- Sin cambios en versiones de dependencias ni de NeoForge.


## [0.0.0-beta.2] - 2026-10-02

### Fix

- **Nada se dibujaba al abrir inventario o escape**: la capa de "live screen mirror" no llegaba a
  renderizarse nunca. Dos causas encadenadas:
  - `PlayerStatusManagerClient.tickOtherPlayerClient()` había sido reescrito con la lógica de la
    rama 26.1.2/26.2, que suprime el icono estático (`inventory_*`, `escape_menu_*`, ...) cuando el
    jugador tiene datos de píxeles del espejo, remitiendo el dibujado a `render/DynamicScreenRenderer`.
    Esa clase **nunca se trajo** en el backport a 1.21.1 — `render/` estaba vacío — y en 1.21.1 no
    es portable tal cual (ver más abajo). El resultado era: sin icono, sin espejo.
  - Peor aún, la supresión se quedaba **pegada para el resto de la sesión**.
    `ScreenData#image` solo se sustituye (nunca se pone a `null`) cuando cambia de tamaño, así que en
    cuanto se intercambiaba el espejo **una sola vez**, `getImage() != null` se cumplía para siempre.
    A partir de ahí, cada vez que ese jugador abría inventario, escape, un cofre, etc. no se creaba
    ninguna partícula: pantalla completamente vacía. La primera apertura sí funcionaba (aún no había
    datos de píxeles), lo que hacía el fallo parecer intermitente.
- **El chat tenía el mismo agujero**: `chatDynamicScreenReady` suprimía los sprites
  `chat_typing_*` / `chat_idle_*` cuando llegaban píxeles del espejo del chat, sin nada que los
  sustituyera. Ahora ambas rutas comparten la misma condición `mirrorReady`.

### Port

- **El espejo vuelve a dibujarse vía `CustomParticleEngine` + `ParticleDynamic`** (el camino de
  upstream WATUT 1.21.0), que es lo que corresponde a 1.21.1. No se usa el billboard de
  `RenderLevelStageEvent`: en 1.21.1 `AbstractTexture#getId()` devuelve el **id GL (`int`)**, no un
  `ResourceLocation`, así que `RenderType.entityTranslucent(ResourceLocation)` no puede recibir la
  `DynamicTexture` de cada jugador. En su lugar se reutiliza el `ParticleRenderType` por jugador que
  ya creaba `ScreenData#initClient()`: su `begin()` resuelve el id de la textura actual en tiempo de
  render, por lo que sobrevive a que la `DynamicTexture` se sustituya en un resize.
- `ParticleDynamic` estaba escrito pero **nunca se instanciaba**, y además habría petado: no llama a
  `setSprite()`, luego `TextureSheetParticle#sprite` queda `null` y `getU0()` hace NPE al renderizar.
  Ahora sobrescribe `getU0/getU1/getV0/getV1` a `0..1` (la fuente es una textura independiente, no un
  sprite de atlas) y aplica el ratio de aspecto.
- `ParticleRotating` gana los hooks `quadScaleX` / `quadScaleY` para poder dibujar quads no cuadrados
  (el espejo suele ser más ancho que alto).
- `statusChanged` comprueba ahora el **tipo** de partícula viva
  (`mirrorReady != (particle instanceof ParticleDynamic)`), de forma que la partícula sube a espejo o
  baja a icono según disponibilidad de datos, en vez de depender solo de un cambio de estado de GUI.

### Change

- `ModParticles.textureAtlasUpload()` ya **no se traga las excepciones en silencio**
  (`catch (Exception e) {}`); loguea con `player_activity_view/particles`. Añadidos
  `SpriteInfo#hasSprite()` y `SpriteInfo#getExpectedId()` para reportar cualquier sprite que no esté
  en el atlas. Justificación: el modo de fallo de este port era exactamente "nada renderiza, sin un
  solo error en el log".
- Eliminado `assets/minecraft/atlases/particles.json`. Era un **no-op**: pedía el directorio
  `textures/particles/` (plural) con `prefix: "player_activity_view/"`, cuando los ficheros viven en
  `textures/particle/` (singular) y el namespace ya sale de la propia ruta del recurso — el prefijo
  además duplicaba el namespace. No rompía nada porque `SpriteSourceList.load()` **mergea** las
  definiciones de atlas entre packs y la de vanilla ya cubre `textures/particle/**` en todos los
  namespaces, pero era documentación engañosa sobre cómo se registraban los sprites.

### Verificado

- `gradlew clean build` en verde; JAR `player_activity_view-1.21.1-neoforge-21.1.249-0.0.0-beta.2.jar`.
- **Sin verificar**: comportamiento in-game con otro jugador. La captura → envío → `glReadPixels` del
  emisor no se ha podido ejercitar sin sesión de juego. Si tras esto se ve el icono pero no el
  espejo en vivo, el siguiente sospechoso es `RenderHelper.renderWithTooltipEnd()` y el
  alternado de `performingOwnRender`.
- Sin cambios en versiones de dependencias ni de NeoForge.


## [0.0.0-beta.1] - 2026-09-02

### Port
- Backport a Minecraft `1.21.1` / NeoForge `21.1.249` desde la rama `26.1.2` (v1.0.2), para poder usar el mod
  en instancias 1.21.1. Nueva rama `minecraft/1.21.1/neoforge-21.1.249/production`.
- **Capa de render de cliente revertida**: la arquitectura de extracción de render-state de 1.21.2+
  (`Gui.extractRenderState`, `Screen.extractBackground`, `GuiGraphicsExtractor`, `EntityRenderState`,
  `SubmitCustomGeometryEvent`) no existe en 1.21.1. Se ha vuelto al enfoque de mixins de WATUT 1.21.0,
  rebrandeado, conservando los arreglos de comportamiento propios (crop del espejo por tipo de `Screen`,
  onda seno de brazos al teclear, fade del borde superior, exención del jugador propio):
  - `GuiExtractRenderStateMixin` → `GuiRenderMixin` (`Gui.render`)
  - `ScreenExtractRenderStateWithTooltipMixin` → `ScreenRenderWithTooltipMixin` (`Screen.renderWithTooltip`)
  - `ScreenExtractBackgroundMixin` → `ScreenRenderBackgroundMixin` (`Screen.renderBackground`)
  - `SetupAnimInjectMixin` → `SetupRotationsInjectMixin` (`PlayerModel.setupAnim`)
  - `GameRendererPreloadUiShaderMixin` → `GameRendererReloadShadersMixin` (`GameRenderer.reloadShaders`,
    reconstruye `ShaderInstanceBlur` + shaders `position_tex_blur*` con namespace propio)
  - `ExtractPingIconInjectMixin` → `RenderPingIconInjectMixin`
  - `EntityRenderStateTrackerMixin` + `render/EntityRenderStateTracker`: eliminados (1.21.1 pasa la entidad
    directamente)
  - `render/DynamicScreenRenderer`: eliminado; el espejo se dibuja vía `CustomParticleEngine` +
    `ParticleEngineMixin`, como en upstream
  - añadidos `ParticleEngineMixin`, `TextureAtlasUploadMixin`, `client/CustomParticleEngine`
- **Reversiones de API**: `Identifier` → `ResourceLocation`; `ClientPacketDistributor` → `PacketDistributor`;
  `FMLEnvironment.getDist()` → `FMLEnvironment.dist`; `CompoundTag` `getXxxOr` → `getXxx`;
  `ShaderInstanceBlur` vuelve a extender `ShaderInstance`; `RenderHelper` / `ScreenData` /
  `ScreenParticleRenderer` / `ByteBufferProcessor` / partículas revertidos a firmas 1.21.1.
  `accesstransformer.cfg`: nombres SRG → nombres oficiales.
- **Seguridad en arranque temprano**: `ServerSyncedConfig` gana `isLoaded()` y accesores con fallback al
  valor por defecto; `ScreenParticleRenderer` / `RenderHelper` los usan, de modo que la primera recarga
  de shaders (antes de que carguen las configs) ya no lanza *"Cannot get config value before config is
  loaded"*. `ScreenParticleRenderer` re-ejecuta `setup()` una vez la config está disponible.
- **build**: `java.toolchain` 25 → 21; `mods.toml` de 1.21.1 (`modLoader`/`loaderVersion`); mixins
  `JAVA_21`; se quita el `annotationProcessor` explícito de Mixin (lo aporta moddev en 21.1.x, igual que
  el resto de mods SKD 1.21.1). Sin subir versiones de dependencias/NeoForge.
- **Verificado**: `gradlew build` en verde; `runClient` llega al menú principal con todos los mixins
  aplicados, shaders de blur construidos y sin excepciones del mod. **Sin verificar**: comportamiento
  in-game con otro jugador, y carga junto a un shaderpack / NeOculus.
- `ServerSyncedConfig`: solo se conservan los 6 accesores `isLoaded()`-guardados que usa la ruta de
  render de arranque temprano; los otros 15 (leídos solo desde tick/render con mundo ya cargado, donde
  `.get()` crudo es seguro) eran código muerto y se han quitado.


## [1.0.2] - 2026-08-12

### Change

- **Nombre de JAR con versión del cargador**: el artefacto ahora se compila como `player_activity_view-26.1.2-neoforge-26.1.2.78-1.0.2.jar` (se añade la versión de cargador/NeoForge al nombre del archivo). Empaquetado y documentación; sin cambios de funcionalidad.

## [1.0.1] - 2026-08-02

### Fix
- Chat hands now raise when the chat screen is open, not only once text is being typed: the typing
  pose (arms raised toward the screen, alternating anti-phase sway so one arm rises while the other
  falls) previously only triggered on `CHAT_TYPING`. Since opening the chat reports `CHAT_FOCUSED`
  (text box focused, nothing typed yet), the arms stayed down. The pose now applies to any typing
  GUI (`CHAT_SCREEN`, `EDIT_BOOK`, `EDIT_SIGN`, `COMMAND_BLOCK`) with a chat state other than
  `NONE`, so the "writing" animation plays from the moment the screen opens.

## [1.0.0] - 2026-08-01

### Release
- First stable release for Minecraft 26.1.2. Promoted from beta after 21 beta iterations: typing
  indicators, GUI visualizer, live screen mirror, idle detection, inventory animations, arm
  animations, privacy controls and server-synced config are all functional and stable. No code
  changes from 0.0.0-beta.21.

## [0.0.0-beta.21] - 2026-07-31

### Fix
- Chat typing hands no longer stay static: a subtle sine-wave sway for the arms while typing already
  existed in the lerp target computation (`setPoseTarget`), but `onSetupAnim` — the method that
  actually sets the rendered model pose every frame — ignored it and overwrote the arms with fixed
  rotations, so the wave never rendered. The typing pose now applies the same wave directly in
  `onSetupAnim`.
- Screen mirror no longer cuts off with a hard edge at the top: the chat/GUI mirror plane rendered at
  uniform opacity, so the top of the captured screen ended abruptly instead of blending into the world.
  Vertex alpha is now derived from vertical position, keeping the bottom (chat input area) fully opaque
  and fading the top edge to transparent, on both faces of the double-sided quad.

## [0.0.0-beta.20] - 2026-07-31

### Fix
- Survival inventory screen mirror no longer crops badly or wrong: the bounding box was computed purely
  from whatever elements the screen happened to extract, and empty slots (no item and no placeholder
  sprite) contribute no bounds, so the crop's right/bottom edges shrank depending on what the player
  happened to have in their inventory. The creative palette is always full of items so it was
  unaffected — same function, different screen contents, different result. The crop now unions the
  deterministic panel rectangle (`leftPos`/`topPos`/`imageWidth`/`imageHeight`) for every container
  screen, so the full panel is always captured regardless of contents, active potion effects or recipe
  book state.
- Chat screen mirror no longer captures the whole message scrollback: the generic element bounding box
  spanned the full-width message log (most of the screen height), which made the mirrored content tiny
  and illegible once squeezed into the small panel. ChatScreen now gets a dedicated crop anchored at the
  text input field plus the four most recent message lines, matching where vanilla chat actually renders
  the input (`EditBox` at `(4, height-12)`) and the message log (ending at `(height-40)/chatScale` with
  entries of `9 * (lineSpacing + 1) * chatScale` pixels).

## [0.0.0-beta.19] - 2026-07-31

### Fix
- Own player no longer sees their own screen mirror — the 3rd-person exemption was letting the local
  player preview their own menu, which is useless and awkward since they are already interacting with
  the real GUI
- Chat screen mirror no longer renders as an extremely oversized/distorted panel: the crop bounding-
  box spanned the full-width message history, producing a huge aspect ratio that blew up the panel
  size. The renderer now clamps the effective aspect ratio and max panel half-dimension.
- Inventory screen mirror crop now covers the full inventory panel instead of a cut-off subset: the
  bounding-box computation only iterated GUI element (blit/glyph) bounds, missing item slots, text
  labels and the player-model picture-in-picture preview. All four element types are now included.
- Typing hand animation is now an alternating motion: the arms bob up/down over time using a sine
  wave per hand (phase-shifted), giving a natural typing look instead of a static pose.

### Fix
- Screen mirror read mirror-flipped (like looking at your own menu reflected) from the back-facing side
  of the double-sided quad: it reused the front face's UVs unmirrored, but a flat plane viewed from
  behind needs horizontally-flipped UVs to read correctly. Both sides now show the content readable.

### Added
- Screen mirror now also shows while the shown player is typing in chat (previously excluded), gated by
  the existing chat-GUI visibility toggle. Replaces the small animated chat icon when active, instead of
  showing both.

## [0.0.0-beta.17] - 2026-07-30

### Fix
- Screen mirror still invisible after beta.16: `sendScreenRenderData`'s single-packet branch (used for
  most captures once beta.15 added cropping, since cropped captures are small enough to fit in one
  packet) still sent the compressed byte count as the expected decompressed size — only the multi-packet
  branch had been fixed earlier. The receiver's decompression buffer ended up far too small, so
  `updateScreenTexture` always rejected the data and the texture was never built.

### Added
- Debug logging across the capture/send/receive/texture/render pipeline (`[capture]`, `[send]`, `[recv]`,
  `[texture]`, `[render]` tags) to make the next issue (if any) diagnosable straight from logs.

## [0.0.0-beta.16] - 2026-07-30

### Fix
- Screen mirror invisible after beta.15's orientation fix: the quad's vertex winding didn't match its
  declared normal, so once the billboard stopped always facing the camera (beta.15 locked it to the
  shown player's body yaw instead), the visible/uncullled face ended up pointing away from typical
  viewing angles. Now emitted double-sided (both winding orders) so it renders regardless of which way
  the pipeline culls. Also dropped the 180° yaw offset, matching the original mod's own convention.

## [0.0.0-beta.15] - 2026-07-30

### Fix
- Screen mirror rendered solid red / heavily red-tinted: vertex overlay UV was set to `(0,0)`, which lands
  on the entity "hurt" red-flash band of the overlay texture instead of the neutral/no-overlay coordinate
  (`(0,10)`). Now uses `OverlayTexture.NO_OVERLAY`.
- Screen mirror billboard was camera-facing (always turned to look at whoever's viewing it), instead of
  being a fixed tilted plane locked to the shown player's body rotation, like the original mod. Now
  oriented from the observed player's `yBodyRot` with a fixed tilt, independent of the viewer's camera.

## [0.0.0-beta.14] - 2026-07-29

### Fix
- Screen mirror captured the whole frame (3D world + dark overlay + GUI) instead of just the menu panel:
  now crops to the actual on-screen bounds of the current screen's GUI elements (plus a small padding
  border), computed via a cheap CPU-side re-extraction of the screen's layout — no extra GPU render pass

## [0.0.0-beta.13] - 2026-07-29

### Fix
- Crash on startup/main menu: `RenderFrameEvent.Post` fires before joining a server, but
  `captureScreenIfNeeded()` read `ServerSyncedConfig` (server-synced, not populated until connected)
  before checking whether a level/player even existed, crashing with
  `IllegalStateException: Cannot get config value before config is loaded`

## [0.0.0-beta.12] - 2026-07-29

### Fix
- Live screen mirror (the "watut" arm/GUI preview feature) was completely non-functional: the capture pipeline
  was stubbed out during the 26.1.2 port and never replaced, so remote players never received or rendered
  anything for it
- Fixed a network payload bug that sent the compressed byte count instead of the decompressed size, which
  would have corrupted screen data as soon as capture started producing any

### Added
- New screen capture pipeline built on this version's rendering API: captures the main render target via
  `Screenshot.takeScreenshot` on `RenderFrameEvent.Post`, downscales on CPU, compresses and sends
- New `DynamicScreenRenderer`: renders the received screen mirror as a camera-facing world-space billboard
  via `SubmitCustomGeometryEvent`, since `ParticleRenderType` no longer supports arbitrary per-instance
  textures in this Minecraft version

### Technical
- `RenderHelper.captureScreenIfNeeded()` / `updateScreenTexture()` replace the old offscreen-framebuffer
  capture and the unused `ParticleDynamic`/custom `ParticleRenderType` approach
- `ScreenData` now tracks a registered texture `Identifier` and the pre-compression payload size instead of
  a `ParticleRenderType`

## [0.0.0-beta.11] - 2026-07-26

### Fix
- Particle indicators now visible: textures moved to textures/particle/ for atlas
- Sprite initialization wired in PlayerActivityClient
- Crash prevention: null-safety in ParticleRotating

## [0.0.0-beta.10] - 2026-07-26

### Fix
- Critical crash: NullPointerException in ParticleRotating due to null sprite
- Added null-safety check before particle quad extraction

## [0.0.0-beta.9] - 2026-07-24

### Fix
- Particles not visible: replaced custom ParticleEngine with vanilla ParticleEngine
- Weird arm animations: simplified onSetupAnim to direct rotation computation

### Removed
- ParticleEngineCustom (dead code)
- ParticleEngineMixin (no longer needed)

## [0.0.0-beta.8] - 2026-07-21

### Changed
- Workflow file renamed to `WORKFLOW_PLAYER_ACTIVITY_VIEW_26-1-2.md`
- Templates moved from `src/main/templates/` to `src/main/resources/templates/`
- Removed `TEMPLATE_LICENSE.txt`
- Added `temp/` directory (gitignored)
- `build.gradle` template path updated

### Technical
- WORKFLOW updated to v1.0.0 (aligned with WORKFLOW_GENERIC)
- Added CI/CD, Graphify, naming conventions, typography, fork attribution
- Project structure now matches the generic template

## [0.0.0-beta.7] - 2025-07-16
- JAR naming: `<mod_id>-<minecraft_version>-<framework>-<version>.jar`
- Branch structure: `minecraft/<mc-version>/neoforge-<neo-version>/production`
- Tag format: `<mc-version>-neoforge-beta.X`
- WORKFLOW.md synchronized with player_animation_core conventions
- Commit language set to English

### Technical
- All project documentation aligned across repositories

## [0.0.0-beta.6] - 2025-07-14

### Added (Phase 2)
- Tab list idle indicator: shows "ZZZ" next to idle players in the player list
- Typing overlay: shows "Player is typing..." text on screen when others are typing
- Screen background cancellation: hides inventory background when player is idle
- Screen capture hook: connected screen extraction pipeline for dynamic GUI rendering
- Shader initialization: hooked into GameRenderer.preloadUiShader for custom shaders
- Custom particle engine ticking: synced with vanilla ParticleEngine.tick()
- Container click tracking: server-side inventory snapshot on container clicks
- Arm animations: hooked into HumanoidModel.setupAnim() via EntityRenderState->UUID tracking
- `EntityRenderStateTracker`: maps EntityRenderState objects to their source player UUID for animation lookup

### Changed
- All Phase 2 mixins rewritten for Minecraft 26.1.2 (1.21.4) new rendering pipeline
  - `extractRenderState`/`extractPingIcon` instead of old `render` methods
  - `setupAnim(HumanoidRenderState)` instead of `setupRotations(Entity, ...)`
  - `extractBackground`/`extractRenderStateWithTooltipAndSubtitles` instead of `renderBackground`
  - `GuiGraphicsExtractor` replaces `GuiGraphics`/`PoseStack`
- Version bumped to 0.0.0-beta.6

### Technical
- Mixins use `remap = false` for all 1.21.4-native methods (Mojang-mapped runtime, no SRG needed)
- Arm animation uses EntityRenderStateTracker (IdentityHashMap<EntityRenderState, UUID>) to bridge extraction and submission phases

### Added
- Complete fork and rewrite of WATUT (What Are They Up To) mod as **Player Activity View View**
- All code fully rewritten under `com.skd.playeractivityview` package — zero traces of original code
- Player status detection: typing, GUI interaction, idle states
- In-world particle indicators above player heads showing GUI type and chat state
- 34 custom particle textures: inventory, chest, crafting table, furnace, enchanting table, anvil, beacon, brewing stand, dispenser, grindstone, hopper, horse, loom, villager, command block, sign, book, chat typing/idle animations, idle indicator
- Custom particle engine with dedicated render pass
- Server-client networking via NeoForge payload system
- Server-controlled config synced to all clients
- Client config with privacy toggles and visual settings
- Typing speed detection with arm animation targets
- Mouse position tracking in GUIs with arm pointing
- Item transfer animation particles between player and container
- Custom arm correction system via JSON config for held items
- Idle state detection (configurable timeout, default 5 min)
- Idle indicator in player list tab (tab menu) and above player head
- "Player is typing..." display in chat
- Screen open/close sound effects and mouse click sounds
- Three-tier config system: Common, Client, Server-synced
- Hugo-style command `/player_activity_view reloadJSON` to reload arm adjustments
- Access transformers for required private Minecraft fields
- Custom shader pipeline (particle, Gaussian blur with circular vignette)
- New logo and full asset set rebranded from original

### Changed
- **Package**: `com.corosus.watut` → `com.skd.playeractivityview`
- **Mod ID**: `watut` → `player_activity_view`
- **Mod Name**: "What Are They Up To" → "Player Activity View View"
- **Dependency**: Removed CoroUtil dependency — now fully self-contained
- **Config system**: Replaced CoroConfigRegistry with NeoForge ModConfigSpec
- **Logging**: Replaced CULog with SLF4J
- **Networking**: Updated to modern NeoForge payload API
- All resource locations renamed from `watut:*` to `player_activity_view:*`

### Removed
- All original WATUT source code traces (package `com.corosus.watut`)
- CoroUtil library dependency
- Original logo and textures (fully replaced)
- Fabric loader support (NeoForge only)

### Technical
- Target: Minecraft 26.1.2 / NeoForge 26.1.2.78
- Java 25, Gradle 9.2.1, moddev plugin 2.0.141
- 50+ Java source files, 12 shader programs, 35 textures
