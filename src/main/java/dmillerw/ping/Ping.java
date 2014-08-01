package dmillerw.ping;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.ping.proxy.CommonProxy;

/**
 * @author dmillerw
 */
@Mod(modid = "Ping", name = "Ping", version = "%MOD_VERSION", dependencies = "required-after:Forge@[%FORGE_VERSION%,)", guiFactory = "dmillerw.ping.client.gui.config.PingGuiFactory")
public class Ping {

    @Mod.Instance("Ping")
    public static Ping instance;

    @SidedProxy(serverSide = "dmillerw.ping.proxy.CommonProxy", clientSide = "dmillerw.ping.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(instance);

        proxy.preInit(event);
        proxy.syncConfig();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.PostConfigChangedEvent event) {
        if (event.modID.equals("Ping")) {
            proxy.syncConfig();
        }
    }
}
