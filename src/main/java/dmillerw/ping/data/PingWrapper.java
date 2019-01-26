package dmillerw.ping.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class PingWrapper {

    public static PingWrapper readFromBuffer(ByteBuf buffer) {
        int x = buffer.readInt();
        int y = buffer.readInt();
        int z = buffer.readInt();
        int color = buffer.readInt();
        PingType type = PingType.values()[buffer.readInt()];
        return new PingWrapper(new BlockPos(x, y, z), color, type);
    }

    public final BlockPos pos;
    public final int color;
    public final PingType type;
    public boolean isOffscreen = false;
    public float screenX;
    public float screenY;
    public int animationTimer = 20;
    public int timer;

    public PingWrapper(BlockPos pos, int color, PingType type) {
        this.pos = pos;
        this.color = color;
        this.type = type;
    }

    public AxisAlignedBB getAABB() {
        return new AxisAlignedBB(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
    }

    public void writeToBuffer(ByteBuf buffer) {
        buffer.writeInt(pos.getX());
        buffer.writeInt(pos.getY());
        buffer.writeInt(pos.getZ());
        buffer.writeInt(color);
        buffer.writeInt(type.ordinal());
    }
}