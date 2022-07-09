package dmillerw.ping;

import dmillerw.ping.network.PacketHandler;
import dmillerw.ping.util.Config;
import dmillerw.ping.util.PingSounds;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = Ping.MOD_ID)
public class Ping {
    public static final String MOD_ID = "ping";

    public Ping() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::setupClient);
        this.registerDeferredRegistries(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.spec);
    }

    public void setup(final FMLCommonSetupEvent event) {
        PacketHandler.initialize();
    }

    public void setupClient(final FMLClientSetupEvent event) {
    }

    public void registerDeferredRegistries(IEventBus modBus) {
        PingSounds.SOUND_DEFERRED.register(modBus);
    }
}