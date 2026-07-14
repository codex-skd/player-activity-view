package com.skd.playeractivityview.render;

import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class EntityRenderStateTracker {
    private static final WeakHashMap<EntityRenderState, UUID> stateToUuid = new WeakHashMap<>();

    public static void put(EntityRenderState state, UUID uuid) {
        if (uuid != null) stateToUuid.put(state, uuid);
    }

    public static UUID get(EntityRenderState state) {
        return stateToUuid.get(state);
    }

    public static void clear() {
        stateToUuid.clear();
    }
}
