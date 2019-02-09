package dmillerw.ping.network;

import dmillerw.ping.network.packet.ClientSendPing;
import dmillerw.ping.network.packet.ServerBroadcastPing;
import dmillerw.ping.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Reference.MOD_ID, "ping_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void initialize() {
        CHANNEL.registerMessage(0, ClientSendPing.class, ClientSendPing::encode, ClientSendPing::decode, ClientSendPing.Handler::handle);
        CHANNEL.registerMessage(1, ServerBroadcastPing.class, ServerBroadcastPing::encode, ServerBroadcastPing::decode, ServerBroadcastPing.Handler::handle);
    }
}