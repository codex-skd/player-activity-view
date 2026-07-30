# Flujo de trabajo — Player Activity View (NeoForge)

> **Versión del workflow**: 1.5.0 (codex-docs)
> Este archivo pertenece al proyecto **Player Activity View**. Cada proyecto tiene su propio `WORKFLOW_<MOD_ID>_<MC-VERSION>.md`.
> No es un archivo central ni template compartido. Los cambios aquí solo afectan a este proyecto.
> Para actualizar este workflow, revisar la última versión en `codex-docs/WORKFLOW_GENERIC.md`.

## Convenciones de nomenclatura

| Convención | Uso | Ejemplo |
|---|---|---|
| **snake_case** | `mod_id` en gradle.properties, assets/, packages Java | `player_activity_view` |
| **PascalCase** | Clases Java principales | `PlayerActivityView` |
| **camelCase** | Variables, métodos, config keys | `playerActivityView` |
| **Title Case** | Display name en README, CHANGELOG, docs, CurseForge | `Player Activity View` |

### Ficheros de documentación

| Fichero | Formato | Ejemplo |
|---|---|---|
| WORKFLOW | `WORKFLOW_<MOD_ID>_<MC-VERSION>.md` | `WORKFLOW_PLAYER_ACTIVITY_VIEW_26-1-2.md` |
| CHANGELOG | `CHANGELOG.md` (fijo) | `CHANGELOG.md` |
| README | `README.md` (fijo) | `README.md` |

> El nombre del WORKFLOW incluye el `mod_id` y la versión de Minecraft para identificar inequívocamente a qué proyecto y versión pertenece.

Reglas:
- `mod_id` en `gradle.properties` debe coincidir con el nombre del directorio del proyecto
- El display name en `README.md` y `CHANGELOG.md` debe estar en **Title Case**
- Las clases Java principales deben seguir el naming del `mod_id` pero en **PascalCase**
- Las config keys en camelCase: `playerActivityView.enableFeature`

## Organización en el workspace

Todos los mods siguen esta estructura en el directorio raíz (`Mods_Minecraft/`), tengan una o varias versiones de Minecraft:

```
<mod_id>/                      # Único repositorio Git (un solo .git/)
└── <minecraft_version>/       # Solo existe en su rama: minecraft/<mc-version>/neoforge-<neo-version>/production
    ├── src/
    ├── docs/
    └── ...
```

Cada versión de Minecraft es una **rama** dentro del mismo repositorio. La carpeta de cada versión **solo existe en su propia rama** — no hay rastro de otras versiones al cambiar de rama.

Ejemplo para este proyecto:

```
player_activity_view/          # Un solo repositorio Git
└── 26.1.2/                    # Rama: minecraft/26.1.2/neoforge-26.1.2.78/production
    ├── gradle.properties → minecraft_version=26.1.2
    └── ...
```

**Reglas:**
- `<mod_id>/` es el repositorio Git, contiene el `.git/`
- Cada `<minecraft_version>/` es una subcarpeta **sin `.git/` propio**
- Cada versión tiene su propia rama `minecraft/<mc-version>/neoforge-<neo-version>/production`
- Cada rama solo contiene los archivos de su versión. Las carpetas de otras versiones **no existen** en esa rama
- El `mod_id` en `gradle.properties` debe coincidir con la carpeta padre
- El nombre del workflow sigue el patrón `WORKFLOW_<MOD_ID>_<MC-VERSION>.md`

## Tipografía

| Ámbito | Fuente |
|---|---|
| Código fuente, logs, nombres técnicos, commits, mensajes de consola | **Monospace** (`Consolas`, `JetBrains Mono`, `Cascadia Code`, `Fira Code`) |
| Documentación interna (README, CHANGELOG, docs/, WORKFLOW) | **Sans-serif** (`Segoe UI`, `Inter`, `Arial`) para cuerpo; **monospace** para código/rutas/comandos |
| CurseForge (descripciones, release notes) | Sans-serif por defecto de la plataforma; usar `<code>` para términos técnicos |

