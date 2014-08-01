package dmillerw.ping.proxy;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.ping.client.KeyHandler;
import dmillerw.ping.client.PingHandler;
import dmillerw.ping.client.RenderHandler;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

/**
 * @author dmillerw
 */
public class ClientProxy extends CommonProxy {

    public static int pingR;
    public static int pingG;
    public static int pingB;

    public static boolean blockOverlay;

    public static double pingAcceptDistance;
    public static int pingDuration;

    public static Configuration configuration;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        PingHandler.register();
        KeyHandler.register();
        RenderHandler.register();

        configuration = new Configuration(event.getSuggestedConfigurationFile());
        configuration.load();
    }

    @Override
    public void syncConfig() {
        Property p_pingR = configuration.get("visual", "red", 100, "Value from 0 - 255");
        Property p_pingG = configuration.get("visual", "green", 0, "Value from 0 - 255");
        Property p_pingB = configuration.get("visual", "blue", 0, "Value from 0 - 255");

        pingR = verify(p_pingR);
        pingG = verify(p_pingG);
        pingB = verify(p_pingB);

        blockOverlay = configuration.get("visual", "blockOverlay", true, "Whether to render a colored overlay on the Pinged block").getBoolean();
        pingAcceptDistance = configuration.get("general", "pingAcceptDistance", 32D, "Maximum distance a Ping can be from you and still be received").getDouble();
        pingDuration = configuration.get("general", "pingDuration", 100, "How long a Ping should remain active before disappearing").getInt();

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    private int verify(Property property) {
        int value = property.getInt();
        if (value < 0) {
            value = 0;
        } else if (value > 255) {
            value = 255;
        }
        return value;
    }
}
