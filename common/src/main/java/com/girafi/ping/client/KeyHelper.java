package com.girafi.ping.client;

import com.girafi.ping.client.gui.PingSelectGui;
import com.girafi.ping.data.PingType;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.sdl.SDLMouse;

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

        //PING MENU
        KeyMapping pingKey = PingKeybinds.KEY_BINDING;
        int pingKeycode = pingKey.key.getValue();
        boolean isPingMenuKeyPressed = pingKey.key.getType() == InputConstants.Type.MOUSE ? isMouseButtonDown(pingKeycode) : InputConstants.isKeyDown(pingKeycode);

        if (isPingMenuKeyPressed && !wasPingMenuKeyPressed) {
            PingSelectGui.activate();
        }
        if (!isPingMenuKeyPressed && wasPingMenuKeyPressed) {
            if (mc.gui.screen() instanceof PingSelectGui) {
                PingSelectGui.deactivate();
            }
        }
        wasPingMenuKeyPressed = isPingMenuKeyPressed;

        //ALERT
        KeyMapping alertKey = PingKeybinds.PING_ALERT;
        int alertKeycode = alertKey.key.getValue();
        boolean quickPingDownAlert = alertKey.key.getType() == InputConstants.Type.MOUSE ? isMouseButtonDown(alertKeycode) : alertKey.isDown();

        if (quickPingDownAlert && !quickPingAlertHeld) {
            while (PingKeybinds.PING_ALERT.consumeClick()) {
                ClientHandlerBase.sendPing(PingType.ALERT);
            }
        }
        quickPingAlertHeld = quickPingDownAlert;

        //MINE
        KeyMapping mineKey = PingKeybinds.PING_MINE;
        int mineKeykode = mineKey.key.getValue();
        boolean quickPingDownMine = mineKey.key.getType() == InputConstants.Type.MOUSE ? isMouseButtonDown(mineKeykode) : mineKey.isDown();

        if (quickPingDownMine && !quickPingMineHeld) {
            while (PingKeybinds.PING_MINE.consumeClick()) {
                ClientHandlerBase.sendPing(PingType.MINE);
            }
        }
        quickPingMineHeld = quickPingDownMine;

        //LOOK
        KeyMapping lookKey = PingKeybinds.PING_LOOK;
        int lookKeycode = lookKey.key.getValue();
        boolean quickPingDownLook = lookKey.key.getType() == InputConstants.Type.MOUSE ? isMouseButtonDown(lookKeycode) : lookKey.isDown();

        if (quickPingDownLook && !quickPingLookHeld) {
            while (PingKeybinds.PING_LOOK.consumeClick()) {
                ClientHandlerBase.sendPing(PingType.LOOK);
            }
        }
        quickPingLookHeld = quickPingDownLook;

        //GOTO
        KeyMapping gotoKey = PingKeybinds.PING_GOTO;
        int gotoKeycode = gotoKey.key.getValue();
        boolean quickPingDownGoto = gotoKey.key.getType() == InputConstants.Type.MOUSE ? isMouseButtonDown(gotoKeycode) : gotoKey.isDown();

        if (quickPingDownGoto && !quickPingGotoHeld) {
            while (PingKeybinds.PING_GOTO.consumeClick()) {
                ClientHandlerBase.sendPing(PingType.GOTO);
            }
        }
        quickPingGotoHeld = quickPingDownGoto;
    }

    private static boolean isMouseButtonDown(int button) {
        if (button < 1 || button > 32) {
            return false;
        }
        return (SDLMouse.SDL_GetMouseState(null, null) & (1 << (button - 1))) != 0;
    }
}