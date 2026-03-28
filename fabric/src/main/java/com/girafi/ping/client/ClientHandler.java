package com.girafi.ping.client;

import com.girafi.ping.PingCommon;
import com.girafi.ping.client.gui.PingSelectGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.resources.Identifier;

public class ClientHandler implements ClientModInitializer {
    public static final RenderStateDataKey<Frustum> FRUSTUM = RenderStateDataKey.create(() -> "PingFrustum");
    public static final RenderStateDataKey<Float> PARTIAL_TICKS = RenderStateDataKey.create(() -> "PingPartialTicks");

    @Override
    public void onInitializeClient() {
        PingCommon.registerPackets();

        //Register keybinds
        KeyMappingHelper.registerKeyMapping(PingKeybinds.KEY_BINDING);
        KeyMappingHelper.registerKeyMapping(PingKeybinds.PING_ALERT);
        KeyMappingHelper.registerKeyMapping(PingKeybinds.PING_MINE);
        KeyMappingHelper.registerKeyMapping(PingKeybinds.PING_LOOK);
        KeyMappingHelper.registerKeyMapping(PingKeybinds.PING_GOTO);

        ClientTickEvents.END_CLIENT_TICK.register((mc) -> {
            PingHandlerHelper.pingTimer();
            KeyHelper.onTick();

            if ((mc.level == null || mc.isPaused()) && PingSelectGui.active) {
                PingSelectGui.deactivate();
            }
        });

        LevelRenderEvents.AFTER_TRANSLUCENT_FEATURES.register((renderContext) -> {
            LevelRenderState levelRenderState = renderContext.levelState();
            PingHandlerHelper.translateWorldPing(renderContext.poseStack(), levelRenderState, levelRenderState.getData(FRUSTUM), levelRenderState.getData(PARTIAL_TICKS));
        });

        LevelRenderEvents.END_EXTRACTION.register(this::stateExtraction);

        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath("ping", "overlay"), (guiGraphics, delta) -> {
            PingHandlerHelper.renderPingDirector(guiGraphics, delta.getGameTimeDeltaTicks());
        });
    }

    public void stateExtraction(LevelExtractionContext context) {
        LevelRenderState levelRenderState = context.levelState();
        levelRenderState.setData(FRUSTUM, context.camera().getCullFrustum());
        levelRenderState.setData(PARTIAL_TICKS, context.deltaTracker().getGameTimeDeltaTicks());
    }
}