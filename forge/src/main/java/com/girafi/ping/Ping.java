package com.girafi.ping;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = Constants.MOD_ID)
public class Ping {

    public Ping() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);

        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec); //TODO Figure out how to do config for Forge
        registerDeferredRegistries(eventBus);
    }

    public void setupCommon(final FMLCommonSetupEvent event) {

    }

    public void setupClient(final FMLClientSetupEvent event) {

    }

    public static void registerDeferredRegistries(IEventBus modBus) {

    }
}