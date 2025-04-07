package com.girafi.ping.client;

import com.girafi.ping.Constants;
import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;

import java.util.function.Function;

public class PingRenderType {
    protected static final RenderStateShard.LayeringStateShard DISABLE_DEPTH = new RenderStateShard.LayeringStateShard("disable_depth", GlStateManager::_disableDepthTest, GlStateManager::_enableDepthTest);
    public static final RenderPipeline PING_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.OUTLINE_SNIPPET).withLocation(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "pipeline/ping")).withVertexShader(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "core/ping")).withFragmentShader(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "core/ping")).withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS).withSampler("Sampler0").withBlend(BlendFunction.TRANSLUCENT).withCull(false).build());
    private static final RenderType PING_OVERLAY = RenderType.create("ping_overlay", RenderType.TRANSIENT_BUFFER_SIZE, true, true, PING_PIPELINE, RenderType.CompositeState.builder().setTextureState(RenderStateShard.BLOCK_SHEET_MIPPED).setLayeringState(DISABLE_DEPTH).createCompositeState(true));
    private static final Function<ResourceLocation, RenderType> PING_ICON = Util.memoize((location) -> RenderType.create("ping_icon", RenderType.TRANSIENT_BUFFER_SIZE, true, true, PING_PIPELINE, RenderType.CompositeState.builder().setTextureState(new RenderStateShard.TextureStateShard(location, TriState.FALSE, true)).setLayeringState(DISABLE_DEPTH).createCompositeState(true)));


    public static RenderType pingOverlay() {
        return PING_OVERLAY;
    }

    public static RenderType pingIcon(ResourceLocation location) {
        return PING_ICON.apply(location);
    }
}