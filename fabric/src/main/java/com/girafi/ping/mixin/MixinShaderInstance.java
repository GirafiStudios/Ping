package com.girafi.ping.mixin;

import com.girafi.ping.Constants;
import com.girafi.ping.client.ClientHandlerBase;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public class MixinShaderInstance {

    @Inject(at = @At("HEAD"), method = "reloadShaders(Lnet/minecraft/server/packs/resources/ResourceProvider;)V")
    private void renderLevel(ResourceProvider resourceProvider, CallbackInfo info) {

    }
}