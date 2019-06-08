package dmillerw.ping.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class PingRenderHelper {

    public static void drawBlockOverlay(float width, float height, float length, TextureAtlasSprite icon, int color, int alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;

        Minecraft.getInstance().getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

        // TOP
        bufferBuilder.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();

        // BOTTOM
        bufferBuilder.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();

        // NORTH
        bufferBuilder.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();

        // SOUTH
        bufferBuilder.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();

        // EAST
        bufferBuilder.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();

        // WEST
        bufferBuilder.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        bufferBuilder.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();

        tessellator.draw();
    }
}