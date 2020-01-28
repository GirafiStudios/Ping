package dmillerw.ping.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dmillerw.ping.client.PingRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class PingRenderHelper {

    public static void drawBlockOverlay(float width, float height, float length, MatrixStack matrixStack, TextureAtlasSprite icon, int color, int alpha) {
        MatrixStack.Entry matrixEntry = matrixStack.getLast();
        Matrix4f posMatrix = matrixEntry.getPositionMatrix();
        RenderType pingOverlay = PingRenderType.getPingOverlay();
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        IVertexBuilder vertexBuilder = buffer.getBuffer(pingOverlay);

        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;

        // TOP
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), (height / 2), -(length / 2), icon.getMinU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), (height / 2), -(length / 2), icon.getMaxU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), (height / 2), (length / 2), icon.getMaxU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), (height / 2), (length / 2), icon.getMinU(), icon.getMaxV(), r, g, b, alpha);

        // BOTTOM
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), -(height / 2), (length / 2), icon.getMinU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), -(height / 2), (length / 2), icon.getMaxU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), -(height / 2), -(length / 2), icon.getMaxU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), -(height / 2), -(length / 2), icon.getMinU(), icon.getMinV(), r, g, b, alpha);

        // NORTH
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), (height / 2), (length / 2), icon.getMinU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), (height / 2), (length / 2), icon.getMaxU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), -(height / 2), (length / 2), icon.getMaxU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), -(height / 2), (length / 2), icon.getMinU(), icon.getMinV(), r, g, b, alpha);

        // SOUTH
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), -(height / 2), -(length / 2), icon.getMinU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), -(height / 2), -(length / 2), icon.getMaxU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), (height / 2), -(length / 2), icon.getMaxU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), (height / 2), -(length / 2), icon.getMinU(), icon.getMaxV(), r, g, b, alpha);

        // EAST
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), (height / 2), -(length / 2), icon.getMinU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), (height / 2), (length / 2), icon.getMaxU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), -(height / 2), (length / 2), icon.getMaxU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, -(width / 2), -(height / 2), -(length / 2), icon.getMinU(), icon.getMinV(), r, g, b, alpha);

        // WEST
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), -(height / 2), -(length / 2), icon.getMinU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), -(height / 2), (length / 2), icon.getMaxU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), (height / 2), (length / 2), icon.getMaxU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, posMatrix, (width / 2), (height / 2), -(length / 2), icon.getMinU(), icon.getMaxV(), r, g, b, alpha);
        buffer.finish(pingOverlay);
    }
}