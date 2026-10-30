package com.girafi.ping.client;

import com.girafi.ping.Constants;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class PingKeybinds {
    private static final KeyMapping.Category PING_CATEGORY =  KeyMapping.Category.register(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "key.categories.ping"));
    public static final KeyMapping KEY_BINDING = new KeyMapping("key.ping", InputConstants.KEY_V, PING_CATEGORY);
    public static final KeyMapping PING_ALERT = new KeyMapping("ping.key.alert", InputConstants.KEY_NUMPAD4, PING_CATEGORY);
    public static final KeyMapping PING_MINE = new KeyMapping("ping.key.mine", InputConstants.KEY_NUMPAD5, PING_CATEGORY);
    public static final KeyMapping PING_LOOK = new KeyMapping("ping.key.look", InputConstants.KEY_NUMPAD6, PING_CATEGORY);
    public static final KeyMapping PING_GOTO = new KeyMapping("ping.key.goto", InputConstants.KEY_NUMPAD8, PING_CATEGORY);
}