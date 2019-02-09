package dmillerw.ping.client;

import dmillerw.ping.data.PingType;
import dmillerw.ping.data.PingWrapper;
import dmillerw.ping.network.PacketHandler;
import dmillerw.ping.network.packet.ClientSendPing;
import dmillerw.ping.util.RaytraceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.awt.*;

public class ClientHandler {
    public static int pingR = 255;
    public static int pingG = 0;
    public static int pingB = 0;

    public static boolean blockOverlay = true;
    public static boolean menuBackground = true;
    public static boolean sound = true;

    public static double pingAcceptDistance = 64;
    public static int pingDuration = 125;

    public static void sendPing(PingType type) {
        RayTraceResult mob = RaytraceHelper.raytrace(Minecraft.getInstance().player, 50);
        if (mob != null && mob.type == RayTraceResult.Type.BLOCK) {
            sendPing(mob, new Color(ClientHandler.pingR, ClientHandler.pingG, ClientHandler.pingB).getRGB(), type);
        }
    }

    private static void sendPing(RayTraceResult mob, int color, PingType type) {
        PacketHandler.CHANNEL.sendToServer(new ClientSendPing(new PingWrapper(mob.getBlockPos(), color, type)));
    }

    public static void preInit() {
        ClientRegistry.registerKeyBinding(KeyHandler.KEY_BINDING);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_ALERT);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_MINE);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_LOOK);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_GOTO);

        /*configuration = new Configuration(event.getSuggestedConfigurationFile());
        configuration.load();*/
    }

    /*public void syncConfig() { //TODO
        Property p_pingR = configuration.get("visual", "red", 255, "Value from 0 - 255");
        Property p_pingG = configuration.get("visual", "green", 0, "Value from 0 - 255");
        Property p_pingB = configuration.get("visual", "blue", 0, "Value from 0 - 255");

        pingR = verify(p_pingR);
        pingG = verify(p_pingG);
        pingB = verify(p_pingB);

        blockOverlay = configuration.get("visual", "blockOverlay", true, "Whether to render a colored overlay on the Pinged block").getBoolean();
        menuBackground = configuration.get("visual", "backgroundMenu", true, "Whether to render the Ping Menu background").getBoolean();
        sound = configuration.get("general", "sound", true, "Whether to play a sound when a Ping is received").getBoolean();
        pingAcceptDistance = configuration.get("general", "pingAcceptDistance", 64D, "Maximum distance a Ping can be from you and still be received").getDouble();
        pingDuration = configuration.get("general", "pingDuration", 125, "How long a Ping should remain active before disappearing").getInt();

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    private int verify(Property property) {
        int value = property.getInt();
        if (value < 0) {
            value = 0;
        } else if (value > 255) {
            value = 255;
        }
        return value;
    }*/
}