package dmillerw.ping.client;

import dmillerw.ping.Ping;
import dmillerw.ping.client.gui.PingSelectGui;
import dmillerw.ping.data.PingType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.gameevent.TickEvent;
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

        long handle = Minecraft.getInstance().mainWindow.getHandle();
        boolean keyPressed = (KEY_BINDING.getKey().getKeyCode() >= 0 ? InputMappings.func_216506_a(handle, KEY_BINDING.getKey().getKeyCode()) : InputMappings.func_216506_a(handle, KEY_BINDING.getKey().getKeyCode() + 100));

        if (keyPressed != lastKeyState) {
            if (keyPressed) {
                PingSelectGui.activate();
            } else {
                if (!ignoreNextRelease) {
                    final double mouseX = mc.mouseHelper.getMouseX() * ((double) mc.mainWindow.getScaledWidth() / mc.mainWindow.getWidth());
                    final double mouseY = mc.mouseHelper.getMouseY() * ((double) mc.mainWindow.getScaledHeight() / mc.mainWindow.getHeight());

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

    private static boolean canSendQuickPing(KeyBinding keyBinding) {
        return keyBinding.isKeyDown() && !keyBinding.isPressed();
    }
}