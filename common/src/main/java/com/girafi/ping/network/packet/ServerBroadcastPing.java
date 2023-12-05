package com.girafi.ping.network.packet;

import com.girafi.ping.Constants;
import com.girafi.ping.client.PingHandlerHelper;
import com.girafi.ping.data.PingWrapper;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/**
 * Sent from the Server, handled on the Client
 */
public class ServerBroadcastPing {
    public static final ResourceLocation CHANNEL = new ResourceLocation(Constants.MOD_ID, "server_broadcast_ping");
    public PingWrapper ping;

    public ServerBroadcastPing() {}

    public ServerBroadcastPing(PingWrapper ping) {
        this.ping = ping;
    }

    public static void encode(ServerBroadcastPing pingPacket, FriendlyByteBuf buf) {
        pingPacket.ping.writeToBuffer(buf);
    }

    public static ServerBroadcastPing decode(FriendlyByteBuf buf) {
        return new ServerBroadcastPing(PingWrapper.readFromBuffer(buf));
    }

    public static void handle(PacketContext<ServerBroadcastPing> ctx) {
        if (ctx.side() == Side.SERVER) {
            PingHandlerHelper.onPingPacket(ctx.message());
        }
    }
}