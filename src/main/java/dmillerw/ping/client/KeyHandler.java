package dmillerw.ping.client;

import dmillerw.ping.client.gui.CompatibleScaledResolution;
import dmillerw.ping.client.gui.GuiPingSelect;
import dmillerw.ping.data.PingType;
import dmillerw.ping.proxy.ClientProxy;
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
    public static final String PING_CATEOGRY = "ping:key.categories.ping";
    public static final KeyBinding KEY_BINDING = new KeyBinding("key.ping", Keyboard.KEY_V, PING_CATEOGRY);
    public static final KeyBinding PING_ALERT = new KeyBinding("ping:key.alert", Keyboard.KEY_NUMPAD4, PING_CATEOGRY);
    public static final KeyBinding PING_MINE = new KeyBinding("ping:key.mine", Keyboard.KEY_NUMPAD5, PING_CATEOGRY);
    public static final KeyBinding PING_LOOK = new KeyBinding("ping:key.look", Keyboard.KEY_NUMPAD6, PING_CATEOGRY);
    public static final KeyBinding PING_GOTO = new KeyBinding("ping:key.goto", Keyboard.KEY_NUMPAD8, PING_CATEOGRY);

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

        if (PING_ALERT.isPressed()) {
            ClientProxy.sendPing(PingType.ALERT);
        } else if (PING_MINE.isPressed()) {
            ClientProxy.sendPing(PingType.MINE);
        } else if (PING_LOOK.isPressed()) {
            ClientProxy.sendPing(PingType.LOOK);
        } else if (PING_GOTO.isPressed()) {
            ClientProxy.sendPing(PingType.GOTO);
        }
    }
}