package dmillerw.ping.client;

import com.mojang.blaze3d.platform.GlStateManager;
import dmillerw.ping.Ping;
import dmillerw.ping.client.gui.PingSelectGui;
import dmillerw.ping.data.PingType;
import dmillerw.ping.util.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Ping.MOD_ID, value = Dist.CLIENT)
public class RenderHandler {
    public static final int ITEM_PADDING = 10;
    public static final int ITEM_SIZE = 32;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();
            if ((mc.world == null || mc.isGamePaused()) && PingSelectGui.active) {
                PingSelectGui.deactivate();
            }
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent event) {
        if (!(event instanceof RenderGameOverlayEvent.Post) || event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.world != null && !mc.gameSettings.hideGUI && !mc.isGamePaused() && PingSelectGui.active) {
            renderGui();
            renderText();
        }
    }

    private static void renderGui() {
        int numOfItems = PingType.values().length - 1;

        Minecraft mc = Minecraft.getInstance();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        // Menu Background
        if (Config.VISUAL.menuBackground.get()) {
            GlStateManager.pushMatrix();
            GlStateManager.disableTexture();
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(770, 771, 1, 0);

            int halfWidth = (ITEM_SIZE * (numOfItems)) - (ITEM_PADDING * (numOfItems));
            int halfHeight = (ITEM_SIZE + ITEM_PADDING) / 2;
            int backgroundX = mc.mainWindow.getScaledWidth() / 2 - halfWidth;
            int backgroundY = mc.mainWindow.getScaledHeight() / 4 - halfHeight;

            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.pos(backgroundX, backgroundY + 15 + halfHeight * 2, 0).color(0F, 0F, 0F, 0.5F).endVertex();
            bufferBuilder.pos(backgroundX + halfWidth * 2, backgroundY + 15 + halfHeight * 2, 0).color(0F, 0F, 0F, 0.5F).endVertex();
            bufferBuilder.pos(backgroundX + halfWidth * 2, backgroundY, 0).color(0F, 0F, 0F, 0.5F).endVertex();
            bufferBuilder.pos(backgroundX, backgroundY, 0).color(0F, 0F, 0F, 0.5F).endVertex();
            tessellator.draw();

            GlStateManager.disableBlend();
            GlStateManager.enableTexture();
            GlStateManager.popMatrix();
        }

        Minecraft.getInstance().getTextureManager().bindTexture(PingHandler.TEXTURE);

        final double mouseX = mc.mouseHelper.getMouseX() * ((double) mc.mainWindow.getScaledWidth() / mc.mainWindow.getWidth());
        final double mouseY = mc.mouseHelper.getMouseY() * ((double) mc.mainWindow.getScaledHeight() / mc.mainWindow.getHeight());

        int half = numOfItems / 2;
        for (int i = 0; i < numOfItems; i++) {
            PingType type = PingType.values()[i + 1];
            int drawX = mc.mainWindow.getScaledWidth() / 2 - (ITEM_SIZE * half) - (ITEM_PADDING * (half));
            int drawY = mc.mainWindow.getScaledHeight() / 4;

            drawX += ITEM_SIZE / 2 + ITEM_PADDING / 2 + (ITEM_PADDING * i) + ITEM_SIZE * i;

            boolean mouseIn = mouseX >= (drawX - ITEM_SIZE * 0.5D) && mouseX <= (drawX + ITEM_SIZE * 0.5D) &&
                    mouseY >= (drawY - ITEM_SIZE * 0.5D) && mouseY <= (drawY + ITEM_SIZE * 0.5D);

            float min = -ITEM_SIZE / 2;
            float max = ITEM_SIZE / 2;

            int r = 255;
            int g = 255;
            int b = 255;

            // Button Background
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            if (mouseIn) {
                r = Config.VISUAL.pingR.get();
                g = Config.VISUAL.pingG.get();
                b = Config.VISUAL.pingB.get();
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

    private static void renderText() {
        Minecraft mc = Minecraft.getInstance();
        int numOfItems = PingType.values().length - 1;

        final double mouseX = mc.mouseHelper.getMouseX() * ((double) mc.mainWindow.getScaledWidth() / mc.mainWindow.getWidth());
        final double mouseY = mc.mouseHelper.getMouseY() * ((double) mc.mainWindow.getScaledHeight() / mc.mainWindow.getHeight());

        int halfHeight = (ITEM_SIZE + ITEM_PADDING) / 2;
        int backgroundY = mc.mainWindow.getScaledHeight() / 4 - halfHeight;

        int half = numOfItems / 2;
        for (int i = 0; i < numOfItems; i++) {
            PingType type = PingType.values()[i + 1];
            int drawX = mc.mainWindow.getScaledWidth() / 2 - (ITEM_SIZE * half) - (ITEM_PADDING * (half));
            int drawY = mc.mainWindow.getScaledHeight() / 4;

            drawX += ITEM_SIZE / 2 + ITEM_PADDING / 2 + (ITEM_PADDING * i) + ITEM_SIZE * i;

            boolean mouseIn = mouseX >= (drawX - ITEM_SIZE * 0.5D) && mouseX <= (drawX + ITEM_SIZE * 0.5D) &&
                    mouseY >= (drawY - ITEM_SIZE * 0.5D) && mouseY <= (drawY + ITEM_SIZE * 0.5D);

            if (mouseIn) {
                GlStateManager.pushMatrix();
                GlStateManager.color4f(255, 255, 255, 255);
                mc.fontRenderer.drawString(type.toString(), mc.mainWindow.getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(type.toString()) / 2, backgroundY + halfHeight * 2, 0xFFFFFF);
                GlStateManager.popMatrix();
            }
        }
    }
}