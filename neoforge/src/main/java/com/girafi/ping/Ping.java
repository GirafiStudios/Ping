package com.girafi.ping;

import com.girafi.ping.util.PingConfig;
import com.girafi.ping.util.PingSounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Constants.MOD_ID)
public class Ping {
    public static final DeferredRegister<SoundEvent> SOUND_EVENT_DEFERRED = DeferredRegister.create(Registries.SOUND_EVENT, Constants.MOD_ID);

    public Ping(IEventBus eventBus) {
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);
        PingCommon.registerPackets();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PingConfig.spec);

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