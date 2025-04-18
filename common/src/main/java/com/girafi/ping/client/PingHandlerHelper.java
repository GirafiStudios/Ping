package com.girafi.ping.client;

import com.girafi.ping.Constants;
import com.girafi.ping.client.util.PingRenderHelper;
import com.girafi.ping.client.util.VertexHelper;
import com.girafi.ping.data.PingType;
import com.girafi.ping.data.PingWrapper;
import com.girafi.ping.network.packet.ServerBroadcastPing;
import com.girafi.ping.util.PingConfig;
import com.girafi.ping.util.PingSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PingHandlerHelper {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/ping.png");
    private static final List<PingWrapper> ACTIVE_PINGS = Collections.synchronizedList(new ArrayList<>());

    public static void onPingPacket(ServerBroadcastPing packet) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && Mth.sqrt((float) mc.player.distanceToSqr(packet.ping.pos.getX(), packet.ping.pos.getY(), packet.ping.pos.getZ())) <= PingConfig.GENERAL.pingAcceptDistance.get()) {
            synchronized (PingSounds.BLOOP.get()) { //Workaround for crash when playing a lot of ping "bloop" sounds in rapid succession
                if (PingConfig.GENERAL.sound.get()) {
                    mc.getSoundManager().play(new SimpleSoundInstance(PingSounds.BLOOP.get(), SoundSource.PLAYERS, 0.25F, 1.0F, mc.player.getRandom(), packet.ping.pos.getX(), packet.ping.pos.getY(), packet.ping.pos.getZ()));
                }
            }
            packet.ping.timer = PingConfig.GENERAL.pingDuration.get();
            ACTIVE_PINGS.add(packet.ping);
        }
    }

    public static void translateWorldPing(PoseStack poseStack, Frustum clippingHelper, float partialTicks) {
        if (ACTIVE_PINGS.isEmpty() || ACTIVE_PINGS.contains(null)) return;
        Minecraft mc = Minecraft.getInstance();
        Camera camera = mc.getBlockEntityRenderDispatcher().camera;
        Vec3 cameraPos = camera.getPosition();

        clippingHelper.prepare(cameraPos.x(), cameraPos.y(), cameraPos.z());

        synchronized (ACTIVE_PINGS) {
            ACTIVE_PINGS.forEach(ping -> {

                double px = ping.pos.getX() + 0.5D - cameraPos.x();
                double py = ping.pos.getY() + 0.5D - cameraPos.y();
                double pz = ping.pos.getZ() + 0.5D - cameraPos.z();

                if (clippingHelper.isVisible(ping.getAABB())) {
                    ping.isOffscreen = false;
                    if (PingConfig.VISUAL.blockOverlay.get()) {
                        renderPingOverlay(ping.pos.getX() - cameraPos.x(), ping.pos.getY() - cameraPos.y(), ping.pos.getZ() - cameraPos.z(), poseStack, ping);
                    }
                    renderPing(px, py, pz, poseStack, camera, ping);
                } else {
                    ping.isOffscreen = true;
                    translatePingCoordinates(mc, ping, partialTicks);
                }
            });
        }
    }

    public static void renderPingOffscreen(GuiGraphics guiGraphics) {
        synchronized (ACTIVE_PINGS) {
            Minecraft mc = Minecraft.getInstance();
            PoseStack poseStack = guiGraphics.pose();

            for (PingWrapper ping : ACTIVE_PINGS) {
                if (!ping.isOffscreen || mc.screen != null || mc.getDebugOverlay().showDebugScreen()) {
                    continue;
                }
                int width = mc.getWindow().getScreenWidth();
                int height = mc.getWindow().getScreenHeight();

                int x1 = -(width / 2) + 32;
                int y1 = -(height / 2) + 32;
                int x2 = (width / 2) - 32;
                int y2 = (height / 2) - 32;

                double pingX = ping.screenX;
                double pingY = ping.screenY;

                pingX -= width * 0.5D;
                pingY -= height * 0.5D;

                double angle = Math.atan2(pingY, pingX);
                angle += (Math.toRadians(mc.gameRenderer.fovModifier));
                double cos = Math.cos(angle);
                double sin = Math.sin(angle);
                double m = cos / sin;

                if (cos > 0) {
                    pingX = y2 / m;
                    pingY = y2;
                } else {
                    pingX = y1 / m;
                    pingY = y1;
                }

                if (pingX > x2) {
                    pingX = x2;
                    pingY = x2 * m;
                } else if (pingX < x1) {
                    pingX = x1;
                    pingY = x1 * m;
                }

                pingX += width * 0.5D;
                pingY += height * 0.5D;

                poseStack.pushPose();
                PoseStack.Pose matrixEntry = poseStack.last();
                poseStack.translate(pingX / 2, pingY / 2, 0);

                MultiBufferSource.BufferSource source = mc.renderBuffers().bufferSource();
                VertexConsumer vertexConsumer = source.getBuffer(RenderType.guiTextured(TEXTURE));

                final Matrix4f matrix4f = matrixEntry.pose();

                float min = -8;
                float max = 8;

                // Ping Notice Background
                int r = ping.color >> 16 & 255;
                int g = ping.color >> 8 & 255;
                int b = ping.color & 255;
                VertexHelper.renderPosTexColorNoZ(vertexConsumer, matrix4f, min, max, PingType.BACKGROUND.getMinU(), PingType.BACKGROUND.getMaxV(), r, g, b, 255);
                VertexHelper.renderPosTexColorNoZ(vertexConsumer, matrix4f, max, max, PingType.BACKGROUND.getMaxU(), PingType.BACKGROUND.getMaxV(), r, g, b, 255);
                VertexHelper.renderPosTexColorNoZ(vertexConsumer, matrix4f, max, min, PingType.BACKGROUND.getMaxU(), PingType.BACKGROUND.getMinV(), r, g, b, 255);
                VertexHelper.renderPosTexColorNoZ(vertexConsumer, matrix4f, min, min, PingType.BACKGROUND.getMinU(), PingType.BACKGROUND.getMinV(), r, g, b, 255);

                // Ping Notice Icon
                float alpha = ping.type == PingType.ALERT ? mc.level != null ? (float) (1.0F + (0.01D * Math.sin(mc.level.getDayTime()))) : 0.85F : 0.85F;
                VertexHelper.renderPosTexColorNoZ(vertexConsumer, matrix4f, min, max, ping.type.getMinU(), ping.type.getMaxV(), 1.0F, 1.0F, 1.0F, alpha);
                VertexHelper.renderPosTexColorNoZ(vertexConsumer, matrix4f, max, max, ping.type.getMaxU(), ping.type.getMaxV(), 1.0F, 1.0F, 1.0F, alpha);
                VertexHelper.renderPosTexColorNoZ(vertexConsumer, matrix4f, max, min, ping.type.getMaxU(), ping.type.getMinV(), 1.0F, 1.0F, 1.0F, alpha);
                VertexHelper.renderPosTexColorNoZ(vertexConsumer, matrix4f, min, min, ping.type.getMinU(), ping.type.getMinV(), 1.0F, 1.0F, 1.0F, alpha);

                poseStack.popPose();
            }
        }
    }

    public static void pingTimer() {
        synchronized (ACTIVE_PINGS) {
            Iterator<PingWrapper> iterator = ACTIVE_PINGS.iterator();
            while (iterator.hasNext()) {
                PingWrapper pingWrapper = iterator.next();
                if (pingWrapper.animationTimer > 0) {
                    pingWrapper.animationTimer -= 5;
                }
                pingWrapper.timer--;

                if (pingWrapper.timer <= 0) {
                    iterator.remove();
                }
            }
        }
    }

    public static void renderPing(double px, double py, double pz, PoseStack poseStack, Camera camera, PingWrapper ping) {
        Minecraft mc = Minecraft.getInstance();
        poseStack.pushPose();
        poseStack.translate(px, py, pz);
        poseStack.mulPose(Axis.YP.rotationDegrees(-camera.getYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));

        PoseStack.Pose matrixEntry = poseStack.last();
        Matrix4f matrix4f = matrixEntry.pose();
        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
        RenderType pingType = PingRenderType.pingIcon(TEXTURE);
        VertexConsumer vertexBuilder = buffer.getBuffer(pingType);

        float min = -0.25F - (0.25F * (float) ping.animationTimer / 20F);
        float max = 0.25F + (0.25F * (float) ping.animationTimer / 20F);

        // Block Overlay Background
        int r = ping.color >> 16 & 255;
        int g = ping.color >> 8 & 255;
        int b = ping.color & 255;
        VertexHelper.renderPosTexColorNoZ(vertexBuilder, matrix4f, min, max, PingType.BACKGROUND.getMinU(), PingType.BACKGROUND.getMaxV(), r, g, b, 255);
        VertexHelper.renderPosTexColorNoZ(vertexBuilder, matrix4f, max, max, PingType.BACKGROUND.getMaxU(), PingType.BACKGROUND.getMaxV(), r, g, b, 255);
        VertexHelper.renderPosTexColorNoZ(vertexBuilder, matrix4f, max, min, PingType.BACKGROUND.getMaxU(), PingType.BACKGROUND.getMinV(), r, g, b, 255);
        VertexHelper.renderPosTexColorNoZ(vertexBuilder, matrix4f, min, min, PingType.BACKGROUND.getMinU(), PingType.BACKGROUND.getMinV(), r, g, b, 255);

        // Block Overlay Icon
        float alpha = ping.type == PingType.ALERT ? mc.level != null ? (float) (1.0F + (0.01D * Math.sin(mc.level.getDayTime()))) : 0.85F : 0.85F;
        VertexHelper.renderPosTexColorNoZ(vertexBuilder, matrix4f, min, max, ping.type.getMinU(), ping.type.getMaxV(), 1.0F, 1.0F, 1.0F, alpha);
        VertexHelper.renderPosTexColorNoZ(vertexBuilder, matrix4f, max, max, ping.type.getMaxU(), ping.type.getMaxV(), 1.0F, 1.0F, 1.0F, alpha);
        VertexHelper.renderPosTexColorNoZ(vertexBuilder, matrix4f, max, min, ping.type.getMaxU(), ping.type.getMinV(), 1.0F, 1.0F, 1.0F, alpha);
        VertexHelper.renderPosTexColorNoZ(vertexBuilder, matrix4f, min, min, ping.type.getMinU(), ping.type.getMinV(), 1.0F, 1.0F, 1.0F, alpha);
        buffer.endBatch(pingType);

        poseStack.popPose();
    }

    public static void renderPingOverlay(double x, double y, double z, PoseStack poseStack, PingWrapper ping) {
        TextureAtlasSprite icon = Minecraft.getInstance().getBlockRenderer().getBlockModel(Blocks.WHITE_STAINED_GLASS.defaultBlockState()).particleIcon();
        float padding = 0F + (0.20F * (float) ping.animationTimer / (float) 20);
        float box = 1 + padding + padding;

        poseStack.pushPose();
        poseStack.translate(x + 0.5, y + 0.5, z + 0.5);
        PingRenderHelper.drawBlockOverlay(box, box, box, poseStack, icon, ping.color, 175);
        poseStack.translate(0, 0, 0);
        poseStack.popPose();
    }

    public static void translatePingCoordinates(Minecraft mc, PingWrapper ping, float partialTicks) { //Works-ish, but is not perfect
        Player player = mc.player;
        Camera camera = mc.gameRenderer.getMainCamera();
        int blockX = ping.pos.getX();
        int blockZ = ping.pos.getY();

        if (player != null) {
            Vector2f hP = new Vector2f(blockX, blockZ);
            Vector2f vP = new Vector2f(hP.length(), ping.pos.getY());

            Vector2f playerEyePos = new Vector2f((float) player.getEyePosition().x, (float) player.getEyePosition(partialTicks).z);

            Vector2f lookAngleH = new Vector2f(camera.getLookVector().x, camera.getLookVector().z);
            float angleBetweenVecH = lookAngleH.angle(hP.sub(playerEyePos));

            Vector2f lookAngleV = new Vector2f(camera.getLookVector().x, camera.getLookVector().z);
            float angleBetweenVecV = lookAngleV.angle(vP.sub(playerEyePos));

            float fov = mc.gameRenderer.fovModifier;
            double clipH = Math.sin(angleBetweenVecH) / Math.sin(Math.toRadians(fov));
            double clipV = Math.sin(angleBetweenVecV) / Math.sin(Math.toRadians(fov));
            double screenCoordH = (clipH + 1) * (mc.getWindow().getGuiScaledWidth() * 0.5D);
            double screenCoordV = (clipV + 1) * (mc.getWindow().getGuiScaledHeight() * 0.5D);

            ping.screenX = screenCoordH;
            ping.screenY = screenCoordV;
        }
    }
}