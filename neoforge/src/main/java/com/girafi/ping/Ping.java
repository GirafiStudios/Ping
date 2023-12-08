package com.girafi.ping;

import com.girafi.ping.util.PingSounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Constants.MOD_ID)
public class Ping {
    public static final DeferredRegister<SoundEvent> SOUND_EVENT_DEFERRED = DeferredRegister.create(Registries.SOUND_EVENT, Constants.MOD_ID);

    public Ping() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);

        PingCommon.loadCommon(FMLPaths.CONFIGDIR.get());

        registerDeferredRegistries(eventBus);
        SOUND_EVENT_DEFERRED.register("bloop", PingSounds.BLOOP);
    }

    public void setupCommon(final FMLCommonSetupEvent event) {
    }

    public void setupClient(final FMLClientSetupEvent event) {
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        SOUND_EVENT_DEFERRED.register(modBus);
    }
}