package dmillerw.ping.network.packet;

import dmillerw.ping.data.PingWrapper;
import dmillerw.ping.network.PacketHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
            ServerPlayerEntity playerMP = ctx.get().getSender();
            if (playerMP != null && !(playerMP instanceof FakePlayer)) {
                for (PlayerEntity player : playerMP.world.getPlayers()) {
                    if (player instanceof ServerPlayerEntity) {
                        PacketHandler.CHANNEL.sendTo(new ServerBroadcastPing(message.ping), ((ServerPlayerEntity) player).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
                    }
                }
                ctx.get().setPacketHandled(true);
            }
        }
    }
}