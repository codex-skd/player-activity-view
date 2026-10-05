package com.skd.playeractivityview;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;

import java.io.IOException;

public class ShaderInstanceBlur extends ShaderInstance {

    public final Uniform RESOLUTION;

    public final Uniform RADIUS;

    public final Uniform BLUR_LEVEL;

    /** Panel rect inside the mirror target, in pixels: {@code [x1, y1, x2, y2]}. Null if absent. */
    public final Uniform CONTENT_RECT;

    public ShaderInstanceBlur(ResourceProvider p_173336_, ResourceLocation shaderLocation, VertexFormat p_173338_) throws IOException {
        super(p_173336_, shaderLocation.toString(), p_173338_);
        this.RESOLUTION = this.getUniform("resolution");
        this.RADIUS = this.getUniform("radius");
        this.BLUR_LEVEL = this.getUniform("blurLevel");
        this.CONTENT_RECT = this.getUniform("contentRect");
    }

    public ShaderInstanceBlur(ResourceProvider p_173336_, String shaderLocation, VertexFormat p_173338_) throws IOException {
        super(p_173336_, shaderLocation, p_173338_);
        this.RESOLUTION = this.getUniform("resolution");
        this.RADIUS = this.getUniform("radius");
        this.BLUR_LEVEL = this.getUniform("blurLevel");
        this.CONTENT_RECT = this.getUniform("contentRect");
    }
}
