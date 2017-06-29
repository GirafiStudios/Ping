package dmillerw.ping.util;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

@EventBusSubscriber
public class PingSounds {
    private static List<SoundEvent> sounds = Lists.newArrayList();
    public static final SoundEvent BLOOP = createSound("bloop");

    private static SoundEvent createSound(String name) {
        ResourceLocation resourceLocation = new ResourceLocation(Reference.MOD_ID, name);
        SoundEvent sound = new SoundEvent(resourceLocation);
        sound.setRegistryName(resourceLocation);
        sounds.add(sound);
        return sound;
    }

    @SubscribeEvent
    public static void registerSound(RegistryEvent.Register<SoundEvent> event) {
        for (SoundEvent sound : sounds) {
            event.getRegistry().register(sound);
        }
    }
}