package dmillerw.ping.client.util;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class VertexHelper {

    public static void renderPosColor(IVertexBuilder builder, Matrix4f matrix4f, Matrix3f matrix3f, float x, float y, float z, float r, float g, float b, float a) {
        builder.func_227888_a_(matrix4f, x, y, z).func_227887_a_(matrix3f, 0.0F, 1.0F, 0.0F).func_227885_a_(r, g, b, a).endVertex();
    }

    public static void renderPosTexColor(IVertexBuilder builder, Matrix4f matrix4f, Matrix3f matrix3f, float x, float y, float u, float v, float r, float g, float b, float a) {
        builder.func_227888_a_(matrix4f, x, y, 0.0F).func_227885_a_(r, g, b, a).func_225583_a_(u, v).func_227891_b_(OverlayTexture.field_229196_a_).func_227886_a_(1).func_227887_a_(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }
}