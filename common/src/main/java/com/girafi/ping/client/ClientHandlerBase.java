package com.girafi.ping.client;

import com.girafi.ping.data.PingType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.Objects;

public class ClientHandlerBase {
    public static final ClientHandlerBase INSTANCE = new ClientHandlerBase();

    @Nullable
    public static ShaderInstance rendertypePing;

    public static ShaderInstance getRenderTypePing() {
        return Objects.requireNonNull(rendertypePing, "Attempted to call getRenderTypePing before shaders have finished loading.");
    }

    public static BlockHitResult raytrace(Player player, double distance) {
        float eyeHeight = player.getEyeHeight();
        return (BlockHitResult) player.pick(distance, eyeHeight, false);
    }

    public void sendPing(BlockHitResult raytrace, int color, PingType type) {
    }

    public void sendPing(PingType type) {

    }
}
