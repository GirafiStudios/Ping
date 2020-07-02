package dmillerw.ping.client.util;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.util.math.vector.Matrix4f;

public class VertexHelper {

    public static void renderPosTexColor(IVertexBuilder builder, Matrix4f matrix4f, float x, float y, float z, float u, float v, int r, int g, int b, int a) {
        builder.pos(matrix4f, x, y, z).tex(u, v).color(r, g, b, a).endVertex();
    }

    public static void renderPosTexColorNoZ(IVertexBuilder builder, Matrix4f matrix4f, float x, float y, float u, float v, int r, int g, int b, int a) {
        builder.pos(matrix4f, x, y, 0).tex(u, v).color(r, g, b, a).endVertex();
    }

    public static void renderPosTexColorNoZ(IVertexBuilder builder, Matrix4f matrix4f, float x, float y, float u, float v, float r, float g, float b, float a) {
        builder.pos(matrix4f, x, y, 0).tex(u, v).color(r, g, b, a).endVertex();
    }
}