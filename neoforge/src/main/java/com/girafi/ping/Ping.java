package com.girafi.ping;

import com.girafi.ping.network.PacketHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.fml.loading.FMLPaths;

@Mod(Constants.MOD_ID)
public class Ping {

    public Ping() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);

        PingCommon.setConfigFolder(FMLPaths.CONFIGDIR.get());

        registerDeferredRegistries(eventBus);
    }

    public void setupCommon(final FMLCommonSetupEvent event) {
        PacketHandler.initialize();
    }

    public void setupClient(final FMLClientSetupEvent event) {

    }

    public static void registerDeferredRegistries(IEventBus modBus) {

    }
}