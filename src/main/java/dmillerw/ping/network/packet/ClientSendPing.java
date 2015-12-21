package dmillerw.ping.network.packet;

import dmillerw.ping.data.PingWrapper;
import dmillerw.ping.network.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Sent from the Client, handled on the Server
 * @author dmillerw
 */
public class ClientSendPing implements IMessage, IMessageHandler<ClientSendPing, IMessage> {

    public PingWrapper ping;

    public ClientSendPing() {

    }

    public ClientSendPing(PingWrapper ping) {
        this.ping = ping;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ping.writeToBuffer(buf);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        ping = PingWrapper.readFromBuffer(buf);
    }

    @Override
    public IMessage onMessage(ClientSendPing message, MessageContext ctx) {
        PacketHandler.INSTANCE.sendToDimension(new ServerBroadcastPing(message.ping), ctx.getServerHandler().playerEntity.worldObj.provider.getDimensionId());
        return null;
    }
}
