package dmillerw.ping.misc;

public class Reference {
    public static final String MOD_ID = "ping";
    public static final String MOD_NAME = "Ping";
    public static final String MOD_VERSION = "%MOD_VERSION%";
    public static final String CLIENT_PROXY_CLASS = "dmillerw.ping.proxy.ClientProxy";
    public static final String SERVER_PROXY_ClASS = "dmillerw.ping.proxy.CommonProxy";
    public static final String DEPENDENCIES = "required-after:Forge@[%FORGE_VERSION%,)";
    public static final String GUI_FACTORY_CLASS = "dmillerw.ping.client.gui.config.PingGuiFactory";
}