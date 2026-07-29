package com.skd.playeractivityview.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.skd.playeractivityview.PlayerActivity;
import com.skd.playeractivityview.PlayerStatus;
import com.skd.playeractivityview.config.ConfigClient;
import com.skd.playeractivityview.config.ServerSyncedConfig;
import java.util.Map.Entry;
import java.util.UUID;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.SubmitCustomGeometryEvent;
import org.joml.Quaternionf;

/**
 * Draws the live "screen mirror" of remote players as a camera-facing billboard, positioned the same way
 * as the old ParticleDynamic used to be. Kept out of the particle engine because ParticleRenderType no
 * longer supports arbitrary per-instance GPU textures in this Minecraft version.
 */
public class DynamicScreenRenderer {

    public void onSubmitCustomGeometry(SubmitCustomGeometryEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;
        if (PlayerActivity.getPlayerStatusManagerClient() == null) return;

        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 camPos = camera.position();
        Quaternionf camRotation = camera.rotation();
        PoseStack poseStack = event.getPoseStack();

        for (Entry<UUID, PlayerStatus> entry : PlayerActivity.getPlayerStatusManagerClient().lookupPlayerToStatus.entrySet()) {
            PlayerStatus ps = entry.getValue();
            if (!isVisible(ps)) continue;

            Player player = mc.level.getPlayerByUUID(entry.getKey());
            if (player == null || player.isInvisible()) continue;
            if (player == mc.player) {
                boolean firstPerson = mc.options.getCameraType().isFirstPerson();
                if (firstPerson || !ConfigClient.SHOW_GUIS_FOR_YOUR_OWN_PLAYER_IN_3RD_PERSON.get()) continue;
            }

            Vec3 pos = PlayerActivity.getPlayerStatusManagerClient().getParticlePosition(player);
            float aspect = (float) ps.getScreenData().getWidth() / Math.max(1, ps.getScreenData().getHeight());
            float size = 0.6F;
            float halfW = size * Math.max(aspect, 1F);
            float halfH = size * Math.max(1F / aspect, 1F);

            poseStack.pushPose();
            poseStack.translate(pos.x - camPos.x, pos.y - camPos.y, pos.z - camPos.z);
            poseStack.mulPose(camRotation);
            RenderType renderType = RenderTypes.entityTranslucent(ps.getScreenData().getTextureId());
            float hw = halfW;
            float hh = halfH;
            event.getSubmitNodeCollector().submitCustomGeometry(poseStack, renderType, (pose, buffer) -> {
                quadVertex(buffer, pose, -hw, hh, 0, 0, 0);
                quadVertex(buffer, pose, hw, hh, 0, 1, 0);
                quadVertex(buffer, pose, hw, -hh, 0, 1, 1);
                quadVertex(buffer, pose, -hw, -hh, 0, 0, 1);
            });
            poseStack.popPose();
        }
    }

    private void quadVertex(VertexConsumer buffer, PoseStack.Pose pose, float x, float y, float z, float u, float v) {
        buffer.addVertex(pose, x, y, z)
            .setColor(255, 255, 255, 255)
            .setUv(u, v)
            .setUv1(0, 0)
            .setLight(0xF000F0)
            .setNormal(pose, 0, 0, 1);
    }

    private boolean isVisible(PlayerStatus ps) {
        if (ps.getScreenData().getImage() == null || ps.getScreenData().getTextureId() == null) return false;
        if (ps.getPlayerGuiState() == PlayerStatus.PlayerGuiState.NONE) return false;
        if (ps.getPlayerGuiState() == PlayerStatus.PlayerGuiState.CHAT_SCREEN) return false;
        return ConfigClient.SHOW_PLAYER_ACTIVE_NON_CHAT_GUI.get() && ServerSyncedConfig.SHOW_PLAYER_ACTIVE_NON_CHAT_GUI.get();
    }
}
