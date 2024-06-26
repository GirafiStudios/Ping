package com.girafi.ping.network.packet;

import com.girafi.ping.Constants;
import com.girafi.ping.data.PingWrapper;
import com.girafi.ping.util.PingConfig;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/**
 * Sent from the Client, handled on the Server
 */
public class ClientSendPing {
    public static final ResourceLocation CHANNEL = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "client_send_ping");
    private PingWrapper ping;

    public ClientSendPing() {
    }

    public ClientSendPing(PingWrapper ping) {
        this.ping = ping;
    }

    public static void encode(ClientSendPing pingPacket, FriendlyByteBuf buf) {
        pingPacket.ping.writeToBuffer(buf);
    }

    public static ClientSendPing decode(FriendlyByteBuf buf) {
        return new ClientSendPing(PingWrapper.readFromBuffer(buf));
    }

    public PingWrapper getPing() {
        return ping;
    }

    public static void handle(PacketContext<ClientSendPing> ctx) {
        ServerPlayer playerMP = ctx.sender();
        if (playerMP != null) {
            Dispatcher.sendToClientsInRange(new ServerBroadcastPing(ctx.message().getPing()), playerMP.serverLevel(), ctx.message().getPing().pos, PingConfig.GENERAL.pingAcceptDistance.get());
        }
    }
}