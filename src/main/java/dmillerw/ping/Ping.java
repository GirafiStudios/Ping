package dmillerw.ping;

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
@Mod(modid = "Ping", name = "Ping", version = "%MOD_VERSION%", dependencies = "required-after:Forge@[%FORGE_VERSION%,)", acceptableRemoteVersions = "*", guiFactory = "dmillerw.ping.client.gui.config.PingGuiFactory")
public class Ping {

    @Mod.Instance("Ping")
    public static Ping instance;

    @SidedProxy(serverSide = "dmillerw.ping.proxy.CommonProxy", clientSide = "dmillerw.ping.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(instance);

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