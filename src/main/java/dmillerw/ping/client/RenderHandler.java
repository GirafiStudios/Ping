package dmillerw.ping.client;

import dmillerw.ping.client.gui.GuiPingSelect;
import dmillerw.ping.data.PingType;
import dmillerw.ping.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

@EventBusSubscriber
public class RenderHandler {
    public static final int ITEM_PADDING = 10;
    public static final int ITEM_SIZE = 32;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getMinecraft();
            if ((mc.world == null || mc.isGamePaused()) && GuiPingSelect.active) {
                GuiPingSelect.deactivate();
            }
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent event) {
        if (!(event instanceof RenderGameOverlayEvent.Post) || event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        if (mc.world != null && !mc.gameSettings.hideGUI && !mc.isGamePaused() && GuiPingSelect.active) {
            renderGui(event.getResolution());
            renderText(event.getResolution());
        }
    }

    private static void renderGui(ScaledResolution resolution) {
        int numOfItems = PingType.values().length - 1;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        // Menu Background
        if (ClientProxy.menuBackground) {
            GlStateManager.pushMatrix();
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);

            float halfWidth = (ITEM_SIZE * (numOfItems)) - (ITEM_PADDING * (numOfItems));
            float halfHeight = (ITEM_SIZE + ITEM_PADDING) / 2;
            float backgroundX = resolution.getScaledWidth() / 2 - halfWidth;
            float backgroundY = resolution.getScaledHeight() / 4 - halfHeight;

            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(backgroundX, backgroundY + 15 + halfHeight * 2, 0).color(0F, 0F, 0F, 0.5F).endVertex();
            bufferBuilder.pos(backgroundX + halfWidth * 2, backgroundY + 15 + halfHeight * 2, 0).color(0F, 0F, 0F, 0.5F).endVertex();
            bufferBuilder.pos(backgroundX + halfWidth * 2, backgroundY, 0).color(0F, 0F, 0F, 0.5F).endVertex();
            bufferBuilder.pos(backgroundX, backgroundY, 0).color(0F, 0F, 0F, 0.5F).endVertex();
            tessellator.draw();

            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GlStateManager.popMatrix();
        }

        Minecraft.getMinecraft().getTextureManager().bindTexture(PingHandler.TEXTURE);

        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();
        final int mouseX = Mouse.getX() * width / Minecraft.getMinecraft().displayWidth;
        final int mouseY = height - Mouse.getY() * height / Minecraft.getMinecraft().displayHeight - 1;

        float half = numOfItems / 2;
        for (int i = 0; i < numOfItems; i++) {
            PingType type = PingType.values()[i + 1];
            float drawX = resolution.getScaledWidth() / 2 - (ITEM_SIZE * half) - (ITEM_PADDING * (half));
            float drawY = resolution.getScaledHeight() / 4;

            drawX += ITEM_SIZE / 2 + ITEM_PADDING / 2 + (ITEM_PADDING * i) + ITEM_SIZE * i;

            boolean mouseIn = mouseX >= (drawX - ITEM_SIZE / 2) && mouseX <= (drawX + ITEM_SIZE / 2) &&
                    mouseY >= (drawY - ITEM_SIZE / 2) && mouseY <= (drawY + ITEM_SIZE / 2);

            float min = -ITEM_SIZE / 2;
            float max = ITEM_SIZE / 2;

            int r = 255;
            int g = 255;
            int b = 255;

            // Button Background
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            if (mouseIn) {
                r = ClientProxy.pingR;
                g = ClientProxy.pingG;
                b = ClientProxy.pingB;
            }
            bufferBuilder.pos(drawX + min, drawY + max, 0).tex(PingType.BACKGROUND.minU, PingType.BACKGROUND.maxV).color(r, g, b, 255).endVertex();
            bufferBuilder.pos(drawX + max, drawY + max, 0).tex(PingType.BACKGROUND.maxU, PingType.BACKGROUND.maxV).color(r, g, b, 255).endVertex();
            bufferBuilder.pos(drawX + max, drawY + min, 0).tex(PingType.BACKGROUND.maxU, PingType.BACKGROUND.minV).color(r, g, b, 255).endVertex();
            bufferBuilder.pos(drawX + min, drawY + min, 0).tex(PingType.BACKGROUND.minU, PingType.BACKGROUND.minV).color(r, g, b, 255).endVertex();
            tessellator.draw();

            // Button Icon
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.pos(drawX + min, drawY + max, 0).tex(type.minU, type.maxV).color(255, 255, 255, 255).endVertex();
            bufferBuilder.pos(drawX + max, drawY + max, 0).tex(type.maxU, type.maxV).color(255, 255, 255, 255).endVertex();
            bufferBuilder.pos(drawX + max, drawY + min, 0).tex(type.maxU, type.minV).color(255, 255, 255, 255).endVertex();
            bufferBuilder.pos(drawX + min, drawY + min, 0).tex(type.minU, type.minV).color(255, 255, 255, 255).endVertex();
            tessellator.draw();
        }
    }

    private static void renderText(ScaledResolution resolution) {
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
        for (int i = 0; i < numOfItems; i++) {
            PingType type = PingType.values()[i + 1];
            float drawX = resolution.getScaledWidth() / 2 - (ITEM_SIZE * half) - (ITEM_PADDING * (half));
            float drawY = resolution.getScaledHeight() / 4;

            drawX += ITEM_SIZE / 2 + ITEM_PADDING / 2 + (ITEM_PADDING * i) + ITEM_SIZE * i;

            boolean mouseIn = mouseX >= (drawX - ITEM_SIZE / 2) && mouseX <= (drawX + ITEM_SIZE / 2) &&
                    mouseY >= (drawY - ITEM_SIZE / 2) && mouseY <= (drawY + ITEM_SIZE / 2);

            if (mouseIn) {
                GlStateManager.pushMatrix();
                GlStateManager.color(255, 255, 255, 255);
                mc.fontRenderer.drawString(type.toString(), resolution.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(type.toString()) / 2, (int) (backgroundY + halfHeight * 2), 0xFFFFFF);
                GlStateManager.popMatrix();
            }
        }
    }
}