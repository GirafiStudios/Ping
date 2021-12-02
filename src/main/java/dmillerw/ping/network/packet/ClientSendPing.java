package dmillerw.ping.network.packet;

import dmillerw.ping.data.PingWrapper;
import dmillerw.ping.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Sent from the Client, handled on the Server
 */
public class ClientSendPing {
    private PingWrapper ping;

    public ClientSendPing(PingWrapper ping) {
        this.ping = ping;
    }

    public static void encode(ClientSendPing pingPacket, FriendlyByteBuf buf) {
        pingPacket.ping.writeToBuffer(buf);
    }

    public static ClientSendPing decode(FriendlyByteBuf buf) {
        return new ClientSendPing(PingWrapper.readFromBuffer(buf));
    }

    public static class Handler {
        public static void handle(ClientSendPing message, Supplier<NetworkEvent.Context> ctx) {
            ServerPlayer playerMP = ctx.get().getSender();
            if (playerMP != null && !(playerMP instanceof FakePlayer)) {
                for (Player player : playerMP.level.players()) {
                    if (player instanceof ServerPlayer) {
                        PacketHandler.CHANNEL.sendTo(new ServerBroadcastPing(message.ping), ((ServerPlayer) player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
                    }
                }
                ctx.get().setPacketHandled(true);
            }
        }
    }
}