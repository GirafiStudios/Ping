package com.girafi.ping.client;

import com.girafi.ping.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class PingHandler {

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent.AfterParticles event) {
        PingHandlerHelper.translateWorldPing(event.getPoseStack(), event.getFrustum(), event.getRenderTick());
    }

    @SubscribeEvent
    public static void renderPingDirector(RenderGuiLayerEvent.Post event) {
        PingHandlerHelper.renderPingDirector(event.getGuiGraphics());
    }
}