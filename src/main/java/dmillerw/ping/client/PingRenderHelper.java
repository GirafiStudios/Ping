package dmillerw.ping.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class PingRenderHelper {

    public static void drawBlockOverlay(float width, float height, float length, TextureAtlasSprite icon, int func_225586_a_, int alpha) {
        Minecraft mc = Minecraft.getInstance();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        int r = func_225586_a_ >> 16 & 255;
        int g = func_225586_a_ >> 8 & 255;
        int b = func_225586_a_ & 255;

        mc.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);

        // TOP
        bufferBuilder.func_225582_a_(-(width / 2), (height / 2), -(length / 2)).func_225583_a_(icon.getMinU(), icon.getMinV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_((width / 2), (height / 2), -(length / 2)).func_225583_a_(icon.getMaxU(), icon.getMinV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_((width / 2), (height / 2), (length / 2)).func_225583_a_(icon.getMaxU(), icon.getMaxV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_(-(width / 2), (height / 2), (length / 2)).func_225583_a_(icon.getMinU(), icon.getMaxV()).func_225586_a_(r, g, b, alpha).endVertex();

        // BOTTOM
        bufferBuilder.func_225582_a_(-(width / 2), -(height / 2), (length / 2)).func_225583_a_(icon.getMinU(), icon.getMaxV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_((width / 2), -(height / 2), (length / 2)).func_225583_a_(icon.getMaxU(), icon.getMaxV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_((width / 2), -(height / 2), -(length / 2)).func_225583_a_(icon.getMaxU(), icon.getMinV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_(-(width / 2), -(height / 2), -(length / 2)).func_225583_a_(icon.getMinU(), icon.getMinV()).func_225586_a_(r, g, b, alpha).endVertex();

        // NORTH
        bufferBuilder.func_225582_a_(-(width / 2), (height / 2), (length / 2)).func_225583_a_(icon.getMinU(), icon.getMaxV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_((width / 2), (height / 2), (length / 2)).func_225583_a_(icon.getMaxU(), icon.getMaxV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_((width / 2), -(height / 2), (length / 2)).func_225583_a_(icon.getMaxU(), icon.getMinV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_(-(width / 2), -(height / 2), (length / 2)).func_225583_a_(icon.getMinU(), icon.getMinV()).func_225586_a_(r, g, b, alpha).endVertex();

        // SOUTH
        bufferBuilder.func_225582_a_(-(width / 2), -(height / 2), -(length / 2)).func_225583_a_(icon.getMinU(), icon.getMinV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_((width / 2), -(height / 2), -(length / 2)).func_225583_a_(icon.getMaxU(), icon.getMinV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_((width / 2), (height / 2), -(length / 2)).func_225583_a_(icon.getMaxU(), icon.getMaxV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_(-(width / 2), (height / 2), -(length / 2)).func_225583_a_(icon.getMinU(), icon.getMaxV()).func_225586_a_(r, g, b, alpha).endVertex();

        // EAST
        bufferBuilder.func_225582_a_(-(width / 2), (height / 2), -(length / 2)).func_225583_a_(icon.getMinU(), icon.getMaxV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_(-(width / 2), (height / 2), (length / 2)).func_225583_a_(icon.getMaxU(), icon.getMaxV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_(-(width / 2), -(height / 2), (length / 2)).func_225583_a_(icon.getMaxU(), icon.getMinV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_(-(width / 2), -(height / 2), -(length / 2)).func_225583_a_(icon.getMinU(), icon.getMinV()).func_225586_a_(r, g, b, alpha).endVertex();

        // WEST
        bufferBuilder.func_225582_a_((width / 2), -(height / 2), -(length / 2)).func_225583_a_(icon.getMinU(), icon.getMinV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_((width / 2), -(height / 2), (length / 2)).func_225583_a_(icon.getMaxU(), icon.getMinV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_((width / 2), (height / 2), (length / 2)).func_225583_a_(icon.getMaxU(), icon.getMaxV()).func_225586_a_(r, g, b, alpha).endVertex();
        bufferBuilder.func_225582_a_((width / 2), (height / 2), -(length / 2)).func_225583_a_(icon.getMinU(), icon.getMaxV()).func_225586_a_(r, g, b, alpha).endVertex();

        tessellator.draw();
    }
}