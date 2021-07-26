package dmillerw.ping.util;

import com.google.common.collect.Lists;
import dmillerw.ping.Ping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.List;

@EventBusSubscriber(modid = Ping.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class PingSounds {
    private static final List<SoundEvent> SOUNDS = Lists.newArrayList();
    public static final SoundEvent BLOOP = createSound("bloop");

    private static SoundEvent createSound(String name) {
        ResourceLocation resourceLocation = new ResourceLocation(Ping.MOD_ID, name);
        SoundEvent sound = new SoundEvent(resourceLocation);
        sound.setRegistryName(resourceLocation);
        SOUNDS.add(sound);
        return sound;
    }

    @SubscribeEvent
    public static void registerSound(RegistryEvent.Register<SoundEvent> event) {
        for (SoundEvent sound : SOUNDS) {
            event.getRegistry().register(sound);
        }
    }
}