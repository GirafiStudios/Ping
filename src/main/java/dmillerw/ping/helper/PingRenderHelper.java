package dmillerw.ping.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

/**
 * @author dmillerw
 */
public class PingRenderHelper {

    public static void drawBlockOverlay(float width, float height, float length, TextureAtlasSprite icon, int color, int alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;

        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

        // TOP
        worldrenderer.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();

        // BOTTOM
        worldrenderer.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();

        // NORTH
        worldrenderer.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();

        // SOUTH
        worldrenderer.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();

        // EAST
        worldrenderer.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();

        // WEST
        worldrenderer.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();
        worldrenderer.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).color(r, g, b, alpha).endVertex();

        tessellator.draw();
    }
}