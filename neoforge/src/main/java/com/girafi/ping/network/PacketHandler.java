package com.girafi.ping.network;

import com.girafi.ping.Constants;
import com.girafi.ping.client.PingHandlerHelper;
import com.girafi.ping.network.packet.ClientSendPing;
import com.girafi.ping.network.packet.ServerBroadcastPing;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.network.NetworkEvent;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.PlayNetworkDirection;
import net.neoforged.neoforge.network.simple.SimpleChannel;

public class PacketHandler {
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Constants.MOD_ID, "ping_channel"))
            .clientAcceptedVersions(v -> true)
            .serverAcceptedVersions(v -> true)
            .networkProtocolVersion(() -> "PING1")
            .simpleChannel();

    public static void initialize() {
        CHANNEL.registerMessage(0, ClientSendPing.class, ClientSendPing::encode, ClientSendPing::receive, Handler::handleClientSendPing);
        CHANNEL.registerMessage(1, ServerBroadcastPing.class, ServerBroadcastPing::encode, ServerBroadcastPing::receive, Handler::handleServerBroadcastPing);
    }

    public static class Handler {
        public static void handleClientSendPing(ClientSendPing message, NetworkEvent.Context ctx) {
            ServerPlayer playerMP = ctx.getSender();
            if (playerMP != null && !(playerMP instanceof FakePlayer)) {
                for (Player player : playerMP.level().players()) {
                    if (player instanceof ServerPlayer) {
                        PacketHandler.CHANNEL.sendTo(new ServerBroadcastPing(message.getPing()), ((ServerPlayer) player).connection.connection, PlayNetworkDirection.PLAY_TO_CLIENT);
                    }
                }
                ctx.setPacketHandled(true);
            }
        }

        public static void handleServerBroadcastPing(ServerBroadcastPing message, NetworkEvent.Context ctx) {
            ctx.enqueueWork(() -> PingHandlerHelper.INSTANCE.onPingPacket(message));
            ctx.setPacketHandled(true);
        }
    }
}