package com.skd.playeractivityview.config;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class ServerConfigSyncHelper {
    private static ServerConfigSyncHelper instance;
    private final Map<String, Supplier<Object>> getters = new HashMap<>();
    private final Map<String, Consumer<Object>> setters = new HashMap<>();
    private final Map<String, Class<?>> types = new HashMap<>();

    public static ServerConfigSyncHelper getInstance() {
        if (instance == null) {
            instance = new ServerConfigSyncHelper();
            instance.init();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    private <T> void put(String name, Supplier<T> getter, Consumer<T> setter, Class<T> type) {
        getters.put(name, () -> getter.get());
        setters.put(name, v -> setter.accept((T)v));
        types.put(name, type);
    }

    private void init() {
        put("DYNAMIC_GUI_USE_OLD_SIMPLE", () -> ServerSyncedConfig.DYNAMIC_GUI_USE_OLD_SIMPLE.get(), v -> ServerSyncedConfig.DYNAMIC_GUI_USE_OLD_SIMPLE.set(v), Boolean.class);
        put("DYNAMIC_GUI_TICK_SEND_RATE", () -> ServerSyncedConfig.DYNAMIC_GUI_TICK_SEND_RATE.get(), v -> ServerSyncedConfig.DYNAMIC_GUI_TICK_SEND_RATE.set(v), Integer.class);
        put("DYNAMIC_GUI_DONT_SEND_CONSTANT_UPDATES", () -> ServerSyncedConfig.DYNAMIC_GUI_DONT_SEND_CONSTANT_UPDATES.get(), v -> ServerSyncedConfig.DYNAMIC_GUI_DONT_SEND_CONSTANT_UPDATES.set(v), Boolean.class);
        put("DYNAMIC_GUI_BLUR_LEVEL", () -> ServerSyncedConfig.DYNAMIC_GUI_BLUR_LEVEL.get(), v -> ServerSyncedConfig.DYNAMIC_GUI_BLUR_LEVEL.set(v), Integer.class);
        put("DYNAMIC_GUI_SIZE_RADIUS", () -> ServerSyncedConfig.DYNAMIC_GUI_SIZE_RADIUS.get(), v -> ServerSyncedConfig.DYNAMIC_GUI_SIZE_RADIUS.set(v), Double.class);
        put("DYNAMIC_GUI_SHOW_ENTIRE_SCREEN", () -> ServerSyncedConfig.DYNAMIC_GUI_SHOW_ENTIRE_SCREEN.get(), v -> ServerSyncedConfig.DYNAMIC_GUI_SHOW_ENTIRE_SCREEN.set(v), Boolean.class);
        put("DYNAMIC_GUI_DISABLE_BACKGROUND", () -> ServerSyncedConfig.DYNAMIC_GUI_DISABLE_BACKGROUND.get(), v -> ServerSyncedConfig.DYNAMIC_GUI_DISABLE_BACKGROUND.set(v), Boolean.class);
        put("SHOW_ITEMS_TRANSFERRED", () -> ServerSyncedConfig.SHOW_ITEMS_TRANSFERRED.get(), v -> ServerSyncedConfig.SHOW_ITEMS_TRANSFERRED.set(v), Boolean.class);
        put("DISTANCE_REQUIRED_TO_SHOW_GUI_INFO", () -> ServerSyncedConfig.DISTANCE_REQUIRED_TO_SHOW_GUI_INFO.get(), v -> ServerSyncedConfig.DISTANCE_REQUIRED_TO_SHOW_GUI_INFO.set(v), Integer.class);
        put("SHOW_IDLE_STATES_IN_PLAYER_LIST", () -> ServerSyncedConfig.SHOW_IDLE_STATES_IN_PLAYER_LIST.get(), v -> ServerSyncedConfig.SHOW_IDLE_STATES_IN_PLAYER_LIST.set(v), Boolean.class);
        put("SHOW_IDLE_STATES_IN_PLAYER_ABOVE_HEAD", () -> ServerSyncedConfig.SHOW_IDLE_STATES_IN_PLAYER_ABOVE_HEAD.get(), v -> ServerSyncedConfig.SHOW_IDLE_STATES_IN_PLAYER_ABOVE_HEAD.set(v), Boolean.class);
        put("SHOW_PLAYER_ANIMATIONS", () -> ServerSyncedConfig.SHOW_PLAYER_ANIMATIONS.get(), v -> ServerSyncedConfig.SHOW_PLAYER_ANIMATIONS.set(v), Boolean.class);
        put("SHOW_PLAYER_ANIMATION_TYPING", () -> ServerSyncedConfig.SHOW_PLAYER_ANIMATION_TYPING.get(), v -> ServerSyncedConfig.SHOW_PLAYER_ANIMATION_TYPING.set(v), Boolean.class);
        put("SHOW_PLAYER_ANIMATION_IDLE", () -> ServerSyncedConfig.SHOW_PLAYER_ANIMATION_IDLE.get(), v -> ServerSyncedConfig.SHOW_PLAYER_ANIMATION_IDLE.set(v), Boolean.class);
        put("SHOW_PLAYER_ANIMATION_GUI", () -> ServerSyncedConfig.SHOW_PLAYER_ANIMATION_GUI.get(), v -> ServerSyncedConfig.SHOW_PLAYER_ANIMATION_GUI.set(v), Boolean.class);
        put("SHOW_PLAYER_ACTIVE_NON_CHAT_GUI", () -> ServerSyncedConfig.SHOW_PLAYER_ACTIVE_NON_CHAT_GUI.get(), v -> ServerSyncedConfig.SHOW_PLAYER_ACTIVE_NON_CHAT_GUI.set(v), Boolean.class);
        put("SHOW_PLAYER_ACTIVE_CHAT_GUI", () -> ServerSyncedConfig.SHOW_PLAYER_ACTIVE_CHAT_GUI.get(), v -> ServerSyncedConfig.SHOW_PLAYER_ACTIVE_CHAT_GUI.set(v), Boolean.class);
        put("SCREEN_TYPING_VISIBLE", () -> ServerSyncedConfig.SCREEN_TYPING_VISIBLE.get(), v -> ServerSyncedConfig.SCREEN_TYPING_VISIBLE.set(v), Boolean.class);
        put("PLAY_SCREEN_OPEN_SOUNDS", () -> ServerSyncedConfig.PLAY_SCREEN_OPEN_SOUNDS.get(), v -> ServerSyncedConfig.PLAY_SCREEN_OPEN_SOUNDS.set(v), Boolean.class);
        put("PLAY_MOUSE_CLICK_SOUNDS", () -> ServerSyncedConfig.PLAY_MOUSE_CLICK_SOUNDS.get(), v -> ServerSyncedConfig.PLAY_MOUSE_CLICK_SOUNDS.set(v), Boolean.class);
    }

    public CompoundTag getSyncableConfigOnServer() {
        CompoundTag nbt = new CompoundTag();
        for (Map.Entry<String, Supplier<Object>> entry : getters.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue().get();
            if (value instanceof Boolean) nbt.putBoolean(name, (Boolean)value);
            else if (value instanceof Integer) nbt.putInt(name, (Integer)value);
            else if (value instanceof Double) nbt.putDouble(name, (Double)value);
        }
        return nbt;
    }

    public void updateSyncableConfigOnClient(CompoundTag nbt) {
        for (String name : getters.keySet()) {
            if (!nbt.contains(name)) continue;
            Class<?> type = types.get(name);
            Object value;
            if (type == Boolean.class) value = nbt.getBoolean(name).orElse(false);
            else if (type == Integer.class) value = nbt.getInt(name).orElse(0);
            else if (type == Double.class) value = nbt.getDouble(name).orElse(0.0);
            else continue;
            Consumer<Object> setter = setters.get(name);
            if (setter != null) setter.accept(value);
        }
    }
}
