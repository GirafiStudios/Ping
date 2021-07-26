package dmillerw.ping.client;

import dmillerw.ping.data.PingType;
import dmillerw.ping.data.PingWrapper;
import dmillerw.ping.network.PacketHandler;
import dmillerw.ping.network.packet.ClientSendPing;
import dmillerw.ping.util.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class ClientHandler {

    @OnlyIn(Dist.CLIENT)
    public static void sendPing(PingType type) {
        BlockHitResult raytraceBlock = raytrace(Minecraft.getInstance().player, 50);
        if (raytraceBlock.getType() == HitResult.Type.BLOCK) {
            sendPing(raytraceBlock, new Color(Config.VISUAL.pingR.get(), Config.VISUAL.pingG.get(), Config.VISUAL.pingB.get()).getRGB(), type);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void sendPing(BlockHitResult raytrace, int color, PingType type) {
        PacketHandler.CHANNEL.sendToServer(new ClientSendPing(new PingWrapper(raytrace.getBlockPos(), color, type)));
    }

    @OnlyIn(Dist.CLIENT)
    private static BlockHitResult raytrace(Player player, double distance) {
        float eyeHeight = player.getEyeHeight();
        return (BlockHitResult) player.pick(distance, eyeHeight, false);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerKeybinds() {
        ClientRegistry.registerKeyBinding(KeyHandler.KEY_BINDING);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_ALERT);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_MINE);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_LOOK);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_GOTO);
    }
}