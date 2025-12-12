package com.girafi.ping.client;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

public class PingRenderType {
    public static final RenderPipeline PING_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.OUTLINE_SNIPPET)
            .withLocation("core/position_tex_color")
            .withVertexShader("core/position_tex_color")
            .withFragmentShader("core/position_tex_color")
            .withSampler("Sampler0")
            .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
            .withBlend(BlendFunction.TRANSLUCENT)
            .withCull(false).build());
    private static final Function<Identifier, RenderType> PING = Util.memoize(
            texture -> {
                RenderSetup renderSetup = RenderSetup.builder(PING_PIPELINE)
                        .withTexture("Sampler0", texture)
                        .sortOnUpload()
                        .createRenderSetup();
                return RenderType.create("ping", renderSetup);
            }
    );

    public static RenderType ping(Identifier identifier) {
        return PING.apply(identifier);
    }
}