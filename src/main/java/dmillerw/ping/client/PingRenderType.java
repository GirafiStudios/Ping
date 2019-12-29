package dmillerw.ping.client;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class PingRenderType extends RenderState {

    public PingRenderType(String string, Runnable r, Runnable r1) {
        super(string, r, r1);
    }

    public static RenderType getPingOverlay(ResourceLocation location) {
        RenderType.State renderTypeState = RenderType.State.func_228694_a_().func_228724_a_(new RenderState.TextureState(location, false, false)).func_228726_a_(field_228514_f_).func_228716_a_(field_228533_y_).func_228713_a_(field_228517_i_).func_228719_a_(field_228529_u_).func_228722_a_(field_228530_v_).func_228728_a_(true);
        return RenderType.func_228633_a_("ping_overlay_translucent", DefaultVertexFormats.POSITION_COLOR, 7, 256, true, true, renderTypeState);
    }
}