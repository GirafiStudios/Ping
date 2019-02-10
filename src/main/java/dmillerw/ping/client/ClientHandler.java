package dmillerw.ping.client;

import dmillerw.ping.data.PingType;
import dmillerw.ping.data.PingWrapper;
import dmillerw.ping.network.PacketHandler;
import dmillerw.ping.network.packet.ClientSendPing;
import dmillerw.ping.util.RaytraceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.awt.*;

public class ClientHandler {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final Visual VISUAL = new Visual(BUILDER);

    public static void sendPing(PingType type) {
        RayTraceResult mob = RaytraceHelper.raytrace(Minecraft.getInstance().player, 50);
        if (mob != null && mob.type == RayTraceResult.Type.BLOCK) {
            sendPing(mob, new Color(ClientHandler.VISUAL.pingR.get(), ClientHandler.VISUAL.pingG.get(), ClientHandler.VISUAL.pingB.get()).getRGB(), type);
        }
    }

    private static void sendPing(RayTraceResult mob, int color, PingType type) {
        PacketHandler.CHANNEL.sendToServer(new ClientSendPing(new PingWrapper(mob.getBlockPos(), color, type)));
    }

    public static void registerKeybinds() {
        ClientRegistry.registerKeyBinding(KeyHandler.KEY_BINDING);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_ALERT);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_MINE);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_LOOK);
        ClientRegistry.registerKeyBinding(KeyHandler.PING_GOTO);
    }

    public static class General {
        public ForgeConfigSpec.DoubleValue pingAcceptDistance;
        public ForgeConfigSpec.IntValue pingDuration;
        public ForgeConfigSpec.BooleanValue sound;

        General(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            pingAcceptDistance = builder
                    .comment("Maximum distance a Ping can be from you and still be received")
                    .translation("ping.configgui.pingAcceptDistance")
                    .defineInRange("pingAcceptDistance", 64.0D, 0.0D, 255.0D);
            pingDuration = builder
                    .comment("How many ticks a Ping should remain active before disappearing")
                    .translation("ping.configgui.pingDuration")
                    .defineInRange("pingDuration", 125, 0, Integer.MAX_VALUE - 1);
            sound = builder
                    .comment("Whether to play a sound when a Ping is received")
                    .translation("ping.configgui.sound")
                    .define("sound", true);
            builder.pop();
        }
    }

    public static class Visual {
        public ForgeConfigSpec.IntValue pingR;
        public ForgeConfigSpec.IntValue pingG;
        public ForgeConfigSpec.IntValue pingB;
        public ForgeConfigSpec.BooleanValue blockOverlay;
        public ForgeConfigSpec.BooleanValue menuBackground;

        Visual(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            pingR = builder
                    .translation("ping.configgui.pingR")
                    .defineInRange("pingR", 255, 0, 255);
            pingG = builder
                    .translation("ping.configgui.pingG")
                    .defineInRange("pingG", 0, 0, 255);
            pingB = builder
                    .translation("ping.configgui.pingB")
                    .defineInRange("pingB", 0, 0, 255);
            blockOverlay = builder
                    .comment("Whether to render the Ping Menu background")
                    .translation("ping.configgui.blockOverlay")
                    .define("blockOverlay", true);
            menuBackground = builder
                    .comment("Whether to render a colored overlay on the Pinged block")
                    .translation("ping.configgui.menuBackground")
                    .define("menuBackground", true);
            builder.pop();
        }
    }

    static final ForgeConfigSpec spec = BUILDER.build();

    ForgeConfig
}