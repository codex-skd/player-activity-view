# Contexto de trabajo — Mods Minecraft (NeoForge)

## Estructura del proyecto
```
mod/
├── build.gradle                    # Build con net.neoforged.moddev
├── gradle.properties               # mod_id, mod_version, etc.
├── src/main/
│   ├── java/com/skd/<modid>/      # Código fuente
│   ├── resources/
│   │   ├── assets/<modid>/        # Texturas, shaders, lang, etc.
│   │   ├── META-INF/accesstransformer.cfg
│   │   └── <modid>.mixins.json
│   └── templates/META-INF/neoforge.mods.toml
├── curseforge/
│   ├── project_description.md      # Descripción del proyecto para CurseForge
│   └── versions/                   # Descripciones de cada versión (añadir solo cuando se sube)
└── CHANGELOG.md
```

## Flujo de trabajo
1. **Fork de mod existente**: Extraer jar de `lib_ext/`, decompilar con vineflower, reescribir todo el código bajo nuevo package y mod ID, eliminar dependencias externas.
2. **Versiones**: `gradle.properties` → `mod_version=0.0.0-beta.X`
3. **Subida a CurseForge**: Antes de subir, añadir archivo `curseforge/versions/X.X.X-beta.X.md` con la descripción de esa versión.
4. **Commits**: `v0.0.0-beta.X: mensaje descriptivo`
5. **Registro de cambios**: `CHANGELOG.md` siempre actualizado.

## Convenciones de nombres
- Mod ID: snake_case (ej: `player_activity_view`)
- Package: `com.skd.<modid>` (ej: `com.skd.playeractivityview`)
- Display name: Capitalized (ej: `Player Activity View`)
- Assets: `assets/<modid>/`

## Comandos útiles
```bash
./gradlew.bat build                    # Compilar y empaquetar
./gradlew.bat clean build              # Limpiar y compilar
./gradlew.bat runClient                # Ejecutar cliente
```

## Al iniciar nueva sesión
1. Leer `gradle.properties` para conocer mod_id y versión actual
2. Leer `build.gradle` para versión de NeoForge
3. Leer `src/main/templates/META-INF/neoforge.mods.toml` para metadatos
