package dmillerw.ping.client.gui;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import dmillerw.ping.proxy.ClientProxy;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmillerw
 */
public class PingGuiConfig extends GuiConfig {

    private static List<IConfigElement> getElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.addAll((new ConfigElement(ClientProxy.configuration.getCategory("general"))).getChildElements());
        list.addAll((new ConfigElement(ClientProxy.configuration.getCategory("visual"))).getChildElements());
        return list;
    }

    public PingGuiConfig(GuiScreen parent) {
        super(
                parent,
                getElements(),
                "Ping",
                false,
                false,
                GuiConfig.getAbridgedConfigPath(ClientProxy.configuration.toString())
        );
    }
}
