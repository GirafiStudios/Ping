package dmillerw.ping.proxy;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.ping.network.PacketHandler;

/**
 * @author dmillerw
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        PacketHandler.initialize();
    }

    public void syncConfig() {

    }
}
