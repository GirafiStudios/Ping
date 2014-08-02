package dmillerw.ping.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;

/**
 * @author dmillerw
 */
public class PingRenderHelper {

    public static void drawBlockOverlay(float width, float height, float length, IIcon icon, int color, int alpha) {
        Tessellator tessellator = Tessellator.instance;

        tessellator.startDrawingQuads();

        tessellator.setColorRGBA_I(color, alpha);

        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

        tessellator.setBrightness(Integer.MAX_VALUE);

        // TOP
        tessellator.addVertexWithUV(-(width / 2), (height / 2), -(length / 2), icon.getMinU(), icon.getMinV());
        tessellator.addVertexWithUV( (width / 2), (height / 2), -(length / 2), icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV( (width / 2), (height / 2),  (length / 2), icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(-(width / 2), (height / 2),  (length / 2), icon.getMinU(), icon.getMaxV());

        // BOTTOM
        tessellator.addVertexWithUV(-(width / 2), -(height / 2),  (length / 2), icon.getMinU(), icon.getMaxV());
        tessellator.addVertexWithUV( (width / 2), -(height / 2),  (length / 2), icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV( (width / 2), -(height / 2), -(length / 2), icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(-(width / 2), -(height / 2), -(length / 2), icon.getMinU(), icon.getMinV());

        // NORTH
        tessellator.addVertexWithUV(-(width / 2),  (height / 2),  (length / 2), icon.getMinU(), icon.getMaxV());
        tessellator.addVertexWithUV( (width / 2),  (height / 2),  (length / 2), icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV( (width / 2), -(height / 2),  (length / 2), icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(-(width / 2), -(height / 2),  (length / 2), icon.getMinU(), icon.getMinV());

        // SOUTH
        tessellator.addVertexWithUV(-(width / 2), -(height / 2), -(length / 2), icon.getMinU(), icon.getMinV());
        tessellator.addVertexWithUV( (width / 2), -(height / 2), -(length / 2), icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV( (width / 2),  (height / 2), -(length / 2), icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(-(width / 2),  (height / 2), -(length / 2), icon.getMinU(), icon.getMaxV());

        // EAST
        tessellator.addVertexWithUV(-(width / 2),  (height / 2), -(length / 2), icon.getMinU(), icon.getMaxV());
        tessellator.addVertexWithUV(-(width / 2),  (height / 2),  (length / 2), icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(-(width / 2), -(height / 2),  (length / 2), icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(-(width / 2), -(height / 2), -(length / 2), icon.getMinU(), icon.getMinV());

        // WEST
        tessellator.addVertexWithUV( (width / 2), -(height / 2), -(length / 2), icon.getMinU(), icon.getMinV());
        tessellator.addVertexWithUV( (width / 2), -(height / 2),  (length / 2), icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV( (width / 2),  (height / 2),  (length / 2), icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV( (width / 2),  (height / 2), -(length / 2), icon.getMinU(), icon.getMaxV());

        tessellator.draw();
    }
}
