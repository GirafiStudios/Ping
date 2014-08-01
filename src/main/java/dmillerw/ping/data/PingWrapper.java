package dmillerw.ping.data;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.AxisAlignedBB;

/**
 * @author dmillerw
 */
public class PingWrapper {

    public static PingWrapper readFromBuffer(ByteBuf buffer) {
        int x = buffer.readInt();
        int y = buffer.readInt();
        int z = buffer.readInt();
        int color = buffer.readInt();
        PingType type = PingType.values()[buffer.readInt()];
        return new PingWrapper(x, y, z, color, type);
    }

    public final int x;
    public final int y;
    public final int z;

    public final int color;

    public final PingType type;

    public boolean isOffscreen = false;

    public float screenX;
    public float screenY;

    public int animationTimer = 20;
    public int timer;

    public PingWrapper(int x, int y, int z, int color, PingType type) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
        this.type = type;
    }

    public AxisAlignedBB getAABB() {
        return AxisAlignedBB.getBoundingBox(x + 0.5, y + 0.5, z + 0.5, x + 0.5, y + 0.5, z + 0.5);
    }

    public void writeToBuffer(ByteBuf buffer) {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeInt(color);
        buffer.writeInt(type.ordinal());
    }
}
