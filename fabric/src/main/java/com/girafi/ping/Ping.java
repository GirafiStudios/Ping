package com.girafi.ping;

import com.girafi.ping.client.KeyHelper;
import com.girafi.ping.client.PingHandlerHelper;
import com.girafi.ping.client.PingKeybinds;
import com.girafi.ping.client.gui.PingSelectGui;
import com.girafi.ping.util.PingConfig;
import fuzs.forgeconfigapiport.api.config.v3.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.neoforged.fml.config.ModConfig;

public class Ping implements ModInitializer { //TODO Render Ping Offscreen

    @Override
    public void onInitialize() {
        PingCommon.loadCommon();
        ForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, PingConfig.spec);

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

        WorldRenderEvents.AFTER_TRANSLUCENT.register((renderContext) -> {
            PingHandlerHelper.translateWorldPing(renderContext.matrixStack(), renderContext.projectionMatrix());
        });
    }
}