package com.girafi.ping.network.packet;

import com.girafi.ping.Constants;
import com.girafi.ping.client.PingHandlerHelper;
import com.girafi.ping.data.PingWrapper;
import commonnetwork.networking.data.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/**
 * Sent from the Server, handled on the Client
 */
public class ServerBroadcastPing implements CustomPacketPayload {
    public static final Identifier CHANNEL = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "server_broadcast_ping");
    public static final CustomPacketPayload.Type<ServerBroadcastPing> TYPE = new CustomPacketPayload.Type<>(CHANNEL);
    public static final StreamCodec<FriendlyByteBuf, ServerBroadcastPing> STREAM_CODEC = StreamCodec.ofMember(ServerBroadcastPing::encode, ServerBroadcastPing::new);
    public PingWrapper ping;

    public ServerBroadcastPing() {
    }

    public ServerBroadcastPing(FriendlyByteBuf buf) {
        this.ping = PingWrapper.readFromBuffer(buf);
    }

    public ServerBroadcastPing(PingWrapper ping) {
        this.ping = ping;
    }

    @Override
    public CustomPacketPayload.Type<ServerBroadcastPing> type() {
        return TYPE;
    }

    public static void encode(ServerBroadcastPing pingPacket, FriendlyByteBuf buf) {
        pingPacket.ping.writeToBuffer(buf);
    }

    public static void handle(PacketContext<ServerBroadcastPing> ctx) {
        PingHandlerHelper.onPingPacket(ctx.message());
    }
}