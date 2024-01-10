package com.girafi.ping;

import com.girafi.ping.util.PingConfig;
import com.girafi.ping.util.PingSounds;
import fuzs.forgeconfigapiport.forge.api.neoforge.v4.NeoForgeConfigRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

@Mod(value = Constants.MOD_ID)
public class Ping {
    public static final DeferredRegister<SoundEvent> SOUND_EVENT_DEFERRED = DeferredRegister.create(Registries.SOUND_EVENT, Constants.MOD_ID);

    public Ping() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setupCommon);
        eventBus.addListener(this::setupClient);
        PingCommon.registerPackets();

        NeoForgeConfigRegistry.INSTANCE.register(ModConfig.Type.COMMON, PingConfig.spec);

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