package com.girafi.ping.client;

import com.girafi.ping.Constants;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

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
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rendertype_ping"), DefaultVertexFormat.POSITION_TEX_COLOR), (renderShaderInstance) -> {
            rendertypePing = renderShaderInstance;
        });
    }
}