## Estructura del proyecto

```
<mod>/                            # Raíz del repositorio
├── .gitlab-ci.yml               # CI/CD en raíz del repo
├── <minecraft_version>/         # Código fuente y docs de la versión
│   ├── build.gradle             # Build con net.neoforged.moddev
│   ├── gradle.properties        # mod_id, mod_version, mod_group_id...
│   ├── settings.gradle
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/<package>/  # Código fuente del mod
│   │   │   └── resources/
│   │   │       ├── assets/<mod_id>/
│   │   │       ├── META-INF/
│   │   │       ├── <mod_id>.mixins.json
│   │   │       └── <mod_id>.png
│   ├── libs/                    # Dependencias reales. Versionado.
│   ├── lib_ext/                 # Librerías externas. NO versionado.
│   ├── temp/                    # Archivos temporales. NO versionado.
│   ├── docs/
│   │   ├── WORKFLOW_<MOD_ID>_<MC-VERSION>.md
│   │   └── curseforge/
│   │       ├── project_vars.md
│   │       ├── project_description.md
│   │       └── versions/
│   ├── CHANGELOG.md
│   ├── README.md
│   └── graphify-out/            # Knowledge Graph. NO va a GitHub.
```

### Archivos de CurseForge

| Archivo | Propósito |
|---|---|
| `docs/curseforge/project_vars.md` | Variables específicas del proyecto (project ID, token, versiones) |
| `docs/curseforge/project_description.md` | Descripción completa del proyecto (qué hace, características, requisitos) |
| `docs/curseforge/versions/<version>.md` | Release notes de cada versión que se sube a CurseForge. Solo se agrega cuando se va a publicar esa versión |

Las variables de cada proyecto (project ID, API token, versiones de Minecraft/NeoForge/Java) se documentan en `docs/curseforge/project_vars.md`. No duplicar aquí.

> El API token de CurseForge es el mismo para todos los mods (token de cuenta, no de proyecto). Se copia en cada `project_vars.md` individualmente.

### Formato de descripciones CurseForge

CurseForge admite **Markdown y HTML** en las descripciones y release notes. Usamos ambos porque:
- Se versiona junto al código en el repositorio
- Es portátil (funciona en GitHub, GitLab, etc.)
- El HTML permite control preciso sobre espaciado, alineación y estructura visual
- El Markdown es más limpio para listas, tablas y código

Usamos HTML tanto para la **descripción general del proyecto** (`project_description.md`) como para las **release notes** (`versions/<version>.md`), ya que el contenido de estos archivos se sube directamente a CurseForge.

#### Estructura de la descripción general

```
Header:    Título principal (h1 centrado) + tagline
           Separador
Cuerpo:    Overview en párrafos (h2)
           Features con h3 + párrafo descriptivo cada una
           Tabla de requisitos
           Lista de uso
           Separador
Footer:    Créditos
           Logo centrado + enlace web + eslogan
```

#### Elementos HTML disponibles

| Elemento | Uso |
|---|---|
| `<h1 align="center">` | Título principal centrado |
| `<h2>` | Secciones del cuerpo |
| `<h3>` | Subsecciones (cada feature) |
| `<p>` | Párrafos con espaciado natural |
| `<br>` | Saltos de línea para separar bloques |
| `<hr>` | Separadores visuales entre secciones |
| `<table>` | Datos estructurados (requisitos) |
| `<ol>` / `<ul>` | Listas ordenadas y sin orden |
| `<img>` | Logos e imágenes |
| `<a>` | Enlaces externos |
| `<code>` | Comandos y rutas técnicas |
| `<blockquote>` | Notas destacadas |
| `<strong>` / `<em>` | Negritas y cursivas |
| `<p align="center">` | Bloques centrados (footer) |

#### Buenas prácticas

