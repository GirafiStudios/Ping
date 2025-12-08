package com.girafi.ping.client.gui.widget;

import com.girafi.ping.client.ClientHandlerBase;
import com.girafi.ping.client.PingHandlerHelper;
import com.girafi.ping.client.gui.PingSelectGui;
import com.girafi.ping.data.PingType;
import com.girafi.ping.util.PingConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;

public class PingButton extends Button {
    protected final ResourceLocation pingIcon;
    protected final PingType pingType;

    public PingButton(ResourceLocation pingIcon, PingType pingType, int x, int y) {
        super(x, y, 32, 32, Component.translatable("ping.key." + pingType.toString().toLowerCase()), b -> ClientHandlerBase.sendPing(pingType), Button.DEFAULT_NARRATION);
        this.pingIcon = pingIcon;
        this.pingType = pingType;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        guiGraphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                PingHandlerHelper.BUTTON,
                this.getX(),
                this.getY(),
                this.getWidth(),
                this.getHeight(),
                this.isHoveredOrFocused() ? ARGB.color(PingConfig.VISUAL.pingR.get(), PingConfig.VISUAL.pingG.get(), PingConfig.VISUAL.pingB.get()) : ARGB.white(this.alpha)
        );
        guiGraphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                this.pingIcon,
                this.getX(),
                this.getY(),
                this.getWidth(),
                this.getHeight(),
                ARGB.white(this.alpha)
        );

        if (this.isHoveredOrFocused()) {
            int halfHeight = (PingSelectGui.ITEM_SIZE + PingSelectGui.ITEM_PADDING) / 2;
            int backgroundY = mc.getWindow().getGuiScaledHeight() / 4 - halfHeight;
            String pingString = this.pingType.toString();
            guiGraphics.drawString(mc.font, pingString, mc.getWindow().getGuiScaledWidth() / 2 - mc.font.width(pingString) / 2, (backgroundY + halfHeight * 2) - 2, ARGB.white(this.alpha), true);
        }
    }
}