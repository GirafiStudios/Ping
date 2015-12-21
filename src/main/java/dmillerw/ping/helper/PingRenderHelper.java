package dmillerw.ping.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
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

        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        GlStateManager.color(color >> 16 & 255, color >> 8 & 255, color & 255, alpha);

        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

        //worldrenderer.putBrightness4(Integer.MAX_VALUE); //TODO?

        // TOP
        worldrenderer.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).endVertex();
        worldrenderer.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).endVertex();
        worldrenderer.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).endVertex();
        worldrenderer.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).endVertex();

        // BOTTOM
        worldrenderer.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).endVertex();
        worldrenderer.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).endVertex();
        worldrenderer.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).endVertex();
        worldrenderer.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).endVertex();

        // NORTH
        worldrenderer.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMinU(), icon.getMaxV()).endVertex();
        worldrenderer.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).endVertex();
        worldrenderer.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).endVertex();
        worldrenderer.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMinU(), icon.getMinV()).endVertex();

        // SOUTH
        worldrenderer.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).endVertex();
        worldrenderer.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMinV()).endVertex();
        worldrenderer.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMaxU(), icon.getMaxV()).endVertex();
        worldrenderer.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).endVertex();

        // EAST
        worldrenderer.pos(-(width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).endVertex();
        worldrenderer.pos(-(width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).endVertex();
        worldrenderer.pos(-(width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).endVertex();
        worldrenderer.pos(-(width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).endVertex();

        // WEST
        worldrenderer.pos((width / 2), -(height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMinV()).endVertex();
        worldrenderer.pos((width / 2), -(height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMinV()).endVertex();
        worldrenderer.pos((width / 2), (height / 2), (length / 2)).tex(icon.getMaxU(), icon.getMaxV()).endVertex();
        worldrenderer.pos((width / 2), (height / 2), -(length / 2)).tex(icon.getMinU(), icon.getMaxV()).endVertex();

        tessellator.draw();
    }
}