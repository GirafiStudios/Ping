package dmillerw.ping.client.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import dmillerw.ping.client.PingRenderType;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.model.Material;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;

public class PingRenderHelper {
    public static final Material OVERLAY_TEXTURE = new Material(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/white_stained_glass"));

    public static void drawBlockOverlay(float width, float height, float length, MatrixStack matrixStack, int color, int alpha) {
        MatrixStack.Entry matrixEntry = matrixStack.func_227866_c_();
        Matrix4f matrix4f = matrixEntry.func_227870_a_();
        Matrix3f matrix3f = matrixEntry.func_227872_b_();
        Tessellator tessellator = Tessellator.getInstance();
        IRenderTypeBuffer renderTypeBuffer = IRenderTypeBuffer.func_228455_a_(tessellator.getBuffer());
        IVertexBuilder vertexBuilder = OVERLAY_TEXTURE.func_229311_a_(renderTypeBuffer, PingRenderType::getPingOverlay);

        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;

        // TOP
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), (height / 2), -(length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, (width / 2), (height / 2), -(length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, (width / 2), (height / 2), (length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), (height / 2), (length / 2), r, g, b, alpha);

        // BOTTOM
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), -(height / 2), (length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, (width / 2), -(height / 2), (length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, (width / 2), -(height / 2), -(length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), -(height / 2), -(length / 2), r, g, b, alpha);

        // NORTH
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), (height / 2), (length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, (width / 2), (height / 2), (length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, (width / 2), -(height / 2), (length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), -(height / 2), (length / 2), r, g, b, alpha);

        // SOUTH
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), -(height / 2), -(length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, (width / 2), -(height / 2), -(length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, (width / 2), (height / 2), -(length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), (height / 2), -(length / 2), r, g, b, alpha);

        // EAST
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), (height / 2), -(length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), (height / 2), (length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), -(height / 2), (length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, -(width / 2), -(height / 2), -(length / 2), r, g, b, alpha);

        // WEST
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, (width / 2), -(height / 2), -(length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, (width / 2), -(height / 2), (length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, (width / 2), (height / 2), (length / 2), r, g, b, alpha);
        VertexHelper.renderPosColor(vertexBuilder, matrix4f, matrix3f, (width / 2), (height / 2), -(length / 2), r, g, b, alpha);
        tessellator.draw();
    }
}