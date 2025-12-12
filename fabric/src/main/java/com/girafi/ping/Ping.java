package com.girafi.ping;

import com.girafi.ping.util.PingConfig;
import com.girafi.ping.util.PingSounds;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.neoforged.fml.config.ModConfig;

public class Ping implements ModInitializer {

    @Override
    public void onInitialize() {
        PingCommon.registerPackets();
        ConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.COMMON, PingConfig.spec);
        Registry.register(BuiltInRegistries.SOUND_EVENT, Identifier.fromNamespaceAndPath(Constants.MOD_ID, "bloop"), PingSounds.BLOOP.get());
    }
}