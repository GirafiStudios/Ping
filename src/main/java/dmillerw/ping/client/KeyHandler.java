package dmillerw.ping.client;

import dmillerw.ping.client.gui.CompatibleScaledResolution;
import dmillerw.ping.client.gui.GuiPingSelect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@EventBusSubscriber(value = Side.CLIENT)
public class KeyHandler {
    public static final KeyBinding KEY_BINDING = new KeyBinding("key.ping", Keyboard.KEY_V, "key.categories.misc");

    private static boolean lastKeyState = false;
    public static boolean ignoreNextRelease = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();

        if (mc.world == null) {
            return;
        }

        boolean keyPressed = (KEY_BINDING.getKeyCode() >= 0 ? Keyboard.isKeyDown(KEY_BINDING.getKeyCode()) : Mouse.isButtonDown(KEY_BINDING.getKeyCode() + 100));

        if (keyPressed != lastKeyState) {
            if (keyPressed) {
                GuiPingSelect.activate();
            } else {
                if (!ignoreNextRelease) {
                    final CompatibleScaledResolution scaledResolution = new CompatibleScaledResolution(mc, mc.displayWidth, mc.displayHeight);
                    int i = scaledResolution.getScaledWidth();
                    int j = scaledResolution.getScaledHeight();
                    final int k = Mouse.getX() * i / mc.displayWidth;
                    final int l = j - Mouse.getY() * j / mc.displayHeight - 1;

                    GuiPingSelect.INSTANCE.mouseClicked(k, l, 0);
                }
                ignoreNextRelease = false;
                GuiPingSelect.deactivate();
            }
        }
        lastKeyState = keyPressed;
    }
}