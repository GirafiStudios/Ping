package com.girafi.ping.client.util;

import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;

public class VertexHelper {

    public static void renderPosTexColor(VertexConsumer builder, Matrix4f matrix4f, float x, float y, float z, float u, float v, int r, int g, int b, int a) {
        builder.addVertex(matrix4f, x, y, z).setUv(u, v).setColor(r, g, b, a);
    }

    public static void renderPosTexColorNoZ(VertexConsumer builder, Matrix4f matrix4f, float x, float y, float u, float v, int r, int g, int b, int a) {
        builder.addVertex(matrix4f, x, y, 0).setUv(u, v).setColor(r, g, b, a);
    }

    public static void renderPosTexColorNoZ(VertexConsumer builder, Matrix4f matrix4f, float x, float y, float u, float v, float r, float g, float b, float a) {
        builder.addVertex(matrix4f, x, y, 0).setUv(u, v).setColor(r, g, b, a);
    }
}