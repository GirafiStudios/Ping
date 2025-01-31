package com.girafi.ping.client.gui;

import com.girafi.ping.client.ClientHandlerBase;
import com.girafi.ping.client.PingHandlerHelper;
import com.girafi.ping.client.PingKeybinds;
import com.girafi.ping.data.PingType;
import com.girafi.ping.util.PingConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
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
                ClientHandlerBase.sendPing(type);
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
        // Don't render default background
    }

    @Override
    public void render(@Nonnull GuiGraphics guiGraphics, int i, int i2, float f) {
        super.render(guiGraphics, i, i2, f);
        Minecraft mc = minecraft;
        if (mc != null && mc.level != null && !mc.options.hideGui && !mc.isPaused() && PingSelectGui.active) {
            renderPingBackground(guiGraphics);
            renderGui(guiGraphics);
            renderText(guiGraphics);
        }
    }

    public void renderPingBackground(GuiGraphics guiGraphics) {
        if (PingConfig.VISUAL.menuBackground.get() && minecraft != null) {
            int halfWidth = (ITEM_SIZE * 4) - (ITEM_PADDING * 4);
            int halfHeight = (ITEM_SIZE + ITEM_PADDING) / 2;
            int backgroundX = minecraft.getWindow().getGuiScaledWidth() / 2 - halfWidth;
            int backgroundY = minecraft.getWindow().getGuiScaledHeight() / 4 - halfHeight;
            guiGraphics.fillGradient(RenderType.gui(), backgroundX, backgroundY, this.width / 2 + halfWidth, ((this.height / 4) + (halfHeight * 2)) - 10, -1072689136, -804253680, 0);
        }
    }

    public static void renderGui(GuiGraphics guiGraphics) {
        int numOfItems = PingType.values().length - 1;
        PoseStack poseStack = guiGraphics.pose();
        Minecraft mc = Minecraft.getInstance();

        poseStack.pushPose();
        MultiBufferSource.BufferSource source = mc.renderBuffers().bufferSource();
        VertexConsumer vertexConsumer = source.getBuffer(RenderType.guiTextured(PingHandlerHelper.TEXTURE));
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
            if (mouseIn) {
                r = PingConfig.VISUAL.pingR.get();
                g = PingConfig.VISUAL.pingG.get();
                b = PingConfig.VISUAL.pingB.get();
            }
            vertexConsumer.addVertex(drawX + min, drawY + max, 0).setUv(PingType.BACKGROUND.getMinU(), PingType.BACKGROUND.getMaxV()).setColor(r, g, b, 255);
            vertexConsumer.addVertex(drawX + max, drawY + max, 0).setUv(PingType.BACKGROUND.getMaxU(), PingType.BACKGROUND.getMaxV()).setColor(r, g, b, 255);
            vertexConsumer.addVertex(drawX + max, drawY + min, 0).setUv(PingType.BACKGROUND.getMaxU(), PingType.BACKGROUND.getMinV()).setColor(r, g, b, 255);
            vertexConsumer.addVertex(drawX + min, drawY + min, 0).setUv(PingType.BACKGROUND.getMinU(), PingType.BACKGROUND.getMinV()).setColor(r, g, b, 255);

            // Button Icon
            vertexConsumer.addVertex(drawX + min, drawY + max, 0).setUv(type.getMinU(), type.getMaxV()).setColor(255, 255, 255, 255);
            vertexConsumer.addVertex(drawX + max, drawY + max, 0).setUv(type.getMaxU(), type.getMaxV()).setColor(255, 255, 255, 255);
            vertexConsumer.addVertex(drawX + max, drawY + min, 0).setUv(type.getMaxU(), type.getMinV()).setColor(255, 255, 255, 255);
            vertexConsumer.addVertex(drawX + min, drawY + min, 0).setUv(type.getMinU(), type.getMinV()).setColor(255, 255, 255, 255);
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