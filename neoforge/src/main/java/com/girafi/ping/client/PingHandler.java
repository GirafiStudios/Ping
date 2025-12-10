package com.girafi.ping.client;

import com.girafi.ping.Constants;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextKey;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ExtractLevelRenderStateEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class PingHandler {
    public static final ContextKey<Frustum> PING_FRUSTUM = new ContextKey<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "ping_frustum"));
    public static final ContextKey<Float> PARTIAL_TICKS = new ContextKey<>(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "partial_ticks"));

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent.AfterParticles event) {
        LevelRenderState levelRenderState = event.getLevelRenderState();
        PingHandlerHelper.translateWorldPing(event.getPoseStack(), levelRenderState, levelRenderState.getRenderData(PING_FRUSTUM), levelRenderState.getRenderData(PARTIAL_TICKS));
    }

    @SubscribeEvent
    public static void extract(ExtractLevelRenderStateEvent event) {
        event.getRenderState().setRenderData(PING_FRUSTUM, event.getFrustum());
        event.getRenderState().setRenderData(PARTIAL_TICKS, event.getDeltaTracker().getGameTimeDeltaTicks());
    }

    @SubscribeEvent
    public static void renderPingDirector(RenderGuiLayerEvent.Post event) {
        PingHandlerHelper.renderPingDirector(event.getGuiGraphics(), event.getPartialTick().getGameTimeDeltaTicks());
    }
}