package com.girafi.ping.mixin;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinShaderInstance {

    @Inject(at = @At("HEAD"), method = "reloadShaders(Lnet/minecraft/server/packs/resources/ResourceProvider;)V")
    private void renderLevel(ResourceProvider resourceProvider, CallbackInfo info) {

    }
}