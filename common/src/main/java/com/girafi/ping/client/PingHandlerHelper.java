package com.girafi.ping.client;

import com.girafi.ping.Constants;
import com.girafi.ping.client.util.PingRenderHelper;
import com.girafi.ping.client.util.VertexHelper;
import com.girafi.ping.data.PingType;
import com.girafi.ping.data.PingWrapper;
import com.girafi.ping.network.packet.ServerBroadcastPing;
import com.girafi.ping.util.PingConfig;
import com.girafi.ping.util.PingSounds;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.waypoints.TrackedWaypoint;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PingHandlerHelper {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/ping.png");
    public static final ResourceLocation BUTTON = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "ping/button");
    public static final ResourceLocation ALERT_BUTTON = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "ping/alert");
    public static final ResourceLocation MINE_BUTTON = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "ping/mine");
    public static final ResourceLocation LOOK_BUTTON = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "ping/look");
    public static final ResourceLocation GOTO_BUTTON = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "ping/goto");
    private static final ResourceLocation LOCATOR_BAR_ARROW_UP = ResourceLocation.withDefaultNamespace("hud/locator_bar_arrow_up");
    private static final ResourceLocation LOCATOR_BAR_ARROW_DOWN = ResourceLocation.withDefaultNamespace("hud/locator_bar_arrow_down");
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

    public static void translateWorldPing(PoseStack poseStack, LevelRenderState levelRenderState, Frustum frustum, float partialTicks) {
        if (ACTIVE_PINGS.isEmpty() || ACTIVE_PINGS.contains(null)) return;
        Minecraft mc = Minecraft.getInstance();
        CameraRenderState cameraRenderState = levelRenderState.cameraRenderState;
        Vec3 cameraPos = cameraRenderState.pos;


        if (frustum != null) {
            frustum.prepare(cameraPos.x(), cameraPos.y(), cameraPos.z());

            synchronized (ACTIVE_PINGS) {
                ACTIVE_PINGS.forEach(ping -> {

                    double px = ping.pos.getX() + 0.5D - cameraPos.x();
                    double py = ping.pos.getY() + 0.5D - cameraPos.y();
                    double pz = ping.pos.getZ() + 0.5D - cameraPos.z();

                    if (frustum.isVisible(ping.getAABB())) {
                        if (PingConfig.VISUAL.blockOverlay.get()) {
                            renderPingOverlay(ping.pos.getX() - cameraPos.x(), ping.pos.getY() - cameraPos.y(), ping.pos.getZ() - cameraPos.z(), mc, poseStack, ping);
                        }
                        renderPing(px, py, pz, poseStack, cameraRenderState, partialTicks, ping);
                    }
                });
            }
        }
    }

    public static void renderPingDirector(GuiGraphics guiGraphics, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null && mc.level != null) {
            renderPingOnLocatorBar(guiGraphics, mc, partialTicks);
        }
    }

    public static void renderPingOnLocatorBar(GuiGraphics guiGraphics, Minecraft mc, float partialTicks) {
        int windowTop = top(mc.getWindow());
        Entity cameraEntity = mc.getCameraEntity();
        if (cameraEntity instanceof Player player) {
            if (player.isCreative()) return;

            ACTIVE_PINGS.forEach(pingWrapper -> {
                        Vec3 pingPos = new Vec3(pingWrapper.pos.getX(), pingWrapper.pos.getY(), pingWrapper.pos.getZ());
                        double angleToCamera = yawAngleToCamera(mc.gameRenderer.getMainCamera(), pingPos);

                        //Keep inside exp bar
                        if (angleToCamera <= -61.0) {
                            angleToCamera = -61.0;
                        } else if (angleToCamera > 60.0) {
                            angleToCamera = 60.0;
                        }
                        int j = Mth.ceil((guiGraphics.guiWidth() - 9) / 2.0F);
                        ResourceLocation icon = pickPingIcon(pingWrapper.type);
                        int l = (int) (angleToCamera * 173.0 / 2.0 / 60.0);
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, BUTTON, j + l, windowTop - 2, 9, 9, pingWrapper.color);
                        float alpha = pingWrapper.type == PingType.ALERT ? (Mth.sin((mc.level.getGameTime() + partialTicks) * 0.25F) > 0) ? 0.85F : 0.25F : 0.85F;
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, icon, j + l, windowTop - 2, 9, 9, alpha);
                        TrackedWaypoint.PitchDirection pitchDirection = pitchDirectionToCamera(mc.gameRenderer, pingPos);
                        if (pitchDirection != TrackedWaypoint.PitchDirection.NONE) {
                            int yOffset;
                            ResourceLocation directionIcon;
                            if (pitchDirection == TrackedWaypoint.PitchDirection.DOWN) {
                                yOffset = 6;
                                directionIcon = LOCATOR_BAR_ARROW_DOWN;
                            } else {
                                yOffset = -6;
                                directionIcon = LOCATOR_BAR_ARROW_UP;
                            }

                            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, directionIcon, j + l + 1, windowTop + yOffset, 7, 5);
                        }
                    }
            );
        }
    }

    public static ResourceLocation pickPingIcon(PingType pingType) {
        return switch (pingType) {
            case ALERT -> ALERT_BUTTON;
            case MINE -> MINE_BUTTON;
            case GOTO -> GOTO_BUTTON;
            case LOOK -> LOOK_BUTTON;
            case BACKGROUND -> BUTTON;
        };
    }

    public static TrackedWaypoint.PitchDirection pitchDirectionToCamera(TrackedWaypoint.Projector projector, Vec3 pingPos) {
        Vec3 vec3 = projector.projectPointToScreen(pingPos);
        boolean bl = vec3.z > (double) 1.0F;
        double d = bl ? -vec3.y : vec3.y;
        if (d < (double) -1.0F) {
            return TrackedWaypoint.PitchDirection.DOWN;
        } else if (d > (double) 1.0F) {
            return TrackedWaypoint.PitchDirection.UP;
        } else {
            if (bl) {
                if (vec3.y > (double) 0.0F) {
                    return TrackedWaypoint.PitchDirection.UP;
                }

                if (vec3.y < (double) 0.0F) {
                    return TrackedWaypoint.PitchDirection.DOWN;
                }
            }
            return TrackedWaypoint.PitchDirection.NONE;
        }
    }

    public static double yawAngleToCamera(TrackedWaypoint.Camera camera, Vec3 pingPos) {
        Vec3 vec3 = camera.position().subtract(pingPos).rotateClockwise90();
        float f = (float) Mth.atan2(vec3.z(), vec3.x()) * (180F / (float) Math.PI);
        return Mth.degreesDifference(camera.yaw(), f);
    }

    public static int top(Window window) {
        return window.getGuiScaledHeight() - 24 - 5;
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

    public static void renderPing(double px, double py, double pz, PoseStack poseStack, CameraRenderState cameraRenderState, float partialTicks, PingWrapper ping) {
        Minecraft mc = Minecraft.getInstance();
        poseStack.pushPose();
        poseStack.translate(px, py, pz);
        poseStack.mulPose(cameraRenderState.orientation);
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
        float alpha = ping.type == PingType.ALERT ? (Mth.sin((mc.level.getGameTime() + partialTicks) * 0.25F) > 0) ? 0.85F : 0.25F : 0.85F;
        VertexHelper.renderPosTexColorNoZ(vertexBuilder, matrix4f, min, max, ping.type.getMinU(), ping.type.getMaxV(), 1.0F, 1.0F, 1.0F, alpha);
        VertexHelper.renderPosTexColorNoZ(vertexBuilder, matrix4f, max, max, ping.type.getMaxU(), ping.type.getMaxV(), 1.0F, 1.0F, 1.0F, alpha);
        VertexHelper.renderPosTexColorNoZ(vertexBuilder, matrix4f, max, min, ping.type.getMaxU(), ping.type.getMinV(), 1.0F, 1.0F, 1.0F, alpha);
        VertexHelper.renderPosTexColorNoZ(vertexBuilder, matrix4f, min, min, ping.type.getMinU(), ping.type.getMinV(), 1.0F, 1.0F, 1.0F, alpha);
        buffer.endBatch(pingType);

        poseStack.popPose();
    }

    public static void renderPingOverlay(double x, double y, double z, Minecraft mc, PoseStack poseStack, PingWrapper ping) {
        float padding = 0F + (0.20F * (float) ping.animationTimer / (float) 20);
        float box = 1 + padding + padding;
        BlockState overlayBlock = Blocks.WHITE_STAINED_GLASS.defaultBlockState();

        poseStack.pushPose();
        poseStack.translate(x + 0.5, y + 0.5, z + 0.5);
        PingRenderHelper.drawBlockOverlay(box, box, box, poseStack, overlayBlock, ping, 175);
        poseStack.popPose();
    }
}