package dmillerw.ping.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import dmillerw.ping.data.PingType;
import dmillerw.ping.data.PingWrapper;
import dmillerw.ping.network.packet.ClientSendPing;
import dmillerw.ping.network.packet.ServerBroadcastPing;
import net.minecraft.util.MovingObjectPosition;

/**
 * @author dmillerw
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("Ping");

    public static void sendPing(MovingObjectPosition mob, int color, PingType type) {
        INSTANCE.sendToServer(new ClientSendPing(new PingWrapper(mob.blockX, mob.blockY, mob.blockZ, color, type)));
    }

    public static void initialize() {
        INSTANCE.registerMessage(ClientSendPing.class, ClientSendPing.class, 0, Side.SERVER);
        INSTANCE.registerMessage(ServerBroadcastPing.class, ServerBroadcastPing.class, 1, Side.CLIENT);
    }
}
