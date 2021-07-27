package dmillerw.ping.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class PingRenderType extends RenderStateShard {
    protected static final RenderStateShard.LayeringStateShard DISABLE_DEPTH = new RenderStateShard.LayeringStateShard("disable_depth", GlStateManager::_disableDepthTest, GlStateManager::_enableDepthTest);

    public PingRenderType(String string, Runnable r, Runnable r1) {
        super(string, r, r1);
    }

    public static RenderType getPingOverlay() { //TODO ShaderState might be wrong
        RenderType.CompositeState renderTypeState = RenderType.CompositeState.builder().setShaderState(POSITION_COLOR_TEX_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setTextureState(BLOCK_SHEET).setLayeringState(DISABLE_DEPTH).createCompositeState(true);
        return RenderType.create("ping_overlay", DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS, 262144, true, true, renderTypeState);
    }

    public static RenderType getPingIcon(ResourceLocation location) { //TODO ShaderState might be wrong
        RenderType.CompositeState renderTypeState = RenderType.CompositeState.builder().setShaderState(POSITION_COLOR_TEX_SHADER).setTextureState(new RenderStateShard.TextureStateShard(location, false, true)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLayeringState(DISABLE_DEPTH).createCompositeState(true);
        return RenderType.create("ping_icon", DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS, 262144, true, true, renderTypeState);
    }
}