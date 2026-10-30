package com.girafi.ping.client.util;

import com.girafi.ping.client.PingRenderType;
import com.girafi.ping.data.PingWrapper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4f;

public class PingRenderHelper {

    public static void drawBlockOverlay(float width, float height, float length, PoseStack poseStack, SubmitNodeCollector collector, BlockState overlayBlock, PingWrapper ping, int alpha) {
        TextureAtlasSprite icon = Minecraft.getInstance().getModelManager().getBlockStateModelSet().getParticleMaterial(overlayBlock).sprite();
        RenderType pingOverlay = PingRenderType.ping(icon.atlasLocation());

        int color = ping.color;
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;

        collector.submitCustomGeometry(poseStack, pingOverlay, (pose, vertexConsumer) -> {
            Matrix4f posMatrix = pose.pose();

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
        });
    }
}