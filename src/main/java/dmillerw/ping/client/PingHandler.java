package dmillerw.ping.client;

import com.mojang.blaze3d.platform.GlStateManager;
import dmillerw.ping.Ping;
import dmillerw.ping.data.PingType;
import dmillerw.ping.data.PingWrapper;
import dmillerw.ping.network.packet.ServerBroadcastPing;
import dmillerw.ping.util.Config;
import dmillerw.ping.util.GLUUtils;
import dmillerw.ping.util.PingRenderHelper;
import dmillerw.ping.util.PingSounds;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@EventBusSubscriber(modid = Ping.MOD_ID, value = Dist.CLIENT)
public class PingHandler {
    public static final PingHandler INSTANCE = new PingHandler();
    public static final ResourceLocation TEXTURE = new ResourceLocation(Ping.MOD_ID, "textures/ping.png");
    private static List<PingWrapper> active_pings = new ArrayList<>();

    public void onPingPacket(ServerBroadcastPing packet) {
        Minecraft mc = Minecraft.getInstance();
        if (MathHelper.sqrt(mc.player.getDistanceSq(packet.ping.pos.getX(), packet.ping.pos.getY(), packet.ping.pos.getZ())) <= Config.GENERAL.pingAcceptDistance.get()) {
            if (Config.GENERAL.sound.get()) {
                mc.getSoundHandler().play(new SimpleSound(PingSounds.BLOOP, SoundCategory.PLAYERS, 0.25F, 1.0F, packet.ping.pos.getX(), packet.ping.pos.getY(), packet.ping.pos.getZ()));
            }
            packet.ping.timer = Config.GENERAL.pingDuration.get();
            active_pings.add(packet.ping);
        }
    }

