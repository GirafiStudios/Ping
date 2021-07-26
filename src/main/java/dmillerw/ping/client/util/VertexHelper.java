package dmillerw.ping.client.util;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;

public class VertexHelper {

    public static void renderPosTexColor(VertexConsumer builder, Matrix4f matrix4f, float x, float y, float z, float u, float v, int r, int g, int b, int a) {
        builder.vertex(matrix4f, x, y, z).uv(u, v).color(r, g, b, a).endVertex();
    }

    public static void renderPosTexColorNoZ(VertexConsumer builder, Matrix4f matrix4f, float x, float y, float u, float v, int r, int g, int b, int a) {
        builder.vertex(matrix4f, x, y, 0).uv(u, v).color(r, g, b, a).endVertex();
    }

    public static void renderPosTexColorNoZ(VertexConsumer builder, Matrix4f matrix4f, float x, float y, float u, float v, float r, float g, float b, float a) {
        builder.vertex(matrix4f, x, y, 0).uv(u, v).color(r, g, b, a).endVertex();
    }
}