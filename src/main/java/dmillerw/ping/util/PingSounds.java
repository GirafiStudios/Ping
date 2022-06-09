package dmillerw.ping.util;

import dmillerw.ping.Ping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PingSounds {
    public static final DeferredRegister<SoundEvent> SOUND_DEFERRED = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Ping.MOD_ID);
    public static final RegistryObject<SoundEvent> BLOOP = registerSound("bloop");

    /**
     * Registers a sound
     *
     * @param name The name to register the sound with
     * @return The Sound that was registered
     */
    public static RegistryObject<SoundEvent> registerSound(String name) {
        ResourceLocation resourceLocation = new ResourceLocation(Ping.MOD_ID, name);
        SoundEvent sound = new SoundEvent(resourceLocation);
        return SOUND_DEFERRED.register(name, () -> sound);
    }
}