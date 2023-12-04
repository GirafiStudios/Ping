package com.girafi.ping.client;

import com.girafi.ping.Constants;
import com.girafi.ping.client.gui.PingSelectGui;
import com.girafi.ping.data.PingType;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TickEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class KeyHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        PingHandlerHelper.pingTimer();

        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();
            if ((mc.level == null || mc.isPaused()) && PingSelectGui.active) {
                PingSelectGui.deactivate();
            }
            return;
        }

        Minecraft mc = Minecraft.getInstance();

        if (mc.level == null) {
            return;
        }

        long handle = Minecraft.getInstance().getWindow().getWindow();
        int keycode = PingKeybinds.KEY_BINDING.getKey().getValue();
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
            ClientHandler.INSTANCE.sendPing(PingType.ALERT);
        } else if (canSendQuickPing(PingKeybinds.PING_MINE)) {
            ClientHandler.INSTANCE.sendPing(PingType.MINE);
        } else if (canSendQuickPing(PingKeybinds.PING_LOOK)) {
            ClientHandler.INSTANCE.sendPing(PingType.LOOK);
        } else if (canSendQuickPing(PingKeybinds.PING_GOTO)) {
            ClientHandler.INSTANCE.sendPing(PingType.GOTO);
        }
    }

    private static boolean canSendQuickPing(KeyMapping keyBinding) {
        return keyBinding.isDown(); //Will continuously be triggered when key is held down due to vanilla issue.
    }
}