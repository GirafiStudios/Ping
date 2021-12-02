package dmillerw.ping.network;

import dmillerw.ping.Ping;
import dmillerw.ping.network.packet.ClientSendPing;
import dmillerw.ping.network.packet.ServerBroadcastPing;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Ping.MOD_ID, "ping_channel"))
            .clientAcceptedVersions(v -> true)
            .serverAcceptedVersions(v -> true)
            .networkProtocolVersion(() -> "PING1")
            .simpleChannel();

    public static void initialize() {
        CHANNEL.registerMessage(0, ClientSendPing.class, ClientSendPing::encode, ClientSendPing::decode, ClientSendPing.Handler::handle);
        CHANNEL.registerMessage(1, ServerBroadcastPing.class, ServerBroadcastPing::encode, ServerBroadcastPing::decode, ServerBroadcastPing.Handler::handle);
    }
}