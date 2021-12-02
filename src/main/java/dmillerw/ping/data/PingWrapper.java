package dmillerw.ping.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public class PingWrapper {
    private static final float OFFSET = 1f;

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

    public AABB getAABB() {
        return new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + OFFSET, pos.getY() + OFFSET, pos.getZ() + OFFSET);
    }

    public static PingWrapper readFromBuffer(ByteBuf buffer) {
        int x = buffer.readInt();
        int y = buffer.readInt();
        int z = buffer.readInt();
        int color = buffer.readInt();
        PingType type = PingType.values()[buffer.readInt()];
        return new PingWrapper(new BlockPos(x, y, z), color, type);
    }

    public void writeToBuffer(ByteBuf buffer) {
        buffer.writeInt(pos.getX());
        buffer.writeInt(pos.getY());
        buffer.writeInt(pos.getZ());
        buffer.writeInt(color);
        buffer.writeInt(type.ordinal());
    }
}