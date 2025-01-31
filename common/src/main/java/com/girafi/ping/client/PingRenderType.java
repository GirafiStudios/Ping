package com.girafi.ping.client;

import com.girafi.ping.Constants;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderDefines;
import net.minecraft.client.renderer.ShaderProgram;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;

public class PingRenderType extends RenderType {
    public static final ShaderProgram PING_SHADER = new ShaderProgram(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "core/rendertype_ping"), DefaultVertexFormat.POSITION_TEX_COLOR, ShaderDefines.EMPTY);
    protected static final ShaderStateShard PING = new ShaderStateShard(PING_SHADER);
    protected static final LayeringStateShard DISABLE_DEPTH = new LayeringStateShard("disable_depth", GlStateManager::_disableDepthTest, GlStateManager::_enableDepthTest);

    public PingRenderType(String s, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean b, boolean b1, Runnable r, Runnable r1) {
        super(s, vertexFormat, mode, i, b, b1, r, r1);
    }

    public static RenderType getPingOverlay() {
        CompositeState renderTypeState = CompositeState.builder().setShaderState(PING).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setTextureState(BLOCK_SHEET_MIPPED).setLayeringState(DISABLE_DEPTH).createCompositeState(true);
        return create("ping_overlay", DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS, RenderType.TRANSIENT_BUFFER_SIZE, true, true, renderTypeState);
    }

    public static RenderType getPingIcon(ResourceLocation location) {
        CompositeState renderTypeState = CompositeState.builder().setShaderState(PING).setTextureState(new TextureStateShard(location, TriState.FALSE, true)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLayeringState(DISABLE_DEPTH).createCompositeState(true);
        return create("ping_icon", DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS, RenderType.TRANSIENT_BUFFER_SIZE, true, true, renderTypeState);
    }
}