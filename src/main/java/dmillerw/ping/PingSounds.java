package dmillerw.ping;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PingSounds {
    public static SoundEvent bloop;

    static void init() {
        bloop = register("bloop");
    }

    private static SoundEvent register(String name) {
        ResourceLocation resourceLocation = new ResourceLocation("ping", name);
        return GameRegistry.register(new SoundEvent(resourceLocation), resourceLocation);
    }
}