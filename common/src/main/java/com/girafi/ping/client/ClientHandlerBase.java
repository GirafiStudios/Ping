package com.girafi.ping.client;

import com.girafi.ping.PingCommon;
import com.girafi.ping.data.PingType;
import com.girafi.ping.data.PingWrapper;
import com.girafi.ping.network.packet.ClientSendPing;
import commonnetwork.api.Dispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Objects;

public class ClientHandlerBase {
    @Nullable
    public static ShaderInstance rendertypePing;

    public static ShaderInstance getRenderTypePing() {
        return Objects.requireNonNull(rendertypePing, "Attempted to call getRenderTypePing before shaders have finished loading.");
    }

    public static BlockHitResult raytrace(Player player, double distance) {
        float eyeHeight = player.getEyeHeight();
        return (BlockHitResult) player.pick(distance, eyeHeight, false);
    }

    public static void sendPing(BlockHitResult raytrace, int color, PingType type) {
        Dispatcher.sendToServer(new ClientSendPing(new PingWrapper(raytrace.getBlockPos(), color, type)));
    }

    public static void sendPing(PingType type) {
        BlockHitResult raytraceBlock = raytrace(Minecraft.getInstance().player, 50);
        if (raytraceBlock.getType() == HitResult.Type.BLOCK) {
            sendPing(raytraceBlock, new Color(PingCommon.config().pingColorRed, PingCommon.config().pingColorGreen, PingCommon.config().pingColorBlue).getRGB(), type);
        }
    }
}