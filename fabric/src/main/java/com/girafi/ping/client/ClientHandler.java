package com.girafi.ping.client;

import com.girafi.ping.PingCommon;
import com.girafi.ping.client.gui.PingSelectGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldExtractionContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.LevelRenderState;

public class ClientHandler implements ClientModInitializer {
    public static final RenderStateDataKey<Frustum> FRUSTUM = RenderStateDataKey.create(() -> "PingFrustum");
    public static final RenderStateDataKey<Float> PARTIAL_TICKS = RenderStateDataKey.create(() -> "PingPartialTicks");

    @Override
    public void onInitializeClient() {
        PingCommon.registerPackets();

        //Register keybinds
        KeyBindingHelper.registerKeyBinding(PingKeybinds.KEY_BINDING);
        KeyBindingHelper.registerKeyBinding(PingKeybinds.PING_ALERT);
        KeyBindingHelper.registerKeyBinding(PingKeybinds.PING_MINE);
        KeyBindingHelper.registerKeyBinding(PingKeybinds.PING_LOOK);
        KeyBindingHelper.registerKeyBinding(PingKeybinds.PING_GOTO);

        ClientTickEvents.END_CLIENT_TICK.register((mc) -> {
            PingHandlerHelper.pingTimer();
            KeyHelper.onTick();

            if ((mc.level == null || mc.isPaused()) && PingSelectGui.active) {
                PingSelectGui.deactivate();
            }
        });

        WorldRenderEvents.AFTER_ENTITIES.register((renderContext) -> {
            LevelRenderState levelRenderState = renderContext.worldState();
            PingHandlerHelper.translateWorldPing(renderContext.matrices(), levelRenderState, levelRenderState.getData(FRUSTUM), levelRenderState.getData(PARTIAL_TICKS));
        });

        WorldRenderEvents.END_EXTRACTION.register(this::stateExtraction);

        HudRenderCallback.EVENT.register((guiGraphics, delta) -> {
            PingHandlerHelper.renderPingDirector(guiGraphics, delta.getGameTimeDeltaTicks());
        });
    }

    public void stateExtraction(WorldExtractionContext context) {
        LevelRenderState levelRenderState = context.worldState();
        levelRenderState.setData(FRUSTUM, context.frustum());
        levelRenderState.setData(PARTIAL_TICKS, context.tickCounter().getGameTimeDeltaTicks());
    }
}