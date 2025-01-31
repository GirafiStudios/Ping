package com.girafi.ping.client;

import com.girafi.ping.PingCommon;
import com.girafi.ping.client.gui.PingSelectGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.renderer.CoreShaders;

public class ClientHandler implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CoreShaders.getProgramsToPreload().add(PingRenderType.PING_SHADER);
        PingCommon.registerPackets();

        //Register keybinds
        KeyBindingHelper.registerKeyBinding(PingKeybinds.KEY_BINDING);
        KeyBindingHelper.registerKeyBinding(PingKeybinds.PING_ALERT);
        KeyBindingHelper.registerKeyBinding(PingKeybinds.PING_MINE);
        KeyBindingHelper.registerKeyBinding(PingKeybinds.PING_LOOK);
        KeyBindingHelper.registerKeyBinding(PingKeybinds.PING_GOTO);

        ClientTickEvents.END_CLIENT_TICK.register((mc) -> {
            PingHandlerHelper.pingTimer();
            KeyHelper.onTick();

            if ((mc.level == null || mc.isPaused()) && PingSelectGui.active) {
                PingSelectGui.deactivate();
            }
        });

        WorldRenderEvents.AFTER_TRANSLUCENT.register((renderContext) -> {
            PingHandlerHelper.translateWorldPing(renderContext.matrixStack(), renderContext.frustum(), renderContext.tickCounter().getGameTimeDeltaTicks());
        });
        HudRenderCallback.EVENT.register((guiGraphics, delta) -> {
            PingHandlerHelper.renderPingOffscreen(guiGraphics);
        });
    }
}