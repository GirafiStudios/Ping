package com.girafi.ping.client;

import com.girafi.ping.Constants;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
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
        event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(Constants.MOD_ID, "rendertype_ping"), DefaultVertexFormat.POSITION_TEX_COLOR), (p_172645_) -> {
            rendertypePing = p_172645_;
        });
    }
}