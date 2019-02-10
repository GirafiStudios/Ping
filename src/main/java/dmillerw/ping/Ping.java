package dmillerw.ping;

import dmillerw.ping.client.ClientHandler;
import dmillerw.ping.network.PacketHandler;
import dmillerw.ping.util.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;

@Mod(value = Reference.MOD_ID)
public class Ping {

    public Ping() {
        FMLModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    public void setup(final FMLCommonSetupEvent event) {
        PacketHandler.initialize();
    }

    public void setupClient(final FMLClientSetupEvent event) {
        ClientHandler.registerKeybinds();
    }
}