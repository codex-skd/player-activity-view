# Flujo de trabajo — Player Activity View (NeoForge)

> **Versión del workflow**: 1.1.0 (codex-docs)
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

## Tipografía

| Ámbito | Fuente |
|---|---|
| Código fuente, logs, nombres técnicos, commits, mensajes de consola | **Monospace** (`Consolas`, `JetBrains Mono`, `Cascadia Code`, `Fira Code`) |
| Documentación interna (README, CHANGELOG, docs/, WORKFLOW) | **Sans-serif** (`Segoe UI`, `Inter`, `Arial`) para cuerpo; **monospace** para código/rutas/comandos |
| CurseForge (descripciones, release notes) | Sans-serif por defecto de la plataforma; usar `<code>` para términos técnicos |

## Estructura del proyecto

```
<mod>/
├── build.gradle                        # Build con net.neoforged.moddev
├── gradle.properties                   # mod_id, mod_version, mod_group_id...
├── settings.gradle
├── src/
│   ├── main/
│   │   ├── java/<package>/             # Código fuente del mod
│   │   ├── resources/
│   │   │   ├── assets/<mod_id>/        # Texturas, shaders, lang, modelos...
│   │   │   │   └── icon.png           # Logo del mod (64x64, referenciado en neoforge.mods.toml)
│   │   │   ├── templates/
│   │   │   │   └── META-INF/
│   │   │   │       └── neoforge.mods.toml  # Template con placeholders ${...}
│   │   │   ├── META-INF/
│   │   │   │   └── accesstransformer.cfg
│   │   │   ├── <mod_id>.mixins.json
│   │   │   └── <mod_id>.png           # Logo del mod
│   │   └── templates/                 # (alternativa legacy, evitar)
│   │       └── META-INF/
│   │           └── neoforge.mods.toml
│   ├── main/java/<package>/...         # Código fuente
├── libs/                               # Dependencias reales del mod (JARs necesarios para compilar). Versionado.
├── lib_ext/                            # Librerías externas para análisis de la sesión. NO versionado (.gitignore).
├── temp/                               # Archivos temporales: investigaciones, prototipos, JARs extraídos, pruebas. NO versionado (.gitignore).
├── docs/
│   ├── WORKFLOW_PLAYER_ACTIVITY_VIEW_26-1-2.md  # Este documento
│   └── curseforge/                    # Documentación para publicación en CurseForge
│       ├── project_vars.md             # Variables del proyecto (ID, token, versiones)
│       ├── project_description.md      # Descripción del proyecto
│       └── versions/                   # Release notes por versión
│           ├── 0.0.0-beta.1.md
│           └── ...
├── CHANGELOG.md
├── README.md
├── graphify-out/                       # Knowledge Graph (generado por Graphify). Versionado en GitLab, NO va a GitHub (excluido por CI).
│   ├── graph.html
│   ├── GRAPH_REPORT.md
│   └── graph.json
└── .gitlab-ci.yml                      # CI/CD: publica código limpio a */main para mirror a GitHub
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
|---|---|
| `main` | Vacía. Solo contiene un commit inicial. No se usa para desarrollo |
| `minecraft/<mc-version>/neoforge-<neo-version>/production` | Rama de trabajo. Contiene todo el proyecto (código + docs/ + lib_ext/ + graphify-out/ + tokens reales) |
| `minecraft/<mc-version>/neoforge-<neo-version>/main` | Rama pública para mirror a GitHub. Solo contiene código fuente compilable. Se actualiza automáticamente vía CI/CD desde su hermana production |

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

Al crear una nueva rama `production` para una versión, su hermana `main` debe existir:

```bash
git checkout minecraft/26.1.2/neoforge-26.1.2.78/production
git checkout -b minecraft/26.1.2/neoforge-26.1.2.78/main
git push origin minecraft/26.1.2/neoforge-26.1.2.78/main
git checkout minecraft/26.1.2/neoforge-26.1.2.78/production
```

Esto solo se hace **una vez por versión**. A partir de ahí el CI/CD se encarga.

Configuración del mirror en GitLab:
1. **Settings → Repository → Mirroring repositories**
2. Añadir `https://<token>@github.com/tuusuario/<mod>.git`
3. Dirección: **Push**
4. Marcar **"Only mirror protected branches"**
5. Proteger ramas con patrón `minecraft/*/neoforge-*/main`
6. Desmarcar **"Keep divergent refs"**

> ⚠️ Las ramas `*/main` nunca se tocan manualmente. Solo el CI/CD escribe en ellas con force push.

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

Cada push a `production` dispara CI/CD que:
1. Deriva rama `main` hermana: `minecraft/X/N/production` → `minecraft/X/N/main`
2. Filtra solo archivos públicos (`src/`, `build.gradle`, `libs/`, etc.)
3. Sanitiza `gradle.properties` (placeholders)
4. Commitea con force push a `*/main`
5. El mirror de GitLab replica a GitHub

### .gitlab-ci.yml

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
    - MAIN_BRANCH=$(echo "$CI_COMMIT_BRANCH" | sed 's|/production$|/main|')
    - echo "Publishing to $MAIN_BRANCH"
    - git fetch origin "$MAIN_BRANCH" 2>/dev/null || true
    - git checkout "$MAIN_BRANCH" || git checkout --orphan "$MAIN_BRANCH"
    - git rm -rf --ignore-unmatch --quiet . 2>/dev/null || true
    - git checkout "$CI_COMMIT_SHA" -- src/ build.gradle settings.gradle gradle.properties gradlew gradlew.bat .gitignore README.md CHANGELOG.md libs/
    - sed -i 's/^mod_version=.*/mod_version=0.0.0/' gradle.properties
    - sed -i 's/^mod_group_id=.*/mod_group_id=com\.skd\.placeholder/' gradle.properties
    - sed -i 's/^mod_curseforge_project_id=.*/mod_curseforge_project_id=/' gradle.properties
    # Nota: el API token de CurseForge está en docs/curseforge/project_vars.md,
    # no en gradle.properties. No se sanitiza aquí porque GitLab es privado.
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
| `src/` | ✅ | ✅ |
| `build.gradle`, `settings.gradle` | ✅ | ✅ |
| `gradle.properties` | ✅ (tokens reales) | ✅ (placeholders) |
| `gradlew`, `gradlew.bat` | ✅ | ✅ |
| `README.md` | ✅ | ✅ |
| `CHANGELOG.md` | ✅ | ✅ |
| `libs/` | ✅ | ✅ |
| `.gitignore` | ✅ | ✅ |
| `docs/` | ✅ | ❌ |
| `lib_ext/` | ✅ | ❌ |
| `graphify-out/` | ✅ | ❌ |
| `build/` | ❌ (.gitignore) | ❌ |

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

```bash
"C:\Users\llagu\AppData\Local\Packages\PythonSoftwareFoundation.Python.3.13_qbz5n2kfra8p0\LocalCache\local-packages\Python313\Scripts\graphify.exe" build .

git add graphify-out/
git commit -m "chore: update knowledge graph"
git push
```

> **Nota**: El grafo permite a los asistentes de IA entender la arquitectura del mod sin leer todo el código fuente, reduciendo el consumo de tokens hasta 71×.

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
| 1.1.0 | 2026-07-21 | CI: eliminado `mod_curseforge_token` (nunca en gradle.properties). Script: displayName usa `mod_name`. Workflow: añadido paso de subida con el script compartido |
| 1.0.0 | 2026-07-21 | Versión inicial: estructura, naming, tipografía, CI/CD, Graphify, fork attribution, temp/ |
