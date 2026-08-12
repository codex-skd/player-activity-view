# Flujo de trabajo — Player Activity View (Fabric)

> **Versión del workflow**: 1.16.0 (codex-docs)
> Este archivo pertenece al proyecto **Player Activity View**. Cambios aquí solo afectan a este proyecto.
> **Trabaja directamente con este archivo**: es el workflow operativo del mod, autocontenido. No leas `codex-docs/WORKFLOW_AGENT.md` ni `WORKFLOW_GENERIC.md` de forma rutinaria.
> On-demand (solo si la tarea lo necesita): `codex-docs/reference/CURSEFORGE.md` (formato HTML al publicar), `codex-docs/reference/GRAPHIFY.md` (backend LLM de Graphify), `codex-docs/reference/REPO_SETUP.md` (setup único de repo).

## Específico del mod

| Dato | Valor |
|---|---|
| Mod ID (`gradle.properties`) | `player_activity_view` |
| Clase principal | `PlayerActivity` (ModInitializer) + `PlayerActivityClient` (ClientModInitializer) |
| Display name (Title Case) | `Player Activity View` |
| Versiones de Minecraft | `26.2` |
| Framework | `fabric` |
| Rama | `minecraft/26.2/fabric-0.19.3/production` |

### Notas específicas de este mod

- **Clase principal real**: `PlayerActivity` (no `PlayerActivityView`). Renombrado de clase diferido por blast radius (~50 usos/15 ficheros, feature en debug activo).
- **Port desde NeoForge 26.2**: código compartido en `src/main/java`, solo-client en `src/client/java` (Loom `splitEnvironmentSourceSets`). `PlayerStatus` en main es neutro; `PlayerStatusClient` (client) añade las partículas y el `ScreenData` del espejo de pantalla. La config usa `config/spec/ModConfigSpec` (JSON en el config dir), reemplazo del `ModConfigSpec` de NeoForge.

## Convenciones de nomenclatura

| Convención | Uso | Ejemplo |
|---|---|---|
| **snake_case** | `mod_id`, assets/, packages Java | `player_activity_view` |
| **PascalCase** | Clases Java principales | `PlayerActivity` |
| **camelCase** | Variables, métodos, config keys | `playerActivityViewConfig` |
| **Title Case** | Display name (README, CHANGELOG, docs, CurseForge) | `Player Activity View` |

## Organización y ramas

- Un repo GitLab por mod, una rama `minecraft/<mc>/fabric-<loader>/production` por versión. Este clon local trabaja en la rama `production` de esta versión.
- Carpetas: `<mod_id>/<framework>/<mc-version>/` — este clon vive en `<mod_id>/fabric/<mc-version>/`.
- `*/main` y CI/CD: setup único al crear el repo (`codex-docs/reference/REPO_SETUP.md`) — no releer ni modificar.

## Estructura del proyecto

`build.gradle` · `gradle.properties` (mod_version, maven_group, fabric_api_version) · `settings.gradle` · `src/main/java/<package>/` · `src/client/java/<package>/` (código solo-client) · `src/main/resources/` (fabric.mod.json, assets/<mod_id>/, mixins) · `src/client/resources/` (mixins client) · `docs/` (WORKFLOW + curseforge/) · `CHANGELOG.md` · `README.md` · `graphify-out/` (versionado).

## Versionado

- Beta `0.0.0-beta.X` · Release `X.Y.Z` (SemVer: MAJOR breaking / MINOR feature / PATCH fix)
- `mod_version` y `mod_framework=fabric` en `gradle.properties`. JAR: `<mod_id>-<mc>-<framework>-<loader>-<version>.jar`

## Commits (Conventional Commits)

`<tipo>[<ámbito>]: <descripción>` · tipos `feat fix refactor docs chore style perf test` · el mensaje incluye la versión (`v<version>`).

## Tags

Cada subida a CurseForge crea tag: beta `<mc>-<framework>-beta.X` · release `<mc>-<framework>-X.Y.Z`.

## Flujo por tarea

**0. Alcance** — si el mod tiene varias versiones, preguntar con la herramienta `question`: **"Todas"** o una versión. No asumir.

**1. Desarrollo**

```bash
git checkout minecraft/26.2/fabric-0.19.3/production
./gradlew.bat build
git add -A
git commit -m "feat: <descripción>

v<version>"
git push
```

**2. CurseForge** — solo si el usuario confirma:
- Bump `mod_version` en gradle.properties → `./gradlew.bat clean build`
- Release notes `docs/curseforge/versions/<version>.md` (HTML) + actualizar `CHANGELOG.md`
- Commit `chore: bump version to <version>` → tag `<mc>-<framework>-<version>` → push
- Subir JAR: `powershell -File ../../codex-docs/scripts/curseforge-upload.ps1` (desde este repo)
- Formato HTML de descripciones/changelog: `codex-docs/reference/CURSEFORGE.md`

**3. Release estable** — bump `X.Y.Z` + tag.

**4. Graphify** — tras cada push a remoto. Versión 0.9.12: **`build` no existe**, usar `extract` (1ª vez) o `update . --force` (tras cambios):

```bash
GRAPHIFY="C:\Users\llagu\AppData\Local\Packages\PythonSoftwareFoundation.Python.3.13_qbz5n2kfra8p0\LocalCache\local-packages\Python313\Scripts\graphify.exe"
"$GRAPHIFY" update . --force
git add graphify-out/ && git commit -m "chore: update knowledge graph" && git push
```

Leer siempre `GRAPH_REPORT.md`, nunca `graph.json`/`graph.html` (pesan >1MB). Sin copias fechadas de `graphify-out/`. Backend LLM: `codex-docs/reference/GRAPHIFY.md`.

## Buenas prácticas

- Un commit por cambio lógico · commit+push tras cada cambio funcional y de docs
- `clean build` antes del JAR final · versionar antes de CurseForge · CHANGELOG al día
- Graphify actualizado tras cada release · nomenclatura consistente · sin basura en repo (`nul`, `*_errors.txt`, `TEMPLATE_LICENSE.txt`) · `.gitignore` excluye `temp/` y `lib_ext/`
- README en inglés siempre actualizado · sin residuos de mod original (paquetes, clases, toml, lang, assets) · atribución de fork explícita (README, project_description, credits)
- **Port NeoForge → Fabric**: todo lo que toque `net.minecraft.client.*` va en `src/client/java`. No mezclar código client en `src/main/java`.

## Idioma

| Ámbito | Idioma |
|---|---|
| código, logs, commits | en-US |
| README.md | en-US |
| docs internas (docs/, CHANGELOG, este archivo) | es-ES |
| CurseForge | en-US |
