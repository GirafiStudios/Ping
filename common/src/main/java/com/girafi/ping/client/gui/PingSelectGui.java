package com.girafi.ping.client.gui;

import com.girafi.ping.client.PingHandlerHelper;
import com.girafi.ping.client.gui.widget.PingButton;
import com.girafi.ping.data.PingType;
import com.girafi.ping.util.PingConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
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
        if (Minecraft.getInstance().screen == INSTANCE) {
            active = false;
            Minecraft.getInstance().setScreen(null);
        }
    }

    @Override
    protected void init() {
        super.init();

        int numOfItems = PingType.values().length - 1;
        int half = numOfItems / 2;
        int x = this.width / 2 - (ITEM_SIZE * half) - (ITEM_PADDING * (half));
        int y = this.height / 4;

        this.addRenderableWidget(new PingButton(PingHandlerHelper.ALERT_BUTTON, PingType.ALERT, x + 8, y - 16));
        this.addRenderableWidget(new PingButton(PingHandlerHelper.MINE_BUTTON, PingType.MINE, x + 48, y - 16));
        this.addRenderableWidget(new PingButton(PingHandlerHelper.LOOK_BUTTON, PingType.LOOK, x + 88, y - 16));
        this.addRenderableWidget(new PingButton(PingHandlerHelper.GOTO_BUTTON, PingType.GOTO, x + 128, y - 16));
    }

    @Override
    public void renderBackground(@Nonnull GuiGraphics guiGraphics, int i, int i1, float i2) {
        if (PingConfig.VISUAL.menuBackground.get()) {
            int halfWidth = (ITEM_SIZE * 4) - (ITEM_PADDING * 4);
            int halfHeight = (ITEM_SIZE + ITEM_PADDING) / 2;
            int backgroundX = this.width / 2 - halfWidth;
            int backgroundY = this.height / 4 - halfHeight;
            guiGraphics.fillGradient(backgroundX, backgroundY, this.width / 2 + halfWidth, ((this.height / 4) + (halfHeight * 2)) - 10, -1072689136, -804253680);
        }
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
}