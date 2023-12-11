package com.girafi.ping;

import com.girafi.ping.network.packet.ClientSendPing;
import com.girafi.ping.network.packet.ServerBroadcastPing;
import commonnetwork.api.Network;

public class PingCommon {

    public static void registerPackets() {
        Network.registerPacket(ClientSendPing.CHANNEL, ClientSendPing.class, ClientSendPing::encode, ClientSendPing::decode, ClientSendPing::handle)
                .registerPacket(ServerBroadcastPing.CHANNEL, ServerBroadcastPing.class, ServerBroadcastPing::encode, ServerBroadcastPing::decode, ServerBroadcastPing::handle);
    }
}