package com.girafi.ping;

import com.girafi.ping.util.TempConfig;

import java.nio.file.Path;

public class PingCommon {
    private static TempConfig config;

    public static void setConfigFolder (Path configPath) {
        config = TempConfig.load(configPath.resolve(Constants.MOD_ID + ".json").toFile());
    }

    public static TempConfig config() {
        return config;
    }
}