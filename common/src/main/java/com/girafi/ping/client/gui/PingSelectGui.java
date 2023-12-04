package com.girafi.ping.client.gui;

import com.girafi.ping.PingCommon;
import com.girafi.ping.client.ClientHandlerBase;
import com.girafi.ping.client.PingHandlerHelper;
import com.girafi.ping.client.PingKeybinds;
import com.girafi.ping.data.PingType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

public class PingSelectGui extends Screen {
    public static final PingSelectGui INSTANCE = new PingSelectGui();
    public static final int ITEM_PADDING = 10;
    public static final int ITEM_SIZE = 32;
    public static boolean active = false;

    public PingSelectGui() {
        super(Component.translatable("ping.pingSelect.title"));
    }

    public static void activate() {
        if (Minecraft.getInstance().screen == null) {
            active = true;
            Minecraft.getInstance().setScreen(INSTANCE);
        }
    }

    public static void deactivate() {
        active = false;
        if (Minecraft.getInstance().screen == INSTANCE) {
            Minecraft.getInstance().setScreen(null);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Minecraft mc = Minecraft.getInstance();
        int numOfItems = PingType.values().length - 1;

        int half = numOfItems / 2;
        for (int i = 0; i < numOfItems; i++) {
            PingType type = PingType.values()[i + 1];
            int drawX = mc.getWindow().getGuiScaledWidth() / 2 - (ITEM_SIZE * half) - (ITEM_PADDING * (half));
            int drawY = mc.getWindow().getGuiScaledHeight() / 4;

            drawX += ITEM_SIZE / 2 + ITEM_PADDING / 2 + (ITEM_PADDING * i) + ITEM_SIZE * i;

            boolean mouseIn = mouseX >= (drawX - ITEM_SIZE * 0.5D) && mouseX <= (drawX + ITEM_SIZE * 0.5D) &&
                    mouseY >= (drawY - ITEM_SIZE * 0.5D) && mouseY <= (drawY + ITEM_SIZE * 0.5D);

            if (mouseIn) {
                System.out.println("Mouse click");
                ClientHandlerBase.INSTANCE.sendPing(type);
                PingKeybinds.Helper.ignoreNextRelease = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void removed() {
        super.removed();
        active = false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void renderBackground(@Nonnull GuiGraphics guiGraphics, int i, int i1, float i2) {
        // Menu Background
        if (PingCommon.config().renderMenuBackground) {
            int halfWidth = (ITEM_SIZE * 4) - (ITEM_PADDING * 4);
            int halfHeight = (ITEM_SIZE + ITEM_PADDING) / 2;
            int backgroundX = minecraft.getWindow().getGuiScaledWidth() / 2 - halfWidth;
            int backgroundY = minecraft.getWindow().getGuiScaledHeight() / 4 - halfHeight;
            guiGraphics.fillGradient(backgroundX, backgroundY, this.width / 2 + halfWidth, this.height / 3 + (halfHeight / 2), -1072689136, -804253680);
        }
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int i, int i2, float f) {
        super.render(guiGraphics, i, i2, f);
        Minecraft mc = minecraft;
        if (mc != null && mc.level != null && !mc.options.hideGui && !mc.isPaused() && PingSelectGui.active) {
            renderGui(guiGraphics);
            renderText(guiGraphics);
        }
    }

    public static void renderGui(GuiGraphics guiGraphics) {
        int numOfItems = PingType.values().length - 1;
        PoseStack poseStack = guiGraphics.pose();

        Minecraft mc = Minecraft.getInstance();
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();

        poseStack.pushPose();
        RenderSystem.setShaderTexture(0, PingHandlerHelper.TEXTURE);
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
                r = PingCommon.config().pingColorRed;
                g = PingCommon.config().pingColorGreen;
                b = PingCommon.config().pingColorBlue;
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

    public static void renderText(GuiGraphics guiGraphics) {
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
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                guiGraphics.drawString(mc.font, type.toString(), mc.getWindow().getGuiScaledWidth() / 2 - mc.font.width(type.toString()) / 2, backgroundY + halfHeight * 2, 0xFFFFFF, true);
            }
        }
    }
}