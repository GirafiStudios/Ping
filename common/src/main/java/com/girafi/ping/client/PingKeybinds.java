package com.girafi.ping.client;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class PingKeybinds {
    private static final String PING_CATEGORY = "ping:key.categories.ping";
    static final KeyMapping KEY_BINDING = new KeyMapping("key.ping", GLFW.GLFW_KEY_V, PING_CATEGORY);
    static final KeyMapping PING_ALERT = new KeyMapping("ping.key.alert", GLFW.GLFW_KEY_KP_4, PING_CATEGORY);
    static final KeyMapping PING_MINE = new KeyMapping("ping.key.mine", GLFW.GLFW_KEY_KP_5, PING_CATEGORY);
    static final KeyMapping PING_LOOK = new KeyMapping("ping.key.look", GLFW.GLFW_KEY_KP_6, PING_CATEGORY);
    static final KeyMapping PING_GOTO = new KeyMapping("ping.key.goto", GLFW.GLFW_KEY_KP_8, PING_CATEGORY);

    public static class Helper {
        public static boolean lastKeyState = false;
        public static boolean ignoreNextRelease = false;
    }
}
