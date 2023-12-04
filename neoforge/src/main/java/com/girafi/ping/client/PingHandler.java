package com.girafi.ping.client;

import com.girafi.ping.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.event.TickEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class PingHandler {

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        PingHandlerHelper.translateWorldPing(event.getPoseStack(), event.getProjectionMatrix());
    }

    @SubscribeEvent
    public static void renderPingOffscreen(RenderGuiOverlayEvent.Post event) {
        PingHandlerHelper.renderPingOffscreen();
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        PingHandlerHelper.pingTimer();
    }
}