package dmillerw.ping;

import dmillerw.ping.proxy.CommonProxy;
import dmillerw.ping.util.Reference;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.DEPENDENCIES, acceptableRemoteVersions = "*", guiFactory = Reference.GUI_FACTORY_CLASS)
@Mod.EventBusSubscriber
public class Ping {
    @SidedProxy(serverSide = Reference.SERVER_PROXY_ClASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        proxy.syncConfig();
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.PostConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            proxy.syncConfig();
        }
    }
}