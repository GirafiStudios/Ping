package com.girafi.ping.client;

import com.girafi.ping.client.gui.PingSelectGui;
import com.girafi.ping.data.PingType;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class KeyHelper {
    private static boolean quickPingAlertHeld = false;
    private static boolean quickPingMineHeld = false;
    private static boolean quickPingLookHeld = false;
    private static boolean quickPingGotoHeld = false;


    public static void onTick() {
        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null) {
            return;
        }

        long handle = Minecraft.getInstance().getWindow().getWindow();
        int keycode = PingKeybinds.KEY_BINDING.key.getValue();
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

        //ALERT
        boolean quickPingDownAlert = PingKeybinds.PING_ALERT.isDown();

        if (quickPingDownAlert && !quickPingAlertHeld) {
            while (PingKeybinds.PING_ALERT.consumeClick()) {
                ClientHandlerBase.sendPing(PingType.ALERT);
            }
        }
        quickPingAlertHeld = quickPingDownAlert;

        //MINE
        boolean quickPingDownMine = PingKeybinds.PING_MINE.isDown();

        if (quickPingDownMine && !quickPingMineHeld) {
            while (PingKeybinds.PING_MINE.consumeClick()) {
                ClientHandlerBase.sendPing(PingType.MINE);
            }
        }
        quickPingMineHeld = quickPingDownMine;

        //LOOK
        boolean quickPingDownLook = PingKeybinds.PING_LOOK.isDown();

        if (quickPingDownLook && !quickPingLookHeld) {
            while (PingKeybinds.PING_LOOK.consumeClick()) {
                ClientHandlerBase.sendPing(PingType.LOOK);
            }
        }
        quickPingLookHeld = quickPingDownLook;

        //GOTO
        boolean quickPingDownGoto = PingKeybinds.PING_GOTO.isDown();

        if (quickPingDownGoto && !quickPingGotoHeld) {
            while (PingKeybinds.PING_GOTO.consumeClick()) {
                ClientHandlerBase.sendPing(PingType.GOTO);
            }
        }
        quickPingGotoHeld = quickPingDownGoto;
    }
}