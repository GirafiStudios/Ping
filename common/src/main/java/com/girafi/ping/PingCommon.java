package com.girafi.ping;

import com.girafi.ping.network.packet.ClientSendPing;
import com.girafi.ping.network.packet.ServerBroadcastPing;
import com.girafi.ping.util.TempConfig;
import commonnetwork.api.Network;

import java.nio.file.Path;

public class PingCommon {
    private static TempConfig config;

    public static void loadCommon(Path configPath) {
        config = TempConfig.load(configPath.resolve(Constants.MOD_ID + ".json").toFile());


        Network.registerPacket(ClientSendPing.CHANNEL, ClientSendPing.class, ClientSendPing::encode, ClientSendPing::decode, ClientSendPing::handle)
                .registerPacket(ServerBroadcastPing.CHANNEL, ServerBroadcastPing.class, ServerBroadcastPing::encode, ServerBroadcastPing::decode, ServerBroadcastPing::handle);
    }

    public static TempConfig config() {
        return config;
    }

}