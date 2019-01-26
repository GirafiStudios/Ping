/*package dmillerw.ping.client.gui.config;

import dmillerw.ping.client.ClientHandler;
import dmillerw.ping.util.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.DefaultGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class PingGuiFactory extends DefaultGuiFactory {

    public PingGuiFactory() {
        super(Reference.MOD_ID, GuiConfig.getAbridgedConfigPath(ClientHandler.configuration.toString()));
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new GuiConfig(parentScreen, getElements(), Reference.MOD_ID, false, false, title);
    }

    private static List<IConfigElement> getElements() {
        List<IConfigElement> list = new ArrayList<>();
        list.addAll((new ConfigElement(ClientHandler.configuration.getCategory("general"))).getChildElements());
        list.addAll((new ConfigElement(ClientHandler.configuration.getCategory("visual"))).getChildElements());
        return list;
    }
}*/