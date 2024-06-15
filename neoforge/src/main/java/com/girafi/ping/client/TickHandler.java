package com.girafi.ping.client;

import com.girafi.ping.Constants;
import com.girafi.ping.client.gui.PingSelectGui;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class TickHandler {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        PingHandlerHelper.pingTimer();

        Minecraft mc = Minecraft.getInstance();
        if ((mc.level == null || mc.isPaused()) && PingSelectGui.active) {
            PingSelectGui.deactivate();
        }

        KeyHelper.onTick();
    }
}