package com.girafi.ping.client;

import com.girafi.ping.client.gui.PingSelectGui;
import com.girafi.ping.data.PingType;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class KeyHelper {
    private static boolean quickPingAlertHeld = false;
    private static boolean quickPingMineHeld = false;
    private static boolean quickPingLookHeld = false;
    private static boolean quickPingGotoHeld = false;
    private static boolean wasPingMenuKeyPressed = false;


    public static void onTick() {
        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null) {
            return;
        }

        Window window = Minecraft.getInstance().getWindow();
        long handle = window.handle();

        //PING MENU
        KeyMapping pingKey = PingKeybinds.KEY_BINDING;
        int pingKeycode = pingKey.key.getValue();
        boolean isPingMenuKeyPressed = pingKey.key.getType() == InputConstants.Type.MOUSE ? GLFW.glfwGetMouseButton(handle, pingKeycode) == GLFW.GLFW_PRESS : InputConstants.isKeyDown(window, pingKeycode);

        if (isPingMenuKeyPressed && !wasPingMenuKeyPressed) {
            PingSelectGui.activate();
        }
        if (!isPingMenuKeyPressed && wasPingMenuKeyPressed) {
            if (mc.screen instanceof PingSelectGui) {
                PingSelectGui.deactivate();
            }
        }
        wasPingMenuKeyPressed = isPingMenuKeyPressed;

        //ALERT
        KeyMapping alertKey = PingKeybinds.PING_ALERT;
        int alertKeycode = alertKey.key.getValue();
        boolean quickPingDownAlert = alertKey.key.getType() == InputConstants.Type.MOUSE ? GLFW.glfwGetMouseButton(handle, alertKeycode) == GLFW.GLFW_PRESS : alertKey.isDown();

        if (quickPingDownAlert && !quickPingAlertHeld) {
            while (PingKeybinds.PING_ALERT.consumeClick()) {
                ClientHandlerBase.sendPing(PingType.ALERT);
            }
        }
        quickPingAlertHeld = quickPingDownAlert;

        //MINE
        KeyMapping mineKey = PingKeybinds.PING_MINE;
        int mineKeykode = mineKey.key.getValue();
        boolean quickPingDownMine = mineKey.key.getType() == InputConstants.Type.MOUSE ? GLFW.glfwGetMouseButton(handle, mineKeykode) == GLFW.GLFW_PRESS : mineKey.isDown();

        if (quickPingDownMine && !quickPingMineHeld) {
            while (PingKeybinds.PING_MINE.consumeClick()) {
                ClientHandlerBase.sendPing(PingType.MINE);
            }
        }
        quickPingMineHeld = quickPingDownMine;

        //LOOK
        KeyMapping lookKey = PingKeybinds.PING_LOOK;
        int lookKeycode = lookKey.key.getValue();
        boolean quickPingDownLook = lookKey.key.getType() == InputConstants.Type.MOUSE ? GLFW.glfwGetMouseButton(handle, lookKeycode) == GLFW.GLFW_PRESS : lookKey.isDown();

        if (quickPingDownLook && !quickPingLookHeld) {
            while (PingKeybinds.PING_LOOK.consumeClick()) {
                ClientHandlerBase.sendPing(PingType.LOOK);
            }
        }
        quickPingLookHeld = quickPingDownLook;

        //GOTO
        KeyMapping gotoKey = PingKeybinds.PING_GOTO;
        int gotoKeycode = gotoKey.key.getValue();
        boolean quickPingDownGoto = gotoKey.key.getType() == InputConstants.Type.MOUSE ? GLFW.glfwGetMouseButton(handle, gotoKeycode) == GLFW.GLFW_PRESS : gotoKey.isDown();

        if (quickPingDownGoto && !quickPingGotoHeld) {
            while (PingKeybinds.PING_GOTO.consumeClick()) {
                ClientHandlerBase.sendPing(PingType.GOTO);
            }
        }
        quickPingGotoHeld = quickPingDownGoto;
    }
}