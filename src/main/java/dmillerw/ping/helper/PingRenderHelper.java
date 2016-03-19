package dmillerw.ping.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

/**
 * @author dmillerw
 */
public class PingRenderHelper {

    public static void drawBlockOverlay(float width, float height, float length, TextureAtlasSprite icon, int color, int alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();

        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;

        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

        // TOP
        vertexbuffer.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();

        // BOTTOM
        vertexbuffer.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();

        // NORTH
        vertexbuffer.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();

        // SOUTH
        vertexbuffer.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();

        // EAST
        vertexbuffer.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();

        // WEST
        vertexbuffer.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        vertexbuffer.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();

        tessellator.draw();
    }
}