- **Respetar la estructura**: Header → Cuerpo → Footer, con separadores visuales
- **Interlineado**: Usar `<br>` entre bloques, no acumular párrafos seguidos
- **Títulos diferenciados**: h1 muy visible (centrado), h2 para secciones, h3 para cada feature
- **Logo en el footer**: Centrado, con enlace a la web y eslogan
- **Sin carácter retroactivo**: Solo aplicamos el formato a nuevas versiones; las existentes no se modifican
- **Idioma**: CurseForge en **inglés** (en-US) — plataforma global

#### Formato del changelog

El changelog se envía en formato **HTML**, no Markdown.

| Campo | Valor |
|---|---|
| `changelogType` | `html` |
| `changelog` | Código HTML con `<h2>`, `<h3>`, `<ul>/<li>`, `<p>`, `<strong>`, `<code>`, `<blockquote>` |

**Regla importante**: El valor del campo `changelog` en la subida a CurseForge debe ser **exactamente el contenido del archivo** `docs/curseforge/versions/<version>.md`. No resumir, no modificar, no acortar.

#### Ejemplo de estructura HTML para release notes

```html
<h2>v0.0.0-beta.X - Titulo descriptivo</h2>

<h3>Fix</h3>
<ul>
<li><strong>Issue</strong>: description with <code>code</code>.</li>
</ul>

<h3>Technical Changes</h3>
<ul>
<li><code>Class.method()</code> — description.</li>
</ul>
```

#### Elementos HTML permitidos

| Elemento | Uso |
|---|---|
| `<h2>` | Título principal de la versión |
| `<h3>` | Subsecciones (Fix, Technical Changes, Notes) |
| `<ul><li>` | Listas de puntos |
| `<strong>` | Negritas para resaltar |
| `<code>` | Código o nombres técnicos |
| `<blockquote>` | Notas importantes para servidores |
| `<hr>` | Separador |
| `<p>` | Párrafos |

---

## Ramas

### Estructura

| Rama | Propósito |
|---|---|---|
| `main` | ~~Eliminar.~~ Ya no existe. La default ahora es `*/production` |
| `minecraft/<mc-version>/neoforge-<neo-version>/production` | **Rama por defecto**. Rama de trabajo con todo el proyecto: código, docs/, lib_ext/, graphify-out/, tokens reales |
| `minecraft/<mc-version>/neoforge-<neo-version>/main` | **Rama protegida**. Recibe el mirror a GitHub. Solo contiene código fuente compilable. Se actualiza vía CI/CD con force push |

### Ejemplos

| Rama | Propósito |
|---|---|
| `minecraft/26.1.2/neoforge-26.1.2.78/production` | Trabajo diario en Minecraft 26.1.2 |
| `minecraft/26.1.2/neoforge-26.1.2.78/main` | Código público para GitHub (misma versión) |

### Esquema de publicación

```
GitLab (privado)                         GitHub (público)
─────────────────────                    ──────────────────────
minecraft/X/N/production
  (código + docs/ + lib_ext/             minecraft/X/N/main
   + graphify-out/ + tokens)              (solo código + libs/
       │                                   + README + placeholders)
       │  CI/CD: filtra, sanitiza,
       │  commitea con force push
       ▼  a la rama */main hermana
  minecraft/X/N/main ──────────────────→ minecraft/X/N/main
       │         (mirror push automático)
       ▼
    GitHub: minecraft/X/N/main
    (espejo exacto de GitLab)
```

Cada versión de Minecraft/NeoForge tiene su propio par `production` ↔ `main`. El mirror de GitLab replica **todas** las ramas `*/main` a GitHub automáticamente.

### Inicialización única de cada rama `*/main`

Cada vez que se crea una rama `production` para una nueva versión, la agente (sesión) debe crear su hermana `main` inmediatamente después. Sin este paso, el CI/CD fallará (ya no la crea automáticamente).

> La rama `main` raíz (vacía) puede y debe eliminarse. La rama por defecto del repositorio debe ser `*/production`. Si GitLab no permite borrar la rama por defecto, cámbiala primero a `*/production` en Settings → Repository → Default branch.

