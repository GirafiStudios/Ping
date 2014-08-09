package dmillerw.ping.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import dmillerw.ping.network.packet.ClientSendPing;
import dmillerw.ping.network.packet.ServerBroadcastPing;

/**
 * @author dmillerw
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("Ping");

    public static void initialize() {
        INSTANCE.registerMessage(ClientSendPing.class, ClientSendPing.class, 0, Side.SERVER);
        INSTANCE.registerMessage(ServerBroadcastPing.class, ServerBroadcastPing.class, 1, Side.CLIENT);
    }
}
