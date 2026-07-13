package com.skd.playeractivityview.config;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;

public class ServerConfigSyncHelper {
    private static ServerConfigSyncHelper instance;
    private final Map<String, java.util.function.Supplier<Object>> getters = new HashMap<>();
    private final Map<String, java.util.function.Consumer<Object>> setters = new HashMap<>();

    public static ServerConfigSyncHelper getInstance() {
        if (instance == null) {
            instance = new ServerConfigSyncHelper();
            instance.init();
        }
        return instance;
    }

    private void init() {
        put("DYNAMIC_GUI_USE_OLD_SIMPLE", () -> ServerSyncedConfig.DYNAMIC_GUI_USE_OLD_SIMPLE.get(), v -> ServerSyncedConfig.DYNAMIC_GUI_USE_OLD_SIMPLE.set((Boolean)v));
        put("DYNAMIC_GUI_TICK_SEND_RATE", () -> ServerSyncedConfig.DYNAMIC_GUI_TICK_SEND_RATE.get(), v -> ServerSyncedConfig.DYNAMIC_GUI_TICK_SEND_RATE.set((Integer)v));
        put("DYNAMIC_GUI_DONT_SEND_CONSTANT_UPDATES", () -> ServerSyncedConfig.DYNAMIC_GUI_DONT_SEND_CONSTANT_UPDATES.get(), v -> ServerSyncedConfig.DYNAMIC_GUI_DONT_SEND_CONSTANT_UPDATES.set((Boolean)v));
        put("DYNAMIC_GUI_BLUR_LEVEL", () -> ServerSyncedConfig.DYNAMIC_GUI_BLUR_LEVEL.get(), v -> ServerSyncedConfig.DYNAMIC_GUI_BLUR_LEVEL.set((Integer)v));
        put("DYNAMIC_GUI_SIZE_RADIUS", () -> ServerSyncedConfig.DYNAMIC_GUI_SIZE_RADIUS.get(), v -> ServerSyncedConfig.DYNAMIC_GUI_SIZE_RADIUS.set((Double)v));
        put("DYNAMIC_GUI_SHOW_ENTIRE_SCREEN", () -> ServerSyncedConfig.DYNAMIC_GUI_SHOW_ENTIRE_SCREEN.get(), v -> ServerSyncedConfig.DYNAMIC_GUI_SHOW_ENTIRE_SCREEN.set((Boolean)v));
        put("DYNAMIC_GUI_DISABLE_BACKGROUND", () -> ServerSyncedConfig.DYNAMIC_GUI_DISABLE_BACKGROUND.get(), v -> ServerSyncedConfig.DYNAMIC_GUI_DISABLE_BACKGROUND.set((Boolean)v));
        put("SHOW_ITEMS_TRANSFERRED", () -> ServerSyncedConfig.SHOW_ITEMS_TRANSFERRED.get(), v -> ServerSyncedConfig.SHOW_ITEMS_TRANSFERRED.set((Boolean)v));
        put("DISTANCE_REQUIRED_TO_SHOW_GUI_INFO", () -> ServerSyncedConfig.DISTANCE_REQUIRED_TO_SHOW_GUI_INFO.get(), v -> ServerSyncedConfig.DISTANCE_REQUIRED_TO_SHOW_GUI_INFO.set((Integer)v));
        put("SHOW_IDLE_STATES_IN_PLAYER_LIST", () -> ServerSyncedConfig.SHOW_IDLE_STATES_IN_PLAYER_LIST.get(), v -> ServerSyncedConfig.SHOW_IDLE_STATES_IN_PLAYER_LIST.set((Boolean)v));
        put("SHOW_IDLE_STATES_IN_PLAYER_ABOVE_HEAD", () -> ServerSyncedConfig.SHOW_IDLE_STATES_IN_PLAYER_ABOVE_HEAD.get(), v -> ServerSyncedConfig.SHOW_IDLE_STATES_IN_PLAYER_ABOVE_HEAD.set((Boolean)v));
        put("SHOW_PLAYER_ANIMATIONS", () -> ServerSyncedConfig.SHOW_PLAYER_ANIMATIONS.get(), v -> ServerSyncedConfig.SHOW_PLAYER_ANIMATIONS.set((Boolean)v));
        put("SHOW_PLAYER_ANIMATION_TYPING", () -> ServerSyncedConfig.SHOW_PLAYER_ANIMATION_TYPING.get(), v -> ServerSyncedConfig.SHOW_PLAYER_ANIMATION_TYPING.set((Boolean)v));
        put("SHOW_PLAYER_ANIMATION_IDLE", () -> ServerSyncedConfig.SHOW_PLAYER_ANIMATION_IDLE.get(), v -> ServerSyncedConfig.SHOW_PLAYER_ANIMATION_IDLE.set((Boolean)v));
        put("SHOW_PLAYER_ANIMATION_GUI", () -> ServerSyncedConfig.SHOW_PLAYER_ANIMATION_GUI.get(), v -> ServerSyncedConfig.SHOW_PLAYER_ANIMATION_GUI.set((Boolean)v));
        put("SHOW_PLAYER_ACTIVE_NON_CHAT_GUI", () -> ServerSyncedConfig.SHOW_PLAYER_ACTIVE_NON_CHAT_GUI.get(), v -> ServerSyncedConfig.SHOW_PLAYER_ACTIVE_NON_CHAT_GUI.set((Boolean)v));
        put("SHOW_PLAYER_ACTIVE_CHAT_GUI", () -> ServerSyncedConfig.SHOW_PLAYER_ACTIVE_CHAT_GUI.get(), v -> ServerSyncedConfig.SHOW_PLAYER_ACTIVE_CHAT_GUI.set((Boolean)v));
        put("SCREEN_TYPING_VISIBLE", () -> ServerSyncedConfig.SCREEN_TYPING_VISIBLE.get(), v -> ServerSyncedConfig.SCREEN_TYPING_VISIBLE.set((Boolean)v));
        put("PLAY_SCREEN_OPEN_SOUNDS", () -> ServerSyncedConfig.PLAY_SCREEN_OPEN_SOUNDS.get(), v -> ServerSyncedConfig.PLAY_SCREEN_OPEN_SOUNDS.set((Boolean)v));
        put("PLAY_MOUSE_CLICK_SOUNDS", () -> ServerSyncedConfig.PLAY_MOUSE_CLICK_SOUNDS.get(), v -> ServerSyncedConfig.PLAY_MOUSE_CLICK_SOUNDS.set((Boolean)v));
    }

    private void put(String name, java.util.function.Supplier<Object> getter, java.util.function.Consumer<Object> setter) {
        getters.put(name, getter);
        setters.put(name, setter);
    }

    public CompoundTag getSyncableConfigOnServer() {
        CompoundTag nbt = new CompoundTag();
        for (Map.Entry<String, java.util.function.Supplier<Object>> entry : getters.entrySet()) {
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
            if (nbt.contains(name)) {
                Object value;
                if (nbt.getInt(name).isPresent()) value = nbt.getInt(name).get();
                else if (nbt.getDouble(name).isPresent()) value = nbt.getDouble(name).get();
                else value = nbt.getBoolean(name).orElse(false);
                java.util.function.Consumer<Object> setter = setters.get(name);
                if (setter != null) setter.accept(value);
            }
        }
    }
}
