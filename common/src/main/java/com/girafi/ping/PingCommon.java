package com.girafi.ping;

import com.girafi.ping.network.packet.ClientSendPing;
import com.girafi.ping.network.packet.ServerBroadcastPing;
import commonnetwork.CommonNetworkMod;
import commonnetwork.api.Dispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.Team;

public class PingCommon {

    public static void registerPackets() {
        CommonNetworkMod.registerPacket(ClientSendPing.type(), ClientSendPing.class, ClientSendPing.STREAM_CODEC, ClientSendPing::handle)
                .registerPacket(ServerBroadcastPing.type(), ServerBroadcastPing.class, ServerBroadcastPing.STREAM_CODEC, ServerBroadcastPing::handle);
    }

    public static <T> void sendToClientsInRangeWithTeamCheck(T packet, ServerPlayer sender, ServerLevel level, BlockPos pos, double range) {
        double rangeSqr = range * range;
        Team senderTeam = sender.getTeam();
        boolean senderHasTeam = senderTeam != null;

        for (ServerPlayer player : level.players()) {
            if (player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) > rangeSqr) {
                continue;
            }

            Team receiverTeam = player.getTeam();
            boolean receiverHasTeam = receiverTeam != null;

            if (senderHasTeam != receiverHasTeam) {
                continue;
            }

            if (!senderHasTeam || senderTeam == receiverTeam || senderTeam.isAlliedTo(receiverTeam)) {
                Dispatcher.sendToClient(packet, player);
            }
        }
    }
}