**Responsabilidades:**

| Rol | Acción |
|---|---|
| **Agente (sesión)** | Crear la rama `*/main` desde `*/production` y pushearla |
| **Operador (desarrollador)** | Cambiar rama por defecto a `*/production` y eliminar `main` raíz. También proteger ramas `*/main` y configurar mirror a GitHub |

**1. La agente crea la rama `*/main`** (al crear `production`):

```bash
# Ejemplo: para minecraft/26.1.2/neoforge-26.1.2.78/production
git checkout minecraft/26.1.2/neoforge-26.1.2.78/production
git checkout -b minecraft/26.1.2/neoforge-26.1.2.78/main
git push origin minecraft/26.1.2/neoforge-26.1.2.78/main
git checkout minecraft/26.1.2/neoforge-26.1.2.78/production
```

Esto solo se hace **una vez por versión**. A partir de ahí el CI/CD mantiene `*/main` actualizada con force push automático.

**2. El operador configura el repositorio** (una sola vez por repo):

1. **Settings → Repository → Default branch**: cambiar a `minecraft/*/neoforge-*/production` (la rama de trabajo, la que se ve al clonar)
2. **Settings → Repository → Branches**: eliminar `main` raíz (si existe)
3. **Settings → Repository → Protected branches**: proteger `minecraft/*/neoforge-*/main` con force push permitido (es la rama del mirror, necesita protección)
4. **Settings → Repository → Mirroring repositories**: configurar mirror a GitHub

> ⚠️  Las ramas `*/main` nunca se tocan manualmente después de creadas. Solo el CI/CD escribe en ellas con force push.

---

## Versionado

### Esquema

| Estado | Formato | Ejemplos |
|---|---|---|
| Beta / desarrollo | `0.0.0-beta.X` | `0.0.0-beta.1`, `0.0.0-beta.2` |
| Release estable | `X.Y.Z` (SemVer) | `1.0.0`, `1.2.3`, `2.0.0` |

**SemVer**: `MAJOR` (breaking), `MINOR` (nuevas funcionalidades), `PATCH` (bug fixes).

### ¿Cuándo incrementar versión?

- Cada vez que se hace un commit con cambios funcionales (no solo documentación)
- Al preparar una subida a CurseForge

La versión se define en `gradle.properties`:
```properties
mod_version=0.0.0-beta.1
```

### Nombre del JAR

Formato: `<mod_id>-<minecraft_version>-<framework>-<mod_version>.jar`

| Ejemplo | Significado |
|---|---|
| `player_activity_view-26.1.2-neoforge-0.0.0-beta.7.jar` | NeoForge 26.1.2, beta 7 |

```groovy
base {
    archivesName = "${mod_id}-${minecraft_version}-neoforge"
}
```

---

## Commits (Conventional Commits)

```
<tipo>[<ámbito>]: <descripción>

[body opcional]
```

| Tipo | Uso |
|---|---|
| `feat` | Nueva funcionalidad |
| `fix` | Corrección de bug |
| `refactor` | Refactorización sin cambio funcional |
| `docs` | Documentación |
| `chore` | Tareas de mantenimiento (build, CI, etc.) |
| `style` | Cambios de formato |
| `perf` | Mejora de rendimiento |
| `test` | Añadir o modificar tests |

El mensaje del commit **debe incluir la versión** en el formato `v<version>`:

```
git commit -m "feat: add player idle detection

v0.0.0-beta.1"
```

---

## Tags (GitLab)

| Estado | Formato | Ejemplo |
|---|---|---|
| Beta | `<mc-version>-neoforge-beta.X` | `26.1.2-neoforge-beta.7` |
| Release | `<mc-version>-neoforge-X.Y.Z` | `26.1.2-neoforge-1.0.0` |

```bash
git tag -a 26.1.2-neoforge-beta.7 -m "v0.0.0-beta.7: description"
git push origin 26.1.2-neoforge-beta.7
```

