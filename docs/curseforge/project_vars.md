# CurseForge — Variables del proyecto

## Proyecto

| Variable | Valor |
|----------|-------|
| `project_id` | `1608907` |
| `mod_id` | `player_activity_view` |
| `display_name` | `Player Activity View` (separado, no junto) |

## Tokens

| API | Token | Uso |
|-----|-------|-----|
| Upload | `ee776b0a-ee95-4850-b554-06be02a8657f` | Subir archivos JAR |
| Core (GET) | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` | Consultar datos del mod |

Autenticación Upload: cabecera `X-Api-Token`
Autenticación Core: cabecera `x-api-key`

> El proyecto de CurseForge (`1608907`) es **compartido entre versiones de Minecraft** (26.1.2, 26.2
> y ahora 1.21.1). Cada JAR se sube al mismo `project_id`; CurseForge las separa por las game
> versions declaradas en el fichero.

## Versión actual (rama 1.21.1)

| Variable | Valor |
|----------|-------|
| `minecraft_version` | `1.21.1` |
| `neo_version` (loader) | `21.1.249` |
| `framework` | `neoforge` |
| `java_version` | `21` |
| `mod_version` | `0.0.0-beta.1` |
| `environment` | `Client`, `Server` |

## Rama

```
minecraft/1.21.1/neoforge-21.1.249/production
```

## Tag

Formato: `<mc-version>-<framework>-<version>`
Ejemplo (primera beta del port): `1.21.1-neoforge-0.0.0-beta.1`

## Parámetros del upload

| Campo | Valor | Notas |
|-------|-------|-------|
| `displayName` | `Player Activity View (0.0.0-beta.1)` | Nombre visible: `display_name (mod_version)` — lo compone el script |
| `changelog` | HTML (contenido literal de `docs/curseforge/versions/<version>.md`) | No resumir ni modificar |
| `changelogType` | `html` | Obligatorio para que se vea bien |
| `releaseType` | `beta` | Primera beta del port a 1.21.1. Releases estables usarán `release` |
| `gameVersions` | `[9638, 9639, 11779, 10150]` | **IDs numéricos**, no nombres. Client + Server + 1.21.1 + NeoForge |

### IDs de `gameVersions` para 1.21.1

Verificados 2026-09-02 contra `sortableGameVersions` de ficheros 1.21.1 ya publicados de
`common_toolkit` (`GET /v1/mods/1638419/files`), y coincide con `armor_cosmetic` 1.21.1
(verificación previa 2026-08-31):

| Nombre | ID | gameVersionTypeID |
|--------|-----|--------|
| `Client` | `9638` | 75208 |
| `Server` | `9639` | 75208 |
| `1.21.1` | `11779` | 77784 |
| `NeoForge` | `10150` | 68441 |

> Ojo: la API devuelve **tres** entradas con nombre `1.21.1` (`11779` typeId 77784, `12735`
> typeId 1, `16115` typeId 615). La correcta para ficheros NeoForge es **`11779`** — es la que
> usan los ficheros 1.21.1 ya publicados. Verificar siempre contra un fichero real.

### IDs de `gameVersions` para 26.1.2 (rama 26.1.2, referencia)

| Nombre | ID | gameVersionTypeId |
|--------|-----|--------|
| `Client` | `9638` | 75208 |
| `Server` | `9639` | 75208 |
| `NeoForge` | `10150` | 68441 |
| `26.1.2` | `16082` | 83806 |

## Claves parseables por el script genérico

```
project_id = 1608907
api_token = ee776b0a-ee95-4850-b554-06be02a8657f
game_versions = 9638, 9639, 11779, 10150
release_type = beta
```

## Estructura del changelog (HTML)

```html
<h2>v1.0.0 - Titulo descriptivo</h2>

<h3>Fix</h3>
<ul>
<li><strong>Problema</strong>: descripcion con <code>codigo</code>.</li>
<li><strong>Otro</strong>: descripcion.</li>
</ul>

<h3>Technical Changes</h3>
<ul>
<li><code>Clase/metodo()</code> — descripcion.</li>
</ul>

<h3>Notes</h3>
<blockquote>Nota importante para servidores.</blockquote>

<hr>

<p><strong>JAR</strong>: <code>player_activity_view-1.21.1-neoforge-21.1.249-0.0.0-beta.1.jar</code></p>
```

## Subir archivo (JAR)

**No usar `urllib.request` de Python** — el body multipart hecho a mano (concatenando bytes de texto UTF-8 con los bytes crudos del JAR) provoca un `500 An unhandled exception occurred` del lado de CurseForge por razones no diagnosticadas. Usar el script genérico `codex-docs/scripts/curseforge-upload.ps1` (PowerShell con `System.Net.Http.MultipartFormDataContent`, binary-safe):

```powershell
powershell -File ../../codex-docs/scripts/curseforge-upload.ps1
```

## Verificar con GET

```bash
curl -s "https://api.curseforge.com/v1/mods/1608907/files/<FILE_ID>" \
  -H "x-api-key: $2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO"
```

## Changelog

```bash
curl -s "https://api.curseforge.com/v1/mods/1608907/files/<FILE_ID>/changelog" \
  -H "x-api-key: $2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO"
```

## Descripcion del proyecto

No hay endpoint API para actualizar la descripcion. Se edita manualmente desde la web de CurseForge pegando el HTML de `docs/curseforge/project_description.md`.

## Flujo completo

1. `./gradlew clean build`
2. Actualizar `docs/curseforge/versions/<version>.md` con HTML
3. Actualizar `CHANGELOG.md`
4. `git commit -m "fix: descripcion\n\nvX.Y.Z"` + `git push`
5. `git tag -a 1.21.1-neoforge-<version> -m "vX.Y.Z: descripcion"` + `git push origin <tag>`
6. Subir JAR a CurseForge con el script genérico
7. Verificar con GET que el changelog se vea bien
8. Liberar manualmente desde la web si es necesario
