package dmillerw.ping.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import dmillerw.ping.data.PingType;
import dmillerw.ping.helper.RaytraceHelper;
import dmillerw.ping.network.PacketHandler;
import dmillerw.ping.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * @author dmillerw
 */
public class KeyHandler {

    public static final KeyBinding key = new KeyBinding("key.ping", Keyboard.KEY_F, "key.categories.misc");

    public static void register() {
        ClientRegistry.registerKeyBinding(key);
        FMLCommonHandler.instance().bus().register(new KeyHandler());
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (key.getIsKeyPressed()) {
            MovingObjectPosition mob = RaytraceHelper.raytrace(Minecraft.getMinecraft().thePlayer, 50);
            if (mob != null && mob.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                PacketHandler.sendPing(mob, new Color(ClientProxy.pingR, ClientProxy.pingG, ClientProxy.pingB).getRGB(), PingType.ALERT);
            }
        }
    }
}
