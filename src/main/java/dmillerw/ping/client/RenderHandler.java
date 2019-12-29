package dmillerw.ping.client;

import com.mojang.blaze3d.systems.RenderSystem;
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
            RenderSystem.pushMatrix();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 771, 1, 0);

            int halfWidth = (ITEM_SIZE * (numOfItems)) - (ITEM_PADDING * (numOfItems));
            int halfHeight = (ITEM_SIZE + ITEM_PADDING) / 2;
            int backgroundX = mc.func_228018_at_().getScaledWidth() / 2 - halfWidth;
            int backgroundY = mc.func_228018_at_().getScaledHeight() / 4 - halfHeight;

            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.func_225582_a_(backgroundX, backgroundY + 15 + halfHeight * 2, 0).func_227885_a_(0F, 0F, 0F, 0.5F).endVertex();
            bufferBuilder.func_225582_a_(backgroundX + halfWidth * 2, backgroundY + 15 + halfHeight * 2, 0).func_227885_a_(0F, 0F, 0F, 0.5F).endVertex();
            bufferBuilder.func_225582_a_(backgroundX + halfWidth * 2, backgroundY, 0).func_227885_a_(0F, 0F, 0F, 0.5F).endVertex();
            bufferBuilder.func_225582_a_(backgroundX, backgroundY, 0).func_227885_a_(0F, 0F, 0F, 0.5F).endVertex();
            tessellator.draw();

            RenderSystem.disableBlend();
            RenderSystem.enableTexture();
            RenderSystem.popMatrix();
        }

        Minecraft.getInstance().getTextureManager().bindTexture(PingHandler.TEXTURE);

        final double mouseX = mc.mouseHelper.getMouseX() * ((double) mc.func_228018_at_().getScaledWidth() / mc.func_228018_at_().getWidth());
        final double mouseY = mc.mouseHelper.getMouseY() * ((double) mc.func_228018_at_().getScaledHeight() / mc.func_228018_at_().getHeight());

        int half = numOfItems / 2;
        for (int i = 0; i < numOfItems; i++) {
            PingType type = PingType.values()[i + 1];
            int drawX = mc.func_228018_at_().getScaledWidth() / 2 - (ITEM_SIZE * half) - (ITEM_PADDING * (half));
            int drawY = mc.func_228018_at_().getScaledHeight() / 4;

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
            bufferBuilder.func_225582_a_(drawX + min, drawY + max, 0).func_225583_a_(PingType.BACKGROUND.minU, PingType.BACKGROUND.maxV).func_225586_a_(r, g, b, 255).endVertex();
            bufferBuilder.func_225582_a_(drawX + max, drawY + max, 0).func_225583_a_(PingType.BACKGROUND.maxU, PingType.BACKGROUND.maxV).func_225586_a_(r, g, b, 255).endVertex();
            bufferBuilder.func_225582_a_(drawX + max, drawY + min, 0).func_225583_a_(PingType.BACKGROUND.maxU, PingType.BACKGROUND.minV).func_225586_a_(r, g, b, 255).endVertex();
            bufferBuilder.func_225582_a_(drawX + min, drawY + min, 0).func_225583_a_(PingType.BACKGROUND.minU, PingType.BACKGROUND.minV).func_225586_a_(r, g, b, 255).endVertex();
            tessellator.draw();

            // Button Icon
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.func_225582_a_(drawX + min, drawY + max, 0).func_225583_a_(type.minU, type.maxV).func_225586_a_(255, 255, 255, 255).endVertex();
            bufferBuilder.func_225582_a_(drawX + max, drawY + max, 0).func_225583_a_(type.maxU, type.maxV).func_225586_a_(255, 255, 255, 255).endVertex();
            bufferBuilder.func_225582_a_(drawX + max, drawY + min, 0).func_225583_a_(type.maxU, type.minV).func_225586_a_(255, 255, 255, 255).endVertex();
            bufferBuilder.func_225582_a_(drawX + min, drawY + min, 0).func_225583_a_(type.minU, type.minV).func_225586_a_(255, 255, 255, 255).endVertex();
            tessellator.draw();
        }
    }

    private static void renderText() {
        Minecraft mc = Minecraft.getInstance();
        int numOfItems = PingType.values().length - 1;

        final double mouseX = mc.mouseHelper.getMouseX() * ((double) mc.func_228018_at_().getScaledWidth() / mc.func_228018_at_().getWidth());
        final double mouseY = mc.mouseHelper.getMouseY() * ((double) mc.func_228018_at_().getScaledHeight() / mc.func_228018_at_().getHeight());

        int halfHeight = (ITEM_SIZE + ITEM_PADDING) / 2;
        int backgroundY = mc.func_228018_at_().getScaledHeight() / 4 - halfHeight;

        int half = numOfItems / 2;
        for (int i = 0; i < numOfItems; i++) {
            PingType type = PingType.values()[i + 1];
            int drawX = mc.func_228018_at_().getScaledWidth() / 2 - (ITEM_SIZE * half) - (ITEM_PADDING * (half));
            int drawY = mc.func_228018_at_().getScaledHeight() / 4;

            drawX += ITEM_SIZE / 2 + ITEM_PADDING / 2 + (ITEM_PADDING * i) + ITEM_SIZE * i;

            boolean mouseIn = mouseX >= (drawX - ITEM_SIZE * 0.5D) && mouseX <= (drawX + ITEM_SIZE * 0.5D) &&
                    mouseY >= (drawY - ITEM_SIZE * 0.5D) && mouseY <= (drawY + ITEM_SIZE * 0.5D);

            if (mouseIn) {
                RenderSystem.pushMatrix();
                RenderSystem.color4f(255, 255, 255, 255);
                mc.fontRenderer.drawString(type.toString(), mc.func_228018_at_().getScaledWidth() / 2 - mc.fontRenderer.getStringWidth(type.toString()) / 2, backgroundY + halfHeight * 2, 0xFFFFFF);
                RenderSystem.popMatrix();
            }
        }
    }
}