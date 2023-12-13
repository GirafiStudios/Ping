package com.girafi.ping.client;

import com.girafi.ping.Constants;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class PingHandler {

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
            PingHandlerHelper.translateWorldPing(event.getPoseStack(), event.getProjectionMatrix(), event.getPartialTick());
        }
    }

    @SubscribeEvent
    public static void renderPingOffscreen(RenderGuiOverlayEvent.Post event) {
        PingHandlerHelper.renderPingOffscreen(event.getGuiGraphics());
    }
}