    @SubscribeEvent
    public static void onRenderWorld(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Entity renderEntity = mc.getRenderViewEntity();
        if (renderEntity == null) return;
        double interpX = renderEntity.prevPosX + (renderEntity.posX - renderEntity.prevPosX) * event.getPartialTicks();
        double interpY = renderEntity.prevPosY + (renderEntity.posY - renderEntity.prevPosY) * event.getPartialTicks();
        double interpZ = renderEntity.prevPosZ + (renderEntity.posZ - renderEntity.prevPosZ) * event.getPartialTicks();

        Frustum camera = new Frustum();
        camera.setPosition(interpX, interpY, interpZ);

        for (PingWrapper ping : active_pings) {
            double px = ping.pos.getX() + 0.5 - interpX;
            double py = ping.pos.getY() + 0.5 - interpY - renderEntity.getEyeHeight();
            double pz = ping.pos.getZ() + 0.5 - interpZ;

            if (camera.isBoundingBoxInFrustum(ping.getAABB())) {
                ping.isOffscreen = false;
                if (Config.VISUAL.blockOverlay.get()) {
                    renderPingOverlay(ping.pos.getX() - TileEntityRendererDispatcher.staticPlayerX, ping.pos.getY() - TileEntityRendererDispatcher.staticPlayerY, ping.pos.getZ() - TileEntityRendererDispatcher.staticPlayerZ, ping);
                }
                renderPing(px, py, pz, renderEntity, ping);
            } else {
                ping.isOffscreen = true;
                translatePingCoordinates(px, py, pz, ping);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            for (PingWrapper ping : active_pings) {
                if (!ping.isOffscreen) {
                    continue;
                }

                int width = mc.mainWindow.getWidth();
                int height = mc.mainWindow.getHeight();

                int x1 = -(width / 2) + 32;
                int y1 = -(height / 2) + 32;
                int x2 = (width / 2) - 32;
                int y2 = (height / 2) - 32;

                double pingX = ping.screenX;
                double pingY = ping.screenY;

                pingX -= width * 0.5D;
                pingY -= height * 0.5D;

                //TODO Fix that player rotation is not being taken into account
                double angle = Math.atan2(pingY, pingX);
                angle += (Math.toRadians(90));
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

                GlStateManager.pushMatrix();

                Minecraft.getInstance().textureManager.bindTexture(TEXTURE);

                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferBuilder = tessellator.getBuffer();

                bufferBuilder.setTranslation(pingX / 2, pingY / 2, 0);

                float min = -8;
                float max = 8;

                int alpha = ping.type == PingType.ALERT ? (int) (1.3F + Math.sin(mc.world.getDayTime())) : (int) 1.0F;

                // Ping Notice Background
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int r = ping.color >> 16 & 255;
                int g = ping.color >> 8 & 255;
                int b = ping.color & 255;

                bufferBuilder.pos(min, max, 0).tex(PingType.BACKGROUND.minU, PingType.BACKGROUND.maxV).color(r, g, b, 255).endVertex();
                bufferBuilder.pos(max, max, 0).tex(PingType.BACKGROUND.maxU, PingType.BACKGROUND.maxV).color(r, g, b, 255).endVertex();
                bufferBuilder.pos(max, min, 0).tex(PingType.BACKGROUND.maxU, PingType.BACKGROUND.minV).color(r, g, b, 255).endVertex();
                bufferBuilder.pos(min, min, 0).tex(PingType.BACKGROUND.minU, PingType.BACKGROUND.minV).color(r, g, b, 255).endVertex();
                tessellator.draw();

                // Ping Notice Icon
                bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                bufferBuilder.pos(min, max, 0).tex(ping.type.minU, ping.type.maxV).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
                bufferBuilder.pos(max, max, 0).tex(ping.type.maxU, ping.type.maxV).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
                bufferBuilder.pos(max, min, 0).tex(ping.type.maxU, ping.type.minV).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
                bufferBuilder.pos(min, min, 0).tex(ping.type.minU, ping.type.minV).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
                tessellator.draw();

                bufferBuilder.setTranslation(0, 0, 0);

                GlStateManager.popMatrix();
            }
        }
    }


    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Iterator<PingWrapper> iterator = active_pings.iterator();
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

    private static void renderPing(double px, double py, double pz, Entity renderEntity, PingWrapper ping) {
        GlStateManager.pushMatrix();

        GlStateManager.disableDepthTest();
        GlStateManager.translated(px, py, pz);

        GlStateManager.rotatef(-renderEntity.rotationYaw, 0, 1, 0);
        GlStateManager.rotatef(renderEntity.rotationPitch, 1, 0, 0);
        GlStateManager.rotated(180, 0, 0, 1);

        Minecraft.getInstance().textureManager.bindTexture(TEXTURE);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        float min = -0.25F - (0.25F * (float) ping.animationTimer / 20F);
        float max = 0.25F + (0.25F * (float) ping.animationTimer / 20F);

        // Block Overlay Background
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        int r = ping.color >> 16 & 255;
        int g = ping.color >> 8 & 255;
        int b = ping.color & 255;
        bufferBuilder.pos(min, max, 0).tex(PingType.BACKGROUND.minU, PingType.BACKGROUND.maxV).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(max, max, 0).tex(PingType.BACKGROUND.maxU, PingType.BACKGROUND.maxV).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(max, min, 0).tex(PingType.BACKGROUND.maxU, PingType.BACKGROUND.minV).color(r, g, b, 255).endVertex();
        bufferBuilder.pos(min, min, 0).tex(PingType.BACKGROUND.minU, PingType.BACKGROUND.minV).color(r, g, b, 255).endVertex();
        tessellator.draw();

        int alpha = ping.type == PingType.ALERT ? (int) (1.3F + Math.sin(Minecraft.getInstance().world.getDayTime())) : 175;

        // Block Overlay Icon
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferBuilder.pos(min, max, 0).tex(ping.type.minU, ping.type.maxV).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
        bufferBuilder.pos(max, max, 0).tex(ping.type.maxU, ping.type.maxV).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
        bufferBuilder.pos(max, min, 0).tex(ping.type.maxU, ping.type.minV).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
        bufferBuilder.pos(min, min, 0).tex(ping.type.minU, ping.type.minV).color(1.0F, 1.0F, 1.0F, alpha).endVertex();
        tessellator.draw();

        GlStateManager.enableDepthTest();
        GlStateManager.popMatrix();
    }

    private static void renderPingOverlay(double x, double y, double z, PingWrapper ping) {
        TextureAtlasSprite icon = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(new ItemStack(Blocks.WHITE_STAINED_GLASS)).getParticleTexture();

        float padding = 0F + (0.20F * (float) ping.animationTimer / (float) 20);
        float box = 1 + padding + padding;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableDepthTest();

        GlStateManager.translated(x + 0.5, y + 0.5, z + 0.5);
        PingRenderHelper.drawBlockOverlay(box, box, box, icon, ping.color, 175);
        GlStateManager.translated(0, 0, 0);

        GlStateManager.enableDepthTest();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private static void translatePingCoordinates(double px, double py, double pz, PingWrapper ping) {
        FloatBuffer screenCoords = BufferUtils.createFloatBuffer(4);
        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
        FloatBuffer projection = BufferUtils.createFloatBuffer(16);

        GlStateManager.getMatrix(GL11.GL_MODELVIEW_MATRIX, modelView);
        GlStateManager.getMatrix(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetIntegerv(GL11.GL_VIEWPORT, viewport);

        if (GLUUtils.gluProject((float) px, (float) py, (float) pz, modelView, projection, viewport, screenCoords)) {
            ping.screenX = screenCoords.get(0);
            ping.screenY = screenCoords.get(1);
        }
    }
}