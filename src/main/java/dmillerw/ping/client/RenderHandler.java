package dmillerw.ping.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import dmillerw.ping.client.gui.GuiPingSelect;
import dmillerw.ping.data.PingType;
import dmillerw.ping.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/**
 * @author dmillerw
 */
public class RenderHandler {

    private static final float Z_LEVEL = 0.05F;

    public static final int ITEM_PADDING = 10;
    public static final int ITEM_SIZE = 32;

    public static void register() {
        RenderHandler clientTickHandler = new RenderHandler();
        FMLCommonHandler.instance().bus().register(clientTickHandler);
        MinecraftForge.EVENT_BUS.register(clientTickHandler);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getMinecraft();
            if ((mc.theWorld == null || mc.isGamePaused()) && GuiPingSelect.active) {
                GuiPingSelect.deactivate();
            }
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        if (!(event instanceof RenderGameOverlayEvent.Post) || event.type != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld != null && !mc.gameSettings.hideGUI && !mc.isGamePaused() && GuiPingSelect.active) {
            renderGui(event.resolution, Z_LEVEL);
            renderText(event.resolution, Z_LEVEL);
        }
    }

    private void renderGui(ScaledResolution resolution, double zLevel) {
        int numOfItems = PingType.values().length - 1;

        Minecraft mc = Minecraft.getMinecraft();
        Tessellator tessellator = Tessellator.instance;

        // Background
        GL11.glPushMatrix();

        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);

        float halfWidth = (ITEM_SIZE * (numOfItems)) - (ITEM_PADDING * (numOfItems));
        float halfHeight = (ITEM_SIZE + ITEM_PADDING) / 2;
        float backgroundX = resolution.getScaledWidth() / 2 - halfWidth;
        float backgroundY = resolution.getScaledHeight() / 4 - halfHeight;

        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(0x000000, 122);

        tessellator.addVertex(backgroundX, backgroundY + 15 + halfHeight * 2, 0);
        tessellator.addVertex(backgroundX + halfWidth * 2, backgroundY + 15 + halfHeight * 2, 0);
        tessellator.addVertex(backgroundX + halfWidth * 2, backgroundY, 0);
        tessellator.addVertex(backgroundX, backgroundY, 0);

        tessellator.draw();

        GL11.glDisable(GL11.GL_BLEND);

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glPopMatrix();

        Minecraft.getMinecraft().getTextureManager().bindTexture(PingHandler.TEXTURE);

        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();
        final int mouseX = Mouse.getX() * width / mc.displayWidth;
        final int mouseY = height - Mouse.getY() * height / mc.displayHeight - 1;

        float half = numOfItems / 2;
        for (int i=0; i<numOfItems; i++) {
            PingType type = PingType.values()[i + 1];
            float drawX = resolution.getScaledWidth() / 2 - (ITEM_SIZE * half) - (ITEM_PADDING * (half));
            float drawY = resolution.getScaledHeight() / 4;

            drawX += ITEM_SIZE / 2 + ITEM_PADDING / 2 + (ITEM_PADDING * i) + ITEM_SIZE * i;

            boolean mouseIn = mouseX >= (drawX - ITEM_SIZE / 2) && mouseX <= (drawX + ITEM_SIZE / 2) &&
                              mouseY >= (drawY - ITEM_SIZE / 2) && mouseY <= (drawY + ITEM_SIZE / 2);

            float min = -ITEM_SIZE / 2;
            float max =  ITEM_SIZE / 2;

            // Background
            tessellator.startDrawingQuads();
            if (mouseIn) {
                tessellator.setColorOpaque(ClientProxy.pingR, ClientProxy.pingG, ClientProxy.pingB);
            } else {
                tessellator.setColorOpaque_I(0xFFFFFF);
            }
            tessellator.addVertexWithUV(drawX + min, drawY + max, 0, PingType.BACKGROUND.minU, PingType.BACKGROUND.maxV);
            tessellator.addVertexWithUV(drawX + max, drawY + max, 0, PingType.BACKGROUND.maxU, PingType.BACKGROUND.maxV);
            tessellator.addVertexWithUV(drawX + max, drawY + min, 0, PingType.BACKGROUND.maxU, PingType.BACKGROUND.minV);
            tessellator.addVertexWithUV(drawX + min, drawY + min, 0, PingType.BACKGROUND.minU, PingType.BACKGROUND.minV);
            tessellator.draw();

            // Icon
            tessellator.setColorOpaque_F(1, 1, 1);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(drawX + min, drawY + max, 0, type.minU, type.maxV);
            tessellator.addVertexWithUV(drawX + max, drawY + max, 0, type.maxU, type.maxV);
            tessellator.addVertexWithUV(drawX + max, drawY + min, 0, type.maxU, type.minV);
            tessellator.addVertexWithUV(drawX + min, drawY + min, 0, type.minU, type.minV);
            tessellator.draw();
        }
    }

    private void renderText(ScaledResolution resolution, double zLevel) {
        Minecraft mc = Minecraft.getMinecraft();
        int numOfItems = PingType.values().length - 1;

        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();
        final int mouseX = Mouse.getX() * width / mc.displayWidth;
        final int mouseY = height - Mouse.getY() * height / mc.displayHeight - 1;

        float halfWidth = (ITEM_SIZE * (numOfItems)) - (ITEM_PADDING * (numOfItems));
        float halfHeight = (ITEM_SIZE + ITEM_PADDING) / 2;
        float backgroundX = resolution.getScaledWidth() / 2 - halfWidth;
        float backgroundY = resolution.getScaledHeight() / 4 - halfHeight;

        float half = numOfItems / 2;
        for (int i=0; i<numOfItems; i++) {
            PingType type = PingType.values()[i + 1];
            float drawX = resolution.getScaledWidth() / 2 - (ITEM_SIZE * half) - (ITEM_PADDING * (half));
            float drawY = resolution.getScaledHeight() / 4;

            drawX += ITEM_SIZE / 2 + ITEM_PADDING / 2 + (ITEM_PADDING * i) + ITEM_SIZE * i;

            boolean mouseIn = mouseX >= (drawX - ITEM_SIZE / 2) && mouseX <= (drawX + ITEM_SIZE / 2) &&
                    mouseY >= (drawY - ITEM_SIZE / 2) && mouseY <= (drawY + ITEM_SIZE / 2);

            if (mouseIn) {
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glColor4f(1, 1, 1, 1);
                mc.fontRenderer.drawString(type.toString(), resolution.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(type.toString()) / 2, (int) (backgroundY + halfHeight * 2), 0xFFFFFF);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
            }
        }
    }
}
