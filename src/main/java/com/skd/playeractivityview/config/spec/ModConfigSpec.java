package com.skd.playeractivityview.config.spec;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Drop-in replacement for NeoForge's {@code ModConfigSpec} that persists values to a JSON file in
 * the Fabric config dir. Keeps the same {@code get()}/{@code set()} API used across the mod so the
 * rest of the code needs no changes. File is named {@code <fileName>.json}.
 */
public class ModConfigSpec {
    private static final Logger LOGGER = LoggerFactory.getLogger("player_activity_view/config");
    private static final Map<String, JsonObject> CACHE = new HashMap<>();

    private final String fileName;
    private final JsonObject defaults;

    private ModConfigSpec(String fileName, JsonObject defaults) {
        this.fileName = fileName;
        this.defaults = defaults;
    }

    private static Path path(String fileName) {
        return FabricLoader.getInstance().getConfigDir().resolve(fileName + ".json");
    }

    private static JsonObject load(String fileName) {
        return CACHE.computeIfAbsent(fileName, f -> {
            JsonObject obj = new JsonObject();
            try {
                Path p = path(f);
                if (Files.exists(p)) {
                    obj = JsonParser.parseString(Files.readString(p, StandardCharsets.UTF_8)).getAsJsonObject();
                }
            } catch (Exception e) {
                LOGGER.error("Failed to load config {}", f, e);
            }
            return obj;
        });
    }

    private static void save(String fileName) {
        JsonObject obj = CACHE.get(fileName);
        if (obj == null) return;
        try {
            Path p = path(fileName);
            Files.createDirectories(p.getParent());
            Files.writeString(p, new GsonBuilder().setPrettyPrinting().create().toJson(obj), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("Failed to save config {}", fileName, e);
        }
    }

    private static Object getValue(String fileName, String key, Object def) {
        JsonElement el = load(fileName).get(key);
        if (el == null || el.isJsonNull()) return def;
        try {
            if (def instanceof Boolean) return el.getAsBoolean();
            if (def instanceof Integer) return el.getAsInt();
            if (def instanceof Double) return el.getAsDouble();
            if (def instanceof String) return el.getAsString();
        } catch (Exception e) {
            return def;
        }
        return def;
    }

    private static void setValue(String fileName, String key, Object value) {
        JsonObject obj = load(fileName);
        if (value instanceof Boolean b) obj.addProperty(key, b);
        else if (value instanceof Integer i) obj.addProperty(key, i);
        else if (value instanceof Double d) obj.addProperty(key, d);
        else if (value instanceof String s) obj.addProperty(key, s);
        save(fileName);
    }

    public static class Builder {
        private final String fileName;
        private final JsonObject defaults = new JsonObject();

        public Builder(String fileName) {
            this.fileName = fileName;
        }

        public Builder comment(String comment) { return this; }

        public BooleanValue define(String key, boolean def) {
            defaults.addProperty(key, def);
            return new BooleanValue(fileName, key, def);
        }

        public IntValue defineInRange(String key, int def, int min, int max) {
            defaults.addProperty(key, def);
            return new IntValue(fileName, key, def);
        }

        public DoubleValue defineInRange(String key, double def, double min, double max) {
            defaults.addProperty(key, def);
            return new DoubleValue(fileName, key, def);
        }

        public ConfigValue<String> define(String key, String def) {
            defaults.addProperty(key, def);
            return new ConfigValue<>(fileName, key, def);
        }

        public ModConfigSpec build() {
            return new ModConfigSpec(fileName, defaults);
        }
    }

    public static class ConfigValue<T> {
        protected final String fileName;
        protected final String key;
        protected final T def;

        protected ConfigValue(String fileName, String key, T def) {
            this.fileName = fileName;
            this.key = key;
            this.def = def;
        }

        @SuppressWarnings("unchecked")
        public T get() { return (T) getValue(fileName, key, def); }
        public void set(T value) { setValue(fileName, key, value); }
    }

    public static class BooleanValue {
        private final String fileName;
        private final String key;
        private final boolean def;

        private BooleanValue(String fileName, String key, boolean def) {
            this.fileName = fileName;
            this.key = key;
            this.def = def;
        }

        public boolean get() { return (Boolean) getValue(fileName, key, def); }
        public void set(boolean value) { setValue(fileName, key, value); }
    }

    public static class IntValue {
        private final String fileName;
        private final String key;
        private final int def;

        private IntValue(String fileName, String key, int def) {
            this.fileName = fileName;
            this.key = key;
            this.def = def;
        }

        public int get() { return (Integer) getValue(fileName, key, def); }
        public void set(int value) { setValue(fileName, key, value); }
    }

    public static class DoubleValue {
        private final String fileName;
        private final String key;
        private final double def;

        private DoubleValue(String fileName, String key, double def) {
            this.fileName = fileName;
            this.key = key;
            this.def = def;
        }

        public double get() { return (Double) getValue(fileName, key, def); }
        public void set(double value) { setValue(fileName, key, value); }
    }
}
