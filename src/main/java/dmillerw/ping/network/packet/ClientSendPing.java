package dmillerw.ping.network.packet;

import dmillerw.ping.data.PingWrapper;
import dmillerw.ping.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Sent from the Client, handled on the Server
 */
public class ClientSendPing {
    private PingWrapper ping;

    public ClientSendPing(PingWrapper ping) {
        this.ping = ping;
    }

    public static void encode(ClientSendPing pingPacket, PacketBuffer buf) {
        pingPacket.ping.writeToBuffer(buf);
    }

    public static ClientSendPing decode(PacketBuffer buf) {
        return new ClientSendPing(PingWrapper.readFromBuffer(buf));
    }

    public static class Handler {
        public static void handle(ClientSendPing message, Supplier<NetworkEvent.Context> ctx) {
            EntityPlayerMP player = ctx.get().getSender();
            if (player != null && !(player instanceof FakePlayer)) {
                PacketHandler.HANDLER.sendTo(new ServerBroadcastPing(message.ping), player.connection.netManager, NetworkDirection.PLAY_TO_SERVER);
            }
        }
    }
}