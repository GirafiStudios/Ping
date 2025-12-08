package com.girafi.ping.client.util;

import com.girafi.ping.client.PingHandlerHelper;
import com.girafi.ping.client.PingRenderType;
import com.girafi.ping.data.PingWrapper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import org.joml.Matrix4f;

public class PingRenderHelper {

    public static void drawBlockOverlay(float width, float height, float length, PoseStack poseStack, TextureAtlasSprite icon, PingWrapper ping, int alpha) {
        PoseStack.Pose matrixEntry = poseStack.last();
        Matrix4f posMatrix = matrixEntry.pose();
        RenderType pingOverlay = PingRenderType.pingOverlay();
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vertexConsumer = buffer.getBuffer(pingOverlay);

        int color = ping.color;
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

        PingHandlerHelper.drawString(ping.pos, "TEST", 0, 2, 0, 1, poseStack, buffer, 0, 0);

    }
}