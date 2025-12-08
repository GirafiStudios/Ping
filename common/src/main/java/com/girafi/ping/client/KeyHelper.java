package com.girafi.ping.client;

import com.girafi.ping.client.gui.PingSelectGui;
import com.girafi.ping.data.PingType;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import org.lwjgl.glfw.GLFW;

public class KeyHelper {
    private static boolean quickPingAlertHeld = false;
    private static boolean quickPingMineHeld = false;
    private static boolean quickPingLookHeld = false;
    private static boolean quickPingGotoHeld = false;
    private static boolean wasPingMenyKeyPressed = false;


    public static void onTick() {
        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null) {
            return;
        }

        //PING MENU
        boolean isPingMenyKeyPressed = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), PingKeybinds.KEY_BINDING.key.getValue());

        if (isPingMenyKeyPressed && !wasPingMenyKeyPressed) {
            PingSelectGui.activate();
        }
        if (!isPingMenyKeyPressed && wasPingMenyKeyPressed) {
            if (mc.screen instanceof PingSelectGui) {
                PingSelectGui.deactivate();
            }
        }
        wasPingMenyKeyPressed = isPingMenyKeyPressed;

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