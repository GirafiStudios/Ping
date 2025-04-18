package com.girafi.ping;

import com.girafi.ping.util.PingConfig;
import com.girafi.ping.util.PingSounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Constants.MOD_ID)
public class Ping {
    public static final DeferredRegister<SoundEvent> SOUND_EVENT_DEFERRED = DeferredRegister.create(Registries.SOUND_EVENT, Constants.MOD_ID);

    public Ping(ModContainer modContainer, IEventBus eventBus) {
        PingCommon.registerPackets();
        modContainer.registerConfig(ModConfig.Type.COMMON, PingConfig.spec);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }

        registerDeferredRegistries(eventBus);
        SOUND_EVENT_DEFERRED.register("bloop", PingSounds.BLOOP);
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        SOUND_EVENT_DEFERRED.register(modBus);
    }
}