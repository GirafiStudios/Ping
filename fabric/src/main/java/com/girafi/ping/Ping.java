package com.girafi.ping;

import com.girafi.ping.util.PingConfig;
import com.girafi.ping.util.PingSounds;
import fuzs.forgeconfigapiport.api.config.v3.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.config.ModConfig;

public class Ping implements ModInitializer {

    @Override
    public void onInitialize() {
        PingCommon.registerPackets();
        ForgeConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, PingConfig.spec);
        Registry.register(BuiltInRegistries.SOUND_EVENT, new ResourceLocation(Constants.MOD_ID, "bloop"), PingSounds.BLOOP.get());
    }
}