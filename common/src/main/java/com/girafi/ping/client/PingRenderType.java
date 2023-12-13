package com.girafi.ping.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class PingRenderType extends RenderType {
    protected static final ShaderStateShard RENDER_TYPE_PING = new ShaderStateShard(ClientHandlerBase::getRenderTypePing);
    protected static final LayeringStateShard DISABLE_DEPTH = new LayeringStateShard("disable_depth", GlStateManager::_disableDepthTest, GlStateManager::_enableDepthTest);

    public PingRenderType(String s, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean b, boolean b1, Runnable r, Runnable r1) {
        super(s, vertexFormat, mode, i, b, b1, r, r1);
    }

    public static RenderType getPingOverlay() {
        CompositeState renderTypeState = CompositeState.builder().setShaderState(RENDER_TYPE_PING).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setTextureState(BLOCK_SHEET_MIPPED).setLayeringState(DISABLE_DEPTH).createCompositeState(true);
        return create("ping_overlay", DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS, RenderType.MEDIUM_BUFFER_SIZE, true, true, renderTypeState);
    }

    public static RenderType getPingIcon(ResourceLocation location) {
        CompositeState renderTypeState = CompositeState.builder().setShaderState(RENDER_TYPE_PING).setTextureState(new TextureStateShard(location, false, true)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLayeringState(DISABLE_DEPTH).createCompositeState(true);
        return create("ping_icon", DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS, RenderType.MEDIUM_BUFFER_SIZE, true, true, renderTypeState);
    }
}