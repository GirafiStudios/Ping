package dmillerw.ping.client;

import dmillerw.ping.data.PingType;
import dmillerw.ping.data.PingWrapper;
import dmillerw.ping.network.PacketHandler;
import dmillerw.ping.network.packet.ClientSendPing;
import dmillerw.ping.util.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.awt.*;

public class ClientHandler {

    public static void sendPing(PingType type) {
        RayTraceResult mob = raytrace(Minecraft.getInstance().player, 50);
        if (mob != null && mob.type == RayTraceResult.Type.BLOCK) {
            sendPing(mob, new Color(Config.VISUAL.pingR.get(), Config.VISUAL.pingG.get(), Config.VISUAL.pingB.get()).getRGB(), type);
        }
    }

    private static void sendPing(RayTraceResult mob, int color, PingType type) {
        PacketHandler.CHANNEL.sendToServer(new ClientSendPing(new PingWrapper(mob.getBlockPos(), color, type)));
    }

    private static RayTraceResult raytrace(EntityPlayer player, double distance) {
        double eyeHeight = player.getEyeHeight();
        Vec3d lookVec = player.getLookVec();
        Vec3d origin = new Vec3d(player.posX, player.posY + eyeHeight, player.posZ);
        Vec3d direction = origin.add(lookVec.x * distance, lookVec.y * distance, lookVec.z * distance);
        return player.world.rayTraceBlocks(origin, direction);
    }

    public static void registerKeybinds() {
        ClientRegistry.registerKeyBinding(KeyHandler.KEY_BINDING);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_ALERT);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_MINE);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_LOOK);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_GOTO);
    }
}