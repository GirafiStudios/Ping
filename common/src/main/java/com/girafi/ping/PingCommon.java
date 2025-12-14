package com.girafi.ping;

import com.girafi.ping.network.packet.ClientSendPing;
import com.girafi.ping.network.packet.ServerBroadcastPing;
import commonnetwork.CommonNetworkMod;

public class PingCommon {

    public static void registerPackets() {
        CommonNetworkMod.registerPacket(ClientSendPing.type(), ClientSendPing.class, ClientSendPing.STREAM_CODEC, ClientSendPing::handle)
                .registerPacket(ServerBroadcastPing.type(), ServerBroadcastPing.class, ServerBroadcastPing.STREAM_CODEC,  ServerBroadcastPing::handle);
    }
}