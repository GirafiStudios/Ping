package dmillerw.ping.misc;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PingSounds {
    public static final SoundEvent BLOOP = register("bloop");

    private static SoundEvent register(String name) {
        ResourceLocation resourceLocation = new ResourceLocation(Reference.MOD_ID, name);
        return GameRegistry.register(new SoundEvent(resourceLocation), resourceLocation);
    }
}