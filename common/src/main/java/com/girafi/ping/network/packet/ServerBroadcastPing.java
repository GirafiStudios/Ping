package com.girafi.ping.network.packet;

import com.girafi.ping.data.PingWrapper;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Sent from the Server, handled on the Client
 */
public class ServerBroadcastPing {
    public PingWrapper ping;

    public ServerBroadcastPing(PingWrapper ping) {
        this.ping = ping;
    }

    public static void encode(ServerBroadcastPing pingPacket, FriendlyByteBuf buf) {
        pingPacket.ping.writeToBuffer(buf);
    }

    public static ServerBroadcastPing receive(FriendlyByteBuf buf) {
        return new ServerBroadcastPing(PingWrapper.readFromBuffer(buf));
    }
}