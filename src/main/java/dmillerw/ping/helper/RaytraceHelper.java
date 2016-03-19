package dmillerw.ping.helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

/**
 * @author dmillerw
 */
public class RaytraceHelper {

    public static RayTraceResult raytrace(EntityPlayer player, double distance) {
        double eyeHeight = player.getEyeHeight();
        Vec3d lookVec = player.getLookVec();
        Vec3d origin = new Vec3d(player.posX, player.posY + eyeHeight, player.posZ);
        Vec3d direction = origin.addVector(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance);
        return player.worldObj.rayTraceBlocks(origin, direction);
    }
}