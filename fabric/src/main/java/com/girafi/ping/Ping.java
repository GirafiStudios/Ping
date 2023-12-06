package com.girafi.ping;

import com.girafi.ping.client.KeyHelper;
import com.girafi.ping.client.PingHandlerHelper;
import com.girafi.ping.client.PingKeybinds;
import com.girafi.ping.client.gui.PingSelectGui;
import commonnetwork.CommonNetworkMod;
import commonnetwork.networking.FabricNetworkHandler;
import commonnetwork.networking.data.Side;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;

public class Ping implements ModInitializer {

    @Override
    public void onInitialize() {
        PingCommon.loadCommon(FabricLoader.getInstance().getConfigDir());

        //Register keybinds
        KeyBindingHelper.registerKeyBinding(PingKeybinds.KEY_BINDING);
        KeyBindingHelper.registerKeyBinding(PingKeybinds.PING_ALERT);
        KeyBindingHelper.registerKeyBinding(PingKeybinds.PING_MINE);
        KeyBindingHelper.registerKeyBinding(PingKeybinds.PING_LOOK);
        KeyBindingHelper.registerKeyBinding(PingKeybinds.PING_GOTO);

        //TODO Register Shader Instance (Probably not here, but somehow somewhere)

        ClientTickEvents.END_CLIENT_TICK.register((mc) -> {
            PingHandlerHelper.pingTimer();
            KeyHelper.onTick();

            if ((mc.level == null || mc.isPaused()) && PingSelectGui.active) {
                PingSelectGui.deactivate();
            }
        });
    }
}