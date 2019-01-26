package dmillerw.ping.network;

import dmillerw.ping.network.packet.ClientSendPing;
import dmillerw.ping.network.packet.ServerBroadcastPing;
import dmillerw.ping.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler {
    public static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Reference.MOD_ID, "ping_channel"))
            .clientAcceptedVersions(Reference.MOD_ID::equals)
            .serverAcceptedVersions(Reference.MOD_ID::equals)
            .networkProtocolVersion(() -> Reference.MOD_ID)
            .simpleChannel();

    public static void initialize() {
        HANDLER.registerMessage(0, ClientSendPing.class, ClientSendPing::encode, ClientSendPing::decode, ClientSendPing.Handler::handle);
        HANDLER.registerMessage(1, ServerBroadcastPing.class, ServerBroadcastPing::encode, ServerBroadcastPing::decode, ServerBroadcastPing.Handler::handle);
    }
}