package com.girafi.ping.client;

import com.girafi.ping.Constants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class PingHandler {

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) {
            PingHandlerHelper.translateWorldPing(event.getPoseStack(), event.getProjectionMatrix());
        }
    }

    @SubscribeEvent
    public static void renderPingOffscreen(ScreenEvent.Render.Post event) {
        PingHandlerHelper.renderPingOffscreen(event.getGuiGraphics().pose());
    }
}