package dmillerw.ping.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dmillerw.ping.Ping;
import dmillerw.ping.client.gui.PingSelectGui;
import dmillerw.ping.data.PingType;
import dmillerw.ping.util.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
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
            if ((mc.level == null || mc.isPaused()) && PingSelectGui.active) {
                PingSelectGui.deactivate();
            }
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent event) {
        if (!(event instanceof RenderGuiOverlayEvent.Post)) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null && !mc.options.hideGui && !mc.isPaused() && PingSelectGui.active) {
            renderGui(event.getPoseStack());
            renderText(event.getPoseStack());
        }
    }

    private static void renderGui(PoseStack poseStack) {
        int numOfItems = PingType.values().length - 1;

        Minecraft mc = Minecraft.getInstance();
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();

        // Menu Background
        if (Config.VISUAL.menuBackground.get()) {
            poseStack.pushPose();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);

            int halfWidth = (ITEM_SIZE * (numOfItems)) - (ITEM_PADDING * (numOfItems));
            int halfHeight = (ITEM_SIZE + ITEM_PADDING) / 2;
            int backgroundX = mc.getWindow().getGuiScaledWidth() / 2 - halfWidth;
            int backgroundY = mc.getWindow().getGuiScaledHeight() / 4 - halfHeight;

            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            bufferBuilder.vertex(backgroundX, backgroundY + 15 + halfHeight * 2, 0).color(0F, 0F, 0F, 0.5F).endVertex();
            bufferBuilder.vertex(backgroundX + halfWidth * 2, backgroundY + 15 + halfHeight * 2, 0).color(0F, 0F, 0F, 0.5F).endVertex();
            bufferBuilder.vertex(backgroundX + halfWidth * 2, backgroundY, 0).color(0F, 0F, 0F, 0.5F).endVertex();
            bufferBuilder.vertex(backgroundX, backgroundY, 0).color(0F, 0F, 0F, 0.5F).endVertex();
            tessellator.end();

            RenderSystem.disableBlend();
            RenderSystem.enableTexture();
            poseStack.popPose();
        }
        poseStack.pushPose();
        RenderSystem.setShaderTexture(0, PingHandler.TEXTURE);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

        final double mouseX = mc.mouseHandler.xpos() * ((double) mc.getWindow().getGuiScaledWidth() / mc.getWindow().getScreenWidth());
        final double mouseY = mc.mouseHandler.ypos() * ((double) mc.getWindow().getGuiScaledHeight() / mc.getWindow().getScreenHeight());

        int half = numOfItems / 2;
        for (int i = 0; i < numOfItems; i++) {
            PingType type = PingType.values()[i + 1];
            int drawX = mc.getWindow().getGuiScaledWidth() / 2 - (ITEM_SIZE * half) - (ITEM_PADDING * (half));
            int drawY = mc.getWindow().getGuiScaledHeight() / 4;

            drawX += ITEM_SIZE / 2 + ITEM_PADDING / 2 + (ITEM_PADDING * i) + ITEM_SIZE * i;

            boolean mouseIn = mouseX >= (drawX - ITEM_SIZE * 0.5D) && mouseX <= (drawX + ITEM_SIZE * 0.5D) &&
                    mouseY >= (drawY - ITEM_SIZE * 0.5D) && mouseY <= (drawY + ITEM_SIZE * 0.5D);

            float min = -ITEM_SIZE / 2.0F;
            float max = ITEM_SIZE / 2.0F;

            int r = 255;
            int g = 255;
            int b = 255;

            // Button Background
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            if (mouseIn) {
                r = Config.VISUAL.pingR.get();
                g = Config.VISUAL.pingG.get();
                b = Config.VISUAL.pingB.get();
            }
            bufferBuilder.vertex(drawX + min, drawY + max, 0).uv(PingType.BACKGROUND.getMinU(), PingType.BACKGROUND.getMaxV()).color(r, g, b, 255).endVertex();
            bufferBuilder.vertex(drawX + max, drawY + max, 0).uv(PingType.BACKGROUND.getMaxU(), PingType.BACKGROUND.getMaxV()).color(r, g, b, 255).endVertex();
            bufferBuilder.vertex(drawX + max, drawY + min, 0).uv(PingType.BACKGROUND.getMaxU(), PingType.BACKGROUND.getMinV()).color(r, g, b, 255).endVertex();
            bufferBuilder.vertex(drawX + min, drawY + min, 0).uv(PingType.BACKGROUND.getMinU(), PingType.BACKGROUND.getMinV()).color(r, g, b, 255).endVertex();
            tessellator.end();

            // Button Icon
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferBuilder.vertex(drawX + min, drawY + max, 0).uv(type.getMinU(), type.getMaxV()).color(255, 255, 255, 255).endVertex();
            bufferBuilder.vertex(drawX + max, drawY + max, 0).uv(type.getMaxU(), type.getMaxV()).color(255, 255, 255, 255).endVertex();
            bufferBuilder.vertex(drawX + max, drawY + min, 0).uv(type.getMaxU(), type.getMinV()).color(255, 255, 255, 255).endVertex();
            bufferBuilder.vertex(drawX + min, drawY + min, 0).uv(type.getMinU(), type.getMinV()).color(255, 255, 255, 255).endVertex();
            tessellator.end();
        }
        poseStack.popPose();
    }

    private static void renderText(PoseStack poseStack) {
        Minecraft mc = Minecraft.getInstance();
        int numOfItems = PingType.values().length - 1;

        final double mouseX = mc.mouseHandler.xpos() * ((double) mc.getWindow().getGuiScaledWidth() / mc.getWindow().getScreenWidth());
        final double mouseY = mc.mouseHandler.ypos() * ((double) mc.getWindow().getGuiScaledHeight() / mc.getWindow().getScreenHeight());

        int halfHeight = (ITEM_SIZE + ITEM_PADDING) / 2;
        int backgroundY = mc.getWindow().getGuiScaledHeight() / 4 - halfHeight;

        int half = numOfItems / 2;
        for (int i = 0; i < numOfItems; i++) {
            PingType type = PingType.values()[i + 1];
            int drawX = mc.getWindow().getGuiScaledWidth() / 2 - (ITEM_SIZE * half) - (ITEM_PADDING * (half));
            int drawY = mc.getWindow().getGuiScaledHeight() / 4;

            drawX += ITEM_SIZE / 2 + ITEM_PADDING / 2 + (ITEM_PADDING * i) + ITEM_SIZE * i;

            boolean mouseIn = mouseX >= (drawX - ITEM_SIZE * 0.5D) && mouseX <= (drawX + ITEM_SIZE * 0.5D) &&
                    mouseY >= (drawY - ITEM_SIZE * 0.5D) && mouseY <= (drawY + ITEM_SIZE * 0.5D);

            if (mouseIn) {
                poseStack.pushPose();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                mc.font.drawShadow(poseStack, type.toString(), mc.getWindow().getGuiScaledWidth() / 2.0F - mc.font.width(type.toString()) / 2.0F, backgroundY + halfHeight * 2, 0xFFFFFF);
                poseStack.popPose();
            }
        }
    }
}