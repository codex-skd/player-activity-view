package com.skd.playeractivity.math;

import com.skd.playeractivity.PlayerStatus;
import net.minecraft.util.Mth;

public class ModelPartData {
    public float x, y, z;
    public float xRot, yRot, zRot;
    public float xScale = 1.0F, yScale = 1.0F, zScale = 1.0F;

    public ModelPartData copyPartialLerp(PlayerStatus status, ModelPartData from, float partialTick) {
        ModelPartData result = new ModelPartData();
        result.x = Mth.lerp(status.getPartialLerp(partialTick), from.x, this.x);
        result.y = Mth.lerp(status.getPartialLerp(partialTick), from.y, this.y);
        result.z = Mth.lerp(status.getPartialLerp(partialTick), from.z, this.z);
        result.xRot = Mth.lerp(status.getPartialLerp(partialTick), from.xRot, this.xRot);
        result.yRot = Mth.lerp(status.getPartialLerp(partialTick), from.yRot, this.yRot);
        result.zRot = Mth.lerp(status.getPartialLerp(partialTick), from.zRot, this.zRot);
        result.xScale = Mth.lerp(status.getPartialLerp(partialTick), from.xScale, this.xScale);
        result.yScale = Mth.lerp(status.getPartialLerp(partialTick), from.yScale, this.yScale);
        result.zScale = Mth.lerp(status.getPartialLerp(partialTick), from.zScale, this.zScale);
        return result;
    }
}
