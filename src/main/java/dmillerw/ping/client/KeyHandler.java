package dmillerw.ping.client;

import com.mojang.blaze3d.platform.InputConstants;
import dmillerw.ping.Ping;
import dmillerw.ping.client.gui.PingSelectGui;
import dmillerw.ping.data.PingType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Ping.MOD_ID, value = Dist.CLIENT)
public class KeyHandler {
    private static final String PING_CATEGORY = "ping:key.categories.ping";
    static final KeyMapping KEY_BINDING = new KeyMapping("key.ping", GLFW.GLFW_KEY_V, PING_CATEGORY);
    static final KeyMapping PING_ALERT = new KeyMapping("ping.key.alert", GLFW.GLFW_KEY_KP_4, PING_CATEGORY);
    static final KeyMapping PING_MINE = new KeyMapping("ping.key.mine", GLFW.GLFW_KEY_KP_5, PING_CATEGORY);
    static final KeyMapping PING_LOOK = new KeyMapping("ping.key.look", GLFW.GLFW_KEY_KP_6, PING_CATEGORY);
    static final KeyMapping PING_GOTO = new KeyMapping("ping.key.goto", GLFW.GLFW_KEY_KP_8, PING_CATEGORY);

    private static boolean lastKeyState = false;
    public static boolean ignoreNextRelease = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null) {
            return;
        }

        long handle = Minecraft.getInstance().getWindow().getWindow();
        int keycode = KEY_BINDING.getKey().getValue();
        if (keycode >= 0) {
            boolean keyPressed = (KEY_BINDING.matchesMouse(keycode) ? GLFW.glfwGetMouseButton(handle, keycode) == 1 : InputConstants.isKeyDown(handle, keycode));

            if (keyPressed != lastKeyState) {
                if (keyPressed) {
                    PingSelectGui.activate();
                } else {
                    if (!ignoreNextRelease) {
                        final double mouseX = mc.mouseHandler.xpos() * ((double) mc.getWindow().getGuiScaledWidth() / mc.getWindow().getScreenWidth());
                        final double mouseY = mc.mouseHandler.ypos() * ((double) mc.getWindow().getGuiScaledHeight() / mc.getWindow().getScreenHeight());

                        PingSelectGui.INSTANCE.mouseClicked(mouseX, mouseY, 0);
                    }
                    ignoreNextRelease = false;
                    PingSelectGui.deactivate();
                }
            }
            lastKeyState = keyPressed;
        }

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

    private static boolean canSendQuickPing(KeyMapping keyBinding) {
        return keyBinding.isDown(); //Will continuously be triggered when key is held down due to vanilla issue.
    }
}