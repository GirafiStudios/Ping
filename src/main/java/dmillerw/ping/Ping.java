package dmillerw.ping;

import dmillerw.ping.misc.PingSounds;
import dmillerw.ping.misc.Reference;
import dmillerw.ping.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author dmillerw
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, acceptedMinecraftVersions = "[1.10,1.11)", dependencies = Reference.DEPENDENCIES, acceptableRemoteVersions = "*", guiFactory = Reference.GUI_FACTORY_CLASS)
public class Ping {

    @Mod.Instance(Reference.MOD_ID)
    public static Ping instance;

    @SidedProxy(serverSide = Reference.SERVER_PROXY_ClASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(instance);

        proxy.preInit(event);
        proxy.syncConfig();

        PingSounds.init();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.PostConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            proxy.syncConfig();
        }
    }
}