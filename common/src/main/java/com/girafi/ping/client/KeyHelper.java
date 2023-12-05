package com.girafi.ping.client;

import com.girafi.ping.client.gui.PingSelectGui;
import com.girafi.ping.data.PingType;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class KeyHelper {

    public static void onTick() {
        PingHandlerHelper.pingTimer();

        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null) {
            return;
        }

        long handle = Minecraft.getInstance().getWindow().getWindow();
        int keycode = PingKeybinds.KEY_BINDING.getDefaultKey().getValue();
        if (keycode >= 0) {
            boolean keyPressed = (PingKeybinds.KEY_BINDING.matchesMouse(keycode) ? GLFW.glfwGetMouseButton(handle, keycode) == 1 : InputConstants.isKeyDown(handle, keycode));

            if (keyPressed != PingKeybinds.Helper.lastKeyState) {
                if (keyPressed) {
                    PingSelectGui.activate();
                } else {
                    if (!PingKeybinds.Helper.ignoreNextRelease) {
                        final double mouseX = mc.mouseHandler.xpos() * ((double) mc.getWindow().getGuiScaledWidth() / mc.getWindow().getScreenWidth());
                        final double mouseY = mc.mouseHandler.ypos() * ((double) mc.getWindow().getGuiScaledHeight() / mc.getWindow().getScreenHeight());

                        PingSelectGui.INSTANCE.mouseClicked(mouseX, mouseY, 0);
                    }
                    PingKeybinds.Helper.ignoreNextRelease = false;
                    PingSelectGui.deactivate();
                }
            }
            PingKeybinds.Helper.lastKeyState = keyPressed;
        }

        if (canSendQuickPing(PingKeybinds.PING_ALERT)) {
            ClientHandlerBase.sendPing(PingType.ALERT);
        } else if (canSendQuickPing(PingKeybinds.PING_MINE)) {
            ClientHandlerBase.sendPing(PingType.MINE);
        } else if (canSendQuickPing(PingKeybinds.PING_LOOK)) {
            ClientHandlerBase.sendPing(PingType.LOOK);
        } else if (canSendQuickPing(PingKeybinds.PING_GOTO)) {
            ClientHandlerBase.sendPing(PingType.GOTO);
        }
    }

    private static boolean canSendQuickPing(KeyMapping keyBinding) {
        return keyBinding.isDown(); //Will continuously be triggered when key is held down due to vanilla issue.
    }
}
