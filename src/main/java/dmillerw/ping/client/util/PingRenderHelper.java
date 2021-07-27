package dmillerw.ping.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import dmillerw.ping.client.PingRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class PingRenderHelper {

    public static void drawBlockOverlay(float width, float height, float length, PoseStack poseStack, TextureAtlasSprite icon, int color, int alpha) {
        PoseStack.Pose matrixEntry = poseStack.last();
        Matrix4f posMatrix = matrixEntry.pose();
        RenderType pingOverlay = PingRenderType.getPingOverlay();
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vertexBuilder = buffer.getBuffer(pingOverlay);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;

        // TOP
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), (height / 2), -(length / 2), icon.getU0(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), (height / 2), -(length / 2), icon.getU1(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), (height / 2), (length / 2), icon.getU1(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), (height / 2), (length / 2), icon.getU0(), icon.getV1(), r, g, b, alpha);

        // BOTTOM
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), -(height / 2), (length / 2), icon.getU0(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), -(height / 2), (length / 2), icon.getU1(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), -(height / 2), -(length / 2), icon.getU1(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), -(height / 2), -(length / 2), icon.getU0(), icon.getV0(), r, g, b, alpha);

        // NORTH
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), (height / 2), (length / 2), icon.getU0(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), (height / 2), (length / 2), icon.getU1(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), -(height / 2), (length / 2), icon.getU1(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), -(height / 2), (length / 2), icon.getU0(), icon.getV0(), r, g, b, alpha);

        // SOUTH
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), -(height / 2), -(length / 2), icon.getU0(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), -(height / 2), -(length / 2), icon.getU1(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), (height / 2), -(length / 2), icon.getU1(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), (height / 2), -(length / 2), icon.getU0(), icon.getV1(), r, g, b, alpha);

        // EAST
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), (height / 2), -(length / 2), icon.getU0(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), (height / 2), (length / 2), icon.getU1(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), -(height / 2), (length / 2), icon.getU1(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), -(height / 2), -(length / 2), icon.getU0(), icon.getV0(), r, g, b, alpha);

        // WEST
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), -(height / 2), -(length / 2), icon.getU0(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), -(height / 2), (length / 2), icon.getU1(), icon.getV0(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), (height / 2), (length / 2), icon.getU1(), icon.getV1(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), (height / 2), -(length / 2), icon.getU0(), icon.getV1(), r, g, b, alpha);
        buffer.endBatch(pingOverlay);
    }
}