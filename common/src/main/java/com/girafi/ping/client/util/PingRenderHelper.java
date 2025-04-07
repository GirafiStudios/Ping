package com.girafi.ping.client.util;

import com.girafi.ping.client.PingRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.joml.Matrix4f;

public class PingRenderHelper {

    public static void drawBlockOverlay(float width, float height, float length, PoseStack poseStack, TextureAtlasSprite icon, int color, int alpha) {
        PoseStack.Pose matrixEntry = poseStack.last();
        Matrix4f posMatrix = matrixEntry.pose();
        RenderType pingOverlay = PingRenderType.pingOverlay();
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vertexConsumer = buffer.getBuffer(pingOverlay);

        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;

        // TOP
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, -(width / 2), (height / 2), -(length / 2), icon.getU0(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, (width / 2), (height / 2), -(length / 2), icon.getU1(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, (width / 2), (height / 2), (length / 2), icon.getU1(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, -(width / 2), (height / 2), (length / 2), icon.getU0(), icon.getV1(), r, g, b, alpha);

        // BOTTOM
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, -(width / 2), -(height / 2), (length / 2), icon.getU0(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, (width / 2), -(height / 2), (length / 2), icon.getU1(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, (width / 2), -(height / 2), -(length / 2), icon.getU1(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, -(width / 2), -(height / 2), -(length / 2), icon.getU0(), icon.getV0(), r, g, b, alpha);

        // NORTH
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, -(width / 2), (height / 2), (length / 2), icon.getU0(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, (width / 2), (height / 2), (length / 2), icon.getU1(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, (width / 2), -(height / 2), (length / 2), icon.getU1(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, -(width / 2), -(height / 2), (length / 2), icon.getU0(), icon.getV0(), r, g, b, alpha);

        // SOUTH
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, -(width / 2), -(height / 2), -(length / 2), icon.getU0(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, (width / 2), -(height / 2), -(length / 2), icon.getU1(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, (width / 2), (height / 2), -(length / 2), icon.getU1(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, -(width / 2), (height / 2), -(length / 2), icon.getU0(), icon.getV1(), r, g, b, alpha);

        // EAST
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, -(width / 2), (height / 2), -(length / 2), icon.getU0(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, -(width / 2), (height / 2), (length / 2), icon.getU1(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, -(width / 2), -(height / 2), (length / 2), icon.getU1(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, -(width / 2), -(height / 2), -(length / 2), icon.getU0(), icon.getV0(), r, g, b, alpha);

        // WEST
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, (width / 2), -(height / 2), -(length / 2), icon.getU0(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, (width / 2), -(height / 2), (length / 2), icon.getU1(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, (width / 2), (height / 2), (length / 2), icon.getU1(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexConsumer, posMatrix, (width / 2), (height / 2), -(length / 2), icon.getU0(), icon.getV1(), r, g, b, alpha);
        buffer.endBatch(pingOverlay);
    }
}