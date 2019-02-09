package dmillerw.ping.client;

import dmillerw.ping.client.gui.CompatibleScaledResolution;
import dmillerw.ping.client.gui.GuiPingSelect;
import dmillerw.ping.data.PingType;
import dmillerw.ping.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT)
public class KeyHandler {
    private static final String PING_CATEOGRY = "ping:key.categories.ping";
    public static final KeyBinding KEY_BINDING = new KeyBinding("key.ping", 86, PING_CATEOGRY); //V
    public static final KeyBinding PING_ALERT = new KeyBinding("ping.key.alert", 324, PING_CATEOGRY); //Numpad 4
    public static final KeyBinding PING_MINE = new KeyBinding("ping.key.mine", 325, PING_CATEOGRY); //Numpad 5
    public static final KeyBinding PING_LOOK = new KeyBinding("ping.key.look", 326, PING_CATEOGRY); //Numpad 6
    public static final KeyBinding PING_GOTO = new KeyBinding("ping.key.goto", 328, PING_CATEOGRY); //Numpad 8

    private static boolean lastKeyState = false;
    public static boolean ignoreNextRelease = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();

        if (mc.world == null) {
            return;
        }

        boolean keyPressed = (KEY_BINDING.getKey().getKeyCode() >= 0 ? InputMappings.isKeyDown(KEY_BINDING.getKey().getKeyCode()) : InputMappings.isKeyDown(KEY_BINDING.getKey().getKeyCode() + 100));

        if (keyPressed != lastKeyState) {
            if (keyPressed) {
                GuiPingSelect.activate();
            } else {
                if (!ignoreNextRelease) {
                    final CompatibleScaledResolution scaledResolution = new CompatibleScaledResolution(mc, mc.mainWindow.getWidth(), mc.mainWindow.getHeight());
                    int i = scaledResolution.getScaledWidth();
                    int j = scaledResolution.getScaledHeight();
                    final int k = (int) (mc.mouseHelper.getMouseX() * i / mc.mainWindow.getWidth());
                    final int l = (int) (j - mc.mouseHelper.getMouseY() * j / mc.mainWindow.getHeight() - 1);

                    GuiPingSelect.INSTANCE.mouseClicked(k, l, 0);
                }
                ignoreNextRelease = false;
                GuiPingSelect.deactivate();
            }
        }
        lastKeyState = keyPressed;

        if (PING_ALERT.isPressed()) {
            ClientHandler.sendPing(PingType.ALERT);
        } else if (PING_MINE.isPressed()) {
            ClientHandler.sendPing(PingType.MINE);
        } else if (PING_LOOK.isPressed()) {
            ClientHandler.sendPing(PingType.LOOK);
        } else if (PING_GOTO.isPressed()) {
            ClientHandler.sendPing(PingType.GOTO);
        }
    }
}