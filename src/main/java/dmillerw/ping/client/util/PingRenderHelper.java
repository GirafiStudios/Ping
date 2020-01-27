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
        Matrix4f matrix4f = matrixEntry.getPositionMatrix();
        Matrix3f matrix3f = matrixEntry.getNormalMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        IRenderTypeBuffer renderTypeBuffer = IRenderTypeBuffer.getImpl(tessellator.getBuffer());
        IVertexBuilder vertexBuilder = renderTypeBuffer.getBuffer(RenderType.translucent());

        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;

        //Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

        // TOP
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), (height / 2), -(length / 2), icon.getMinU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, (width / 2), (height / 2), -(length / 2), icon.getMaxU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, (width / 2), (height / 2), (length / 2), icon.getMaxU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), (height / 2), (length / 2), icon.getMinU(), icon.getMaxV(), r, g, b, alpha);

        // BOTTOM
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), -(height / 2), (length / 2), icon.getMinU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, (width / 2), -(height / 2), (length / 2), icon.getMaxU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, (width / 2), -(height / 2), -(length / 2), icon.getMaxU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), -(height / 2), -(length / 2), icon.getMinU(), icon.getMinV(), r, g, b, alpha);

        // NORTH
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), (height / 2), (length / 2), icon.getMinU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, (width / 2), (height / 2), (length / 2), icon.getMaxU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, (width / 2), -(height / 2), (length / 2), icon.getMaxU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), -(height / 2), (length / 2), icon.getMinU(), icon.getMinV(), r, g, b, alpha);

        // SOUTH
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), -(height / 2), -(length / 2), icon.getMinU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, (width / 2), -(height / 2), -(length / 2), icon.getMaxU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, (width / 2), (height / 2), -(length / 2), icon.getMaxU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), (height / 2), icon.getMinU(), icon.getMaxV(), -(length / 2), r, g, b, alpha);

        // EAST
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), (height / 2), -(length / 2), icon.getMinU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), (height / 2), (length / 2), icon.getMaxU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), -(height / 2), (length / 2), icon.getMaxU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), -(height / 2), -(length / 2), icon.getMinU(), icon.getMinV(), r, g, b, alpha);

        // WEST
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, (width / 2), -(height / 2), -(length / 2), icon.getMinU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, (width / 2), -(height / 2), (length / 2), icon.getMaxU(), icon.getMinV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, (width / 2), (height / 2), (length / 2), icon.getMaxU(), icon.getMaxV(), r, g, b, alpha);
        VertexHelper.renderPosTexColor(vertexBuilder, matrix4f, matrix3f, (width / 2), (height / 2), -(length / 2), icon.getMinU(), icon.getMaxV(), r, g, b, alpha);
        tessellator.draw();
    }
}