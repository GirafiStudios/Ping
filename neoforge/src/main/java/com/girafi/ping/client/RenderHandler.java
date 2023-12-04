package com.girafi.ping.client;

import com.girafi.ping.Constants;
import com.girafi.ping.client.gui.PingSelectGui;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;
import net.neoforged.neoforge.event.TickEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class RenderHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();
            if ((mc.level == null || mc.isPaused()) && PingSelectGui.active) {
                PingSelectGui.deactivate();
            }
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null && !mc.options.hideGui && !mc.isPaused() && PingSelectGui.active) {
            RenderHandlerHelper.renderGui(event.getGuiGraphics());
            RenderHandlerHelper.renderText(event.getGuiGraphics());
        }
    }
}