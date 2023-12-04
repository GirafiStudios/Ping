package com.girafi.ping.network.packet;

import com.girafi.ping.data.PingWrapper;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Sent from the Client, handled on the Server
 */
public class ClientSendPing {
    private final PingWrapper ping;

    public ClientSendPing(PingWrapper ping) {
        this.ping = ping;
    }

    public PingWrapper getPing() {
        return ping;
    }

    public static void encode(ClientSendPing pingPacket, FriendlyByteBuf buf) {
        pingPacket.ping.writeToBuffer(buf);
    }

    public static ClientSendPing receive(FriendlyByteBuf buf) {
        return new ClientSendPing(PingWrapper.readFromBuffer(buf));
    }
}