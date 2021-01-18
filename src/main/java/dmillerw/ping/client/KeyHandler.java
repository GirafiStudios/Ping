package dmillerw.ping.client;

import dmillerw.ping.Ping;
import dmillerw.ping.client.gui.PingSelectGui;
import dmillerw.ping.data.PingType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Ping.MOD_ID, value = Dist.CLIENT)
public class KeyHandler {
    private static final String PING_CATEGORY = "ping:key.categories.ping";
    static final KeyBinding KEY_BINDING = new KeyBinding("key.ping", GLFW.GLFW_KEY_V, PING_CATEGORY);
    static final KeyBinding PING_ALERT = new KeyBinding("ping.key.alert", GLFW.GLFW_KEY_KP_4, PING_CATEGORY);
    static final KeyBinding PING_MINE = new KeyBinding("ping.key.mine", GLFW.GLFW_KEY_KP_5, PING_CATEGORY);
    static final KeyBinding PING_LOOK = new KeyBinding("ping.key.look", GLFW.GLFW_KEY_KP_6, PING_CATEGORY);
    static final KeyBinding PING_GOTO = new KeyBinding("ping.key.goto", GLFW.GLFW_KEY_KP_8, PING_CATEGORY);

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

        long handle = Minecraft.getInstance().getMainWindow().getHandle();
        int keycode = KEY_BINDING.getKey().getKeyCode();
        if (keycode >= 0) {
            boolean keyPressed = (KEY_BINDING.matchesMouseKey(keycode) ? GLFW.glfwGetMouseButton(handle, keycode) == 1 : InputMappings.isKeyDown(handle, keycode));

            if (keyPressed != lastKeyState) {
                if (keyPressed) {
                    PingSelectGui.activate();
                } else {
                    if (!ignoreNextRelease) {
                        final double mouseX = mc.mouseHelper.getMouseX() * ((double) mc.getMainWindow().getScaledWidth() / mc.getMainWindow().getWidth());
                        final double mouseY = mc.mouseHelper.getMouseY() * ((double) mc.getMainWindow().getScaledHeight() / mc.getMainWindow().getHeight());

                        PingSelectGui.INSTANCE.mouseClicked(mouseX, mouseY, 0);
                    }
                    ignoreNextRelease = false;
                    PingSelectGui.deactivate();
                }
            }
            lastKeyState = keyPressed;

            if (canSendQuickPing(PING_ALERT)) {
                ClientHandler.sendPing(PingType.ALERT);
            } else if (canSendQuickPing(PING_MINE)) {
                ClientHandler.sendPing(PingType.MINE);
            } else if (canSendQuickPing(PING_LOOK)) {
                ClientHandler.sendPing(PingType.LOOK);
            } else if (canSendQuickPing(PING_GOTO)) {
                ClientHandler.sendPing(PingType.GOTO);
            }
        }
    }

    private static boolean canSendQuickPing(KeyBinding keyBinding) {
        return keyBinding.isKeyDown(); //Will continuously be triggered when key is held down due to vanilla issue.
    }
}