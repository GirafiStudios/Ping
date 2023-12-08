package com.girafi.ping.util;

import com.girafi.ping.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class PingSounds {
    public static final Supplier<SoundEvent> BLOOP = registerSound("bloop");

    /**
     * Registers a sound
     *
     * @param name The name to register the sound with
     * @return The Sound that was registered
     */
    public static Supplier<SoundEvent> registerSound(String name) {
        ResourceLocation resourceLocation = new ResourceLocation(Constants.MOD_ID, name);
        SoundEvent sound = SoundEvent.createVariableRangeEvent(resourceLocation);
        return () -> sound;
    }
}