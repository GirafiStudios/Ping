package dmillerw.ping.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class PingRenderType extends RenderState {
    protected static final RenderState.TransparencyState OVERLAY_TRANSPARENCY = new RenderState.TransparencyState("overlay_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }, RenderSystem::disableBlend);
    protected static final RenderState.LayerState DISABLE_DEPTH = new RenderState.LayerState("disable_depth", GlStateManager::disableDepthTest, GlStateManager::enableDepthTest);

    public PingRenderType(String string, Runnable r, Runnable r1) {
        super(string, r, r1);
    }

    public static RenderType getPingOverlay() {
        RenderType.State renderTypeState = RenderType.State.builder().transparency(OVERLAY_TRANSPARENCY).texture(BLOCK_SHEET).layer(DISABLE_DEPTH).build(true);
        return RenderType.get("ping_overlay", DefaultVertexFormats.POSITION_TEX_COLOR, 7, 262144, true, true, renderTypeState);
    }

    public static RenderType getPingIcon(ResourceLocation location) {
        RenderType.State renderTypeState = RenderType.State.builder().texture(new RenderState.TextureState(location, false, false)).layer(DISABLE_DEPTH).build(true);
        return RenderType.get("ping_icon", DefaultVertexFormats.POSITION_TEX_COLOR, 7, 262144, true, true, renderTypeState);
    }
}