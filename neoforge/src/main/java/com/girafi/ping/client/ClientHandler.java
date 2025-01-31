package com.girafi.ping.client;

import com.girafi.ping.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientHandler extends ClientHandlerBase {

    @SubscribeEvent
    public static void registerKeybinds(RegisterKeyMappingsEvent event) {
        event.register(PingKeybinds.KEY_BINDING);
        event.register(PingKeybinds.PING_ALERT);
        event.register(PingKeybinds.PING_MINE);
        event.register(PingKeybinds.PING_LOOK);
        event.register(PingKeybinds.PING_GOTO);
    }

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) {
        event.registerShader(PingRenderType.PING_SHADER);
    }
}