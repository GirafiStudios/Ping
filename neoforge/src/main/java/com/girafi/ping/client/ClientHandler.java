package com.girafi.ping.client;

import com.girafi.ping.Constants;
import com.girafi.ping.PingCommon;
import com.girafi.ping.data.PingType;
import com.girafi.ping.data.PingWrapper;
import com.girafi.ping.network.PacketHandler;
import com.girafi.ping.network.packet.ClientSendPing;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.awt.*;
import java.io.IOException;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientHandler extends ClientHandlerBase {

    @Override
    public void sendPing(BlockHitResult raytrace, int color, PingType type) {
        PacketHandler.CHANNEL.sendToServer(new ClientSendPing(new PingWrapper(raytrace.getBlockPos(), color, type)));
    }

    @Override
    public void sendPing(PingType type) {
        BlockHitResult raytraceBlock = raytrace(Minecraft.getInstance().player, 50);
        if (raytraceBlock.getType() == HitResult.Type.BLOCK) {
            sendPing(raytraceBlock, new Color(PingCommon.config().pingColorRed, PingCommon.config().pingColorGreen, PingCommon.config().pingColorBlue).getRGB(), type);
        }
    }

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