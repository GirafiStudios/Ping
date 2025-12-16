package com.girafi.ping.network.packet;

import com.girafi.ping.Constants;
import com.girafi.ping.PingCommon;
import com.girafi.ping.data.PingWrapper;
import com.girafi.ping.util.PingConfig;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

/**
 * Sent from the Client, handled on the Server
 */
public class ClientSendPing {
    public static final Identifier CHANNEL = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "client_send_ping");
    public static final StreamCodec<FriendlyByteBuf, ClientSendPing> STREAM_CODEC = StreamCodec.ofMember(ClientSendPing::encode, ClientSendPing::new);
    private PingWrapper ping;

    public ClientSendPing() {
    }

    public ClientSendPing(FriendlyByteBuf buf) {
        this.ping = PingWrapper.readFromBuffer(buf);
    }

    public ClientSendPing(PingWrapper ping) {
        this.ping = ping;
    }

    public static CustomPacketPayload.Type<CustomPacketPayload> type() {
        return new CustomPacketPayload.Type<>(CHANNEL);
    }

    public static void encode(ClientSendPing pingPacket, FriendlyByteBuf buf) {
        pingPacket.ping.writeToBuffer(buf);
    }

    public PingWrapper getPing() {
        return ping;
    }

    public static void handle(PacketContext<ClientSendPing> ctx) {
        ServerPlayer sender = ctx.sender();
        if (sender != null) {
            ServerBroadcastPing serverBroadcastPing = new ServerBroadcastPing(ctx.message().getPing());
            BlockPos pos = ctx.message().ping.pos;
            if (PingConfig.GENERAL.sendToTeamOnly.get()) {
                PingCommon.sendToClientsInRangeWithTeamCheck(serverBroadcastPing, sender, sender.level(), pos, PingConfig.GENERAL.pingAcceptDistance.get());
            } else {
                Dispatcher.sendToClientsInRange(serverBroadcastPing, sender.level(), pos, PingConfig.GENERAL.pingAcceptDistance.get());
            }
        }
    }
}