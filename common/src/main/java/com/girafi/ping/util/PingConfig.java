package com.girafi.ping.util;

import net.neoforged.neoforge.common.ModConfigSpec;

public class PingConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final Visual VISUAL = new Visual(BUILDER);

    public static class General {
        public ModConfigSpec.DoubleValue pingAcceptDistance;
        public ModConfigSpec.IntValue pingDuration;
        public ModConfigSpec.BooleanValue sound;

        General(ModConfigSpec.Builder builder) {
            builder.push("general");
            pingAcceptDistance = builder
                    .comment("Maximum distance a Ping can be from you and still be received")
                    .translation("ping.configuration.pingAcceptDistance")
                    .defineInRange("pingAcceptDistance", 64.0D, 0.0D, 255.0D);
            pingDuration = builder
                    .comment("How many ticks a Ping should remain active before disappearing")
                    .translation("ping.configuration.pingDuration")
                    .defineInRange("pingDuration", 125, 0, Integer.MAX_VALUE - 1);
            sound = builder
                    .comment("Whether to play a sound when a Ping is received")
                    .translation("ping.configuration.sound")
                    .define("sound", true);
            builder.pop();
        }
    }

    public static class Visual {
        public ModConfigSpec.IntValue pingR;
        public ModConfigSpec.IntValue pingG;
        public ModConfigSpec.IntValue pingB;
        public ModConfigSpec.BooleanValue blockOverlay;
        public ModConfigSpec.BooleanValue menuBackground;

        Visual(ModConfigSpec.Builder builder) {
            builder.push("visual");
            blockOverlay = builder
                    .comment("Whether to render a colored overlay on the Pinged block")
                    .translation("ping.configuration.blockOverlay")
                    .define("blockOverlay", true);
            menuBackground = builder
                    .comment("Whether to render the Ping Menu background")
                    .translation("ping.configuration.menuBackground")
                    .define("menuBackground", true);
            builder.push("pingColor");
            pingR = builder
                    .translation("ping.configuration.pingRed")
                    .defineInRange("red", 255, 0, 255);
            pingG = builder
                    .translation("ping.configuration.pingGreen")
                    .defineInRange("green", 0, 0, 255);
            pingB = builder
                    .translation("ping.configuration.pingBlue")
                    .defineInRange("blue", 0, 0, 255);
            builder.pop();
        }
    }

    public static final ModConfigSpec spec = BUILDER.build();
}