---

## Publicación a GitHub (CI/CD)

Cada vez que se hace push a una rama `production`, GitLab CI ejecuta automáticamente un pipeline que:
1. Detecta desde qué rama `production` se disparó
2. Deriva la rama `main` hermana: `minecraft/X/N/production` → `minecraft/X/N/main`
3. Filtra solo los archivos públicos (`src/`, `build.gradle`, `settings.gradle`, `libs/`, etc.)
4. Sanitiza `gradle.properties` (reemplaza tokens reales con placeholders)
5. Commitea con force push a la rama `*/main` hermana
6. El mirror de GitLab replica esa rama a GitHub automáticamente

### Variables de CI/CD (grupo GitLab)

Estas variables se configuran en **Settings → CI/CD → Variables** a nivel de grupo `stalking-dragons/minecraft`. Así todos los proyectos del grupo tienen acceso automático sin repetirlas:

| Variable | Propósito |
|---|---|
| `GITLAB_PUSH_TOKEN` | Token de GitLab con permisos de API y push. Usado por el CI para hacer force push a `*/main` |
| `GH_USERNAME` | Usuario de GitHub (`santiagolosadaborrajo`) |
| `GH_TOKEN` | Token de GitHub con permisos de push a repos. Usado para autenticar el mirror |

> Los tokens personales del desarrollador se almacenan localmente en `codex-docs/secrets.md` (excluido vía `.gitignore`). No se suben al repositorio.

### Requisito previo

