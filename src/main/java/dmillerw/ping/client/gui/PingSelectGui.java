package dmillerw.ping.client.gui;

import dmillerw.ping.client.ClientHandler;
import dmillerw.ping.client.KeyHandler;
import dmillerw.ping.data.PingType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static dmillerw.ping.client.RenderHandler.ITEM_PADDING;
import static dmillerw.ping.client.RenderHandler.ITEM_SIZE;

@OnlyIn(Dist.CLIENT)
public class PingSelectGui extends Screen {
    public static final PingSelectGui INSTANCE = new PingSelectGui();
    public static boolean active = false;

    public PingSelectGui() {
        super(new TranslationTextComponent("ping.pingSelect.title"));
    }

    public static void activate() {
        if (Minecraft.getInstance().currentScreen == null) {
            active = true;
            Minecraft.getInstance().displayGuiScreen(INSTANCE);
        }
    }

    public static void deactivate() {
        active = false;
        if (Minecraft.getInstance().currentScreen == INSTANCE) {
            Minecraft.getInstance().displayGuiScreen(null);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Minecraft mc = Minecraft.getInstance();
        int numOfItems = PingType.values().length - 1;

        int half = numOfItems / 2;
        for (int i = 0; i < numOfItems; i++) {
            PingType type = PingType.values()[i + 1];
            int drawX = mc.func_228018_at_().getScaledWidth() / 2 - (ITEM_SIZE * half) - (ITEM_PADDING * (half));
            int drawY = mc.func_228018_at_().getScaledHeight() / 4;

            drawX += ITEM_SIZE / 2 + ITEM_PADDING / 2 + (ITEM_PADDING * i) + ITEM_SIZE * i;

            boolean mouseIn = mouseX >= (drawX - ITEM_SIZE * 0.5D) && mouseX <= (drawX + ITEM_SIZE * 0.5D) &&
                    mouseY >= (drawY - ITEM_SIZE * 0.5D) && mouseY <= (drawY + ITEM_SIZE * 0.5D);

            if (mouseIn) {
                ClientHandler.sendPing(type);
                KeyHandler.ignoreNextRelease = true;
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
}