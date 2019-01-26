package dmillerw.ping.network.packet;

import dmillerw.ping.client.PingHandler;
import dmillerw.ping.data.PingWrapper;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Sent from the Server, handled on the Client
 */
public class ServerBroadcastPing {
    public PingWrapper ping;

    ServerBroadcastPing(PingWrapper ping) {
        this.ping = ping;
    }

    public static void encode(ServerBroadcastPing pingPacket, PacketBuffer buf) {
        pingPacket.ping.writeToBuffer(buf);
    }

    public static ServerBroadcastPing decode(PacketBuffer buf) {
        return new ServerBroadcastPing(PingWrapper.readFromBuffer(buf));
    }

    public static class Handler {
        public static void handle(ServerBroadcastPing message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> PingHandler.INSTANCE.onPingPacket(message));
        }
    }
}