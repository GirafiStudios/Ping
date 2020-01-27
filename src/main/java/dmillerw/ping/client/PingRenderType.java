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
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.disableDepthTest();
    }, () -> {
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public PingRenderType(String string, Runnable r, Runnable r1) {
        super(string, r, r1);
    }

    public static RenderType getPingOverlay() {
        RenderType.State renderTypeState = RenderType.State.builder().transparency(OVERLAY_TRANSPARENCY)
                                                                            //.func_228716_a_(field_228533_y_)
                                                                            //.func_228713_a_(field_228517_i_)
                                                                            //.func_228719_a_(field_228529_u_)
                                                                            //.func_228722_a_(field_228530_v_)
                                                                            .build(true);
        return RenderType.get("ping_overlay_transparent", DefaultVertexFormats.BLOCK, 7, 256, true, true, renderTypeState);
    }
}