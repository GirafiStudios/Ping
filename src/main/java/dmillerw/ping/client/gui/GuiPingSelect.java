package dmillerw.ping.client.gui;

import dmillerw.ping.data.PingType;
import dmillerw.ping.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import static dmillerw.ping.client.RenderHandler.ITEM_PADDING;
import static dmillerw.ping.client.RenderHandler.ITEM_SIZE;

/**
 * @author dmillerw
 */
public class GuiPingSelect extends GuiScreen {

    public static final GuiPingSelect INSTANCE = new GuiPingSelect();

    public static boolean active = false;

    public static void activate() {
        if (Minecraft.getMinecraft().currentScreen == null) {
            active = true;
            Minecraft.getMinecraft().displayGuiScreen(INSTANCE);
        }
    }

    public static void deactivate() {
        active = false;
        if (Minecraft.getMinecraft().currentScreen == INSTANCE) {
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        Minecraft mc = Minecraft.getMinecraft();
        CompatibleScaledResolution resolution = new CompatibleScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int numOfItems = PingType.values().length - 1;

        float half = numOfItems / 2;
        for (int i=0; i<numOfItems; i++) {
            PingType type = PingType.values()[i + 1];
            float drawX = resolution.getScaledWidth() / 2 - (ITEM_SIZE * half) - (ITEM_PADDING * (half));
            float drawY = resolution.getScaledHeight() / 4;

            drawX += ITEM_SIZE / 2 + ITEM_PADDING / 2 + (ITEM_PADDING * i) + ITEM_SIZE * i;

            boolean mouseIn = mouseX >= (drawX - ITEM_SIZE / 2) && mouseX <= (drawX + ITEM_SIZE / 2) &&
                    mouseY >= (drawY - ITEM_SIZE / 2) && mouseY <= (drawY + ITEM_SIZE / 2);

            if (mouseIn) {
                PacketHandler.sendPing(type);
                return;
            }
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        active = false;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