Antes de que el CI/CD funcione, la rama `main` hermana debe existir al menos una vez en el remoto. Ver [Inicialización única de cada rama `*/main`](#inicialización-única-de-cada-rama-main).

### .gitlab-ci.yml

Crear en la raíz del proyecto:

```yaml
image: alpine:latest

variables:
  GIT_DEPTH: 0

stages:
  - publish

publish-public:
  stage: publish
  only:
    - /^minecraft\/.*\/.*\/production$/
  except:
    - main
  script:
    - apk add --no-cache git
    - git config user.email "ci@mods-minecraft.dev"
    - git config user.name "Mods Minecraft CI"

    # Derivar la rama main: minecraft/X/N/production → minecraft/X/N/main
    - MAIN_BRANCH=$(echo "$CI_COMMIT_BRANCH" | sed 's|/production$|/main|')
    - echo "Publishing to $MAIN_BRANCH"

    # Obtener la rama main hermana. Si no existe, falla — el agente debe crearla manualmente.
    - |
      if ! git fetch origin "$MAIN_BRANCH" 2>/dev/null; then
        echo "ERROR: $MAIN_BRANCH no existe. Créala desde production primero."
        exit 1
      fi
    - git checkout "$MAIN_BRANCH"

    # Limpiar y copiar solo archivos públicos desde production
    - git rm -rf --ignore-unmatch --quiet . 2>/dev/null || true

    # Archivos obligatorios
    - git checkout "$CI_COMMIT_SHA" -- src/ build.gradle settings.gradle gradle.properties gradlew gradlew.bat .gitignore README.md CHANGELOG.md

    # Archivos opcionales
    - git checkout "$CI_COMMIT_SHA" -- libs/ 2>/dev/null || true

    # Sanitizar secrets en gradle.properties
    - sed -i 's/^mod_version=.*/mod_version=0.0.0/' gradle.properties
    - sed -i 's/^mod_group_id=.*/mod_group_id=com\.skd\.placeholder/' gradle.properties
    - sed -i 's/^mod_curseforge_project_id=.*/mod_curseforge_project_id=/' gradle.properties
    # Nota: el API token de CurseForge está en docs/curseforge/project_vars.md,
    # no en gradle.properties. No se sanitiza aquí porque GitLab es privado.

    # Commit y push (force push a la rama main hermana)
    - git add -A
    - |
      if ! git diff --cached --quiet; then
        git commit -m "chore: sync public code from ${CI_COMMIT_SHORT_SHA}"
        git push --force "https://oauth2:${GITLAB_PUSH_TOKEN}@${CI_SERVER_HOST}/${CI_PROJECT_PATH}.git" HEAD:"$MAIN_BRANCH"
      else
        echo "No changes to publish"
      fi
```

### Archivos que pasan a GitHub

| Archivo/Carpeta | GitLab production | GitLab */main → GitHub |
|---|---|---|
| `<version>/src/` | ✅ | ✅ |
| `<version>/build.gradle`, `<version>/settings.gradle` | ✅ | ✅ |
| `<version>/gradle.properties` | ✅ (tokens reales) | ✅ (placeholders) |
| `<version>/gradlew`, `<version>/gradlew.bat` | ✅ | ✅ |
| `<version>/README.md` | ✅ | ✅ |
| `<version>/CHANGELOG.md` | ✅ | ✅ |
| `<version>/libs/` | ✅ | ✅ |
| `<version>/.gitignore` | ✅ | ✅ |
| `<version>/docs/` | ✅ | ❌ |
| `<version>/lib_ext/` | ✅ | ❌ |
| `<version>/graphify-out/` | ✅ | ❌ (excluido por CI) |
| `<version>/build/` | ❌ (.gitignore) | ❌ |

---

## Flujo completo (paso a paso)

### 1. Desarrollo

```bash
git checkout minecraft/26.1.2/neoforge-26.1.2.78/production

# Hacer cambios en el código
./gradlew.bat build

git add -A
git commit -m "feat: add typing indicator particles

v0.0.0-beta.2"
git push
```

### 2. Copiar a instancia de pruebas

```bash
./gradlew.bat clean build

# PREGUNTAR: "¿Copiar el JAR a la instancia de pruebas?"
# cp build/libs/<mod_id>-<minecraft_version>-<framework>-<version>.jar /ruta/a/la/instancia/mods/
# rm /ruta/a/la/instancia/mods/<mod_id>-<minecraft_version>-<framework>-<version-anterior>.jar
```

### 3. Probar en instancia

- El usuario abre Minecraft y verifica que funcione
- Si hay errores, se vuelve a Desarrollo (paso 1)
- Si funciona, se continúa

### 4. Preparar versión para CurseForge

```bash
# PREGUNTAR: "¿Subir esta versión a CurseForge?"

# Actualizar versión en gradle.properties
# mod_version=0.0.0-beta.3

./gradlew.bat clean build

# Crear release notes: docs/curseforge/versions/0.0.0-beta.3.md
# Actualizar CHANGELOG.md

git add -A
git commit -m "chore: bump version to 0.0.0-beta.3"

git tag -a 26.1.2-neoforge-beta.3 -m "v0.0.0-beta.3: Bugfix release"
git push origin 26.1.2-neoforge-beta.3

# PREGUNTAR: "¿Subir JAR a CurseForge ahora?"
# JAR en: build/libs/<mod_id>-<minecraft_version>-<framework>-<version>.jar

# 9. Subir a CurseForge usando el script compartido
#    powershell -File ../codex-docs/scripts/curseforge-upload.ps1
#
#    Este script lee project_vars.md (project_id, api_token) y gradle.properties
#    (mod_id, mod_name, mod_version) y sube el JAR automáticamente.
#    Es el mismo script para todos los mods, vive en codex-docs.
```

### 5. Release estable

```bash
# gradle.properties → mod_version=1.0.0
git commit -m "chore: bump version to 1.0.0"
git tag -a 26.1.2-neoforge-1.0.0 -m "v1.0.0: First stable release"
git push origin 26.1.2-neoforge-1.0.0
```

### 6. Actualizar Knowledge Graph (Graphify)

**`build` no es un comando válido** (versión instalada: 0.9.12) — usar `extract` (primera vez) o `update` (refrescos, sin LLM):

```bash
GRAPHIFY="C:\Users\llagu\AppData\Local\Packages\PythonSoftwareFoundation.Python.3.13_qbz5n2kfra8p0\LocalCache\local-packages\Python313\Scripts\graphify.exe"

# Si graphify-out/ NO existe todavía (primera vez): extracción completa con LLM
"$GRAPHIFY" extract .

# Si graphify-out/ YA existe (actualización tras cambios de código): más barato, sin LLM
"$GRAPHIFY" update . --force

git add graphify-out/
git commit -m "chore: update knowledge graph"
git push
```

**Nunca crear copias fechadas** de `graphify-out/` (p. ej. `graphify-out/2026-07-27/`) — el historial ya vive en `git log -- graphify-out/`.

**Qué archivo leer**: siempre `GRAPH_REPORT.md` (resumen legible). Nunca `graph.json`/`graph.html` directamente como contexto — `graph.json` puede pesar >1MB y anula el ahorro de tokens.

> **Nota**: El grafo permite a los asistentes de IA entender la arquitectura del mod sin leer todo el código fuente, reduciendo el consumo de tokens hasta 71×. Ver la sección de Graphify en `codex-docs/WORKFLOW_GENERIC.md` para el backend Ollama local usado en `extract`/`label`.

---

## Buenas prácticas

- **Un commit por cambio lógico**: no acumular múltiples cambios en un solo commit
- **Commit y push después de cada cambio funcional**: no esperar a tener todo terminado
- **Cualquier cambio en documentación debe committearse y pushearse inmediatamente**: los archivos de `docs/` deben reflejar siempre el estado actual del proyecto
- **Versionar antes de subir a CurseForge**: el tag debe apuntar al commit exacto del JAR que se sube
- **CHANGELOG.md siempre actualizado**: reflejar todos los cambios de cada versión
- **Siempre hacer `clean build` antes de generar el JAR final**: la caché de Gradle puede dejar artefactos obsoletos o corruptos
- **Graphify**: mantener el knowledge graph actualizado tras cada release
- **Nomenclatura consistente**: no mezclar snake_case, PascalCase, camelCase o Title Case en contextos donde no corresponde
- **Sin archivos basura**: eliminar `nul`, `TEMPLATE_LICENSE.txt`, `errors.txt` y otros artefactos temporales antes de commitear
- **README.md actualizado y en inglés**: puerta de entrada del proyecto en GitHub
- **Sin residuos del mod original**: nombres de paquetes, clases, referencias en toml, lang/, assets
- **Atribución de fork**: si el mod es un fork, indicarlo en README, descripción CurseForge y neoforge.mods.toml

## Idioma

| Ámbito | Idioma |
|---|---|
| Código fuente, logs, nombres técnicos, commits | **Inglés** (en-US) |
| README.md | **Inglés** (en-US) — puerta de entrada pública (GitHub) |
| Documentación interna (docs/, CHANGELOG, WORKFLOW) | **Castellano** (es-ES) |
| CurseForge (descripción del proyecto, release notes) | **Inglés** (en-US) |

---

## Historial de versiones del workflow

| Versión | Fecha | Cambios |
|---|---|---|
| 1.5.0 | 2026-07-30 | Sincronizado con WORKFLOW_GENERIC.md 1.12.0: corregido comando de Graphify (`build` no existe, usar `extract`/`update --force`), regla de no crear copias fechadas, qué archivo leer (`GRAPH_REPORT.md`) |
| 1.4.0 | 2026-07-23 | Añadida sección de organización en workspace con estructura `<mod_id>/<mc-version>/` |
| 1.2.7 | 2026-07-23 | Versión actual: Ramas reescritas con roles, CI con variables de grupo, libs/ opcional, sin orphan creation |
| 1.1.0 | 2026-07-21 | CI: eliminado `mod_curseforge_token` (nunca en gradle.properties). Script: displayName usa `mod_name`. Workflow: añadido paso de subida con el script compartido |
| 1.0.0 | 2026-07-21 | Versión inicial: estructura, naming, tipografía, CI/CD, Graphify, fork attribution, temp/ |
