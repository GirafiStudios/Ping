package dmillerw.ping.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import dmillerw.ping.client.gui.CompatibleScaledResolution;
import dmillerw.ping.client.gui.GuiPingSelect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * @author dmillerw
 */
public class KeyHandler {

    public static final KeyBinding KEY_BINDING = new KeyBinding("key.ping", Keyboard.KEY_F, "key.categories.misc");

    public static void register() {
        ClientRegistry.registerKeyBinding(KEY_BINDING);
        FMLCommonHandler.instance().bus().register(new KeyHandler());
    }

    private static boolean lastKeyState = false;

    public static boolean ignoreNextRelease = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();

        if (mc.theWorld == null) {
            return;
        }

        boolean keyPressed = (KEY_BINDING.getKeyCode() >= 0 ? Keyboard.isKeyDown(KEY_BINDING.getKeyCode()) : Mouse.isButtonDown(KEY_BINDING.getKeyCode() + 100));

        if (keyPressed != lastKeyState) {
            if (keyPressed) {
                GuiPingSelect.activate();
            } else {
                if (!ignoreNextRelease) {
                    final CompatibleScaledResolution scaledresolution = new CompatibleScaledResolution(mc, mc.displayWidth, mc.displayHeight);
                    int i = scaledresolution.getScaledWidth();
                    int j = scaledresolution.getScaledHeight();
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
