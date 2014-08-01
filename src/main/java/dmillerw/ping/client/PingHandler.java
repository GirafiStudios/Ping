package dmillerw.ping.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import dmillerw.ping.data.PingType;
import dmillerw.ping.data.PingWrapper;
import dmillerw.ping.network.packet.ServerBroadcastPing;
import dmillerw.ping.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author dmillerw
 */
public class PingHandler {

    public static final PingHandler INSTANCE = new PingHandler();

    public static final ResourceLocation TEXTURE = new ResourceLocation("ping:textures/ping.png");

    public static void register() {
        FMLCommonHandler.instance().bus().register(INSTANCE);
        MinecraftForge.EVENT_BUS.register(INSTANCE);
    }

    private RenderBlocks renderBlocks;

    private List<PingWrapper> activePings = new ArrayList<PingWrapper>();

    public void onPingPacket(ServerBroadcastPing packet) {
        if (Minecraft.getMinecraft().thePlayer.getDistance(packet.ping.x, packet.ping.y, packet.ping.z) <= ClientProxy.pingAcceptDistance) {
            packet.ping.timer = ClientProxy.pingDuration;
            activePings.add(packet.ping);
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        Entity renderEntity = mc.renderViewEntity;
        double interpX = renderEntity.prevPosX + (renderEntity.posX - renderEntity.prevPosX) * event.partialTicks;
        double interpY = renderEntity.prevPosY + (renderEntity.posY - renderEntity.prevPosY) * event.partialTicks;
        double interpZ = renderEntity.prevPosZ + (renderEntity.posZ - renderEntity.prevPosZ) * event.partialTicks;

        Frustrum camera = new Frustrum();
        camera.setPosition(interpX, interpY, interpZ);

        for (PingWrapper ping : activePings) {
            double px = ping.x + 0.5 - interpX;
            double py = ping.y + 0.5 - interpY;
            double pz = ping.z + 0.5 - interpZ;

            if (camera.isBoundingBoxInFrustum(ping.getAABB())) {
                ping.isOffscreen = false;
                if (ClientProxy.blockOverlay) {
                    renderPingOverlay(ping.x - TileEntityRendererDispatcher.staticPlayerX, ping.y - TileEntityRendererDispatcher.staticPlayerY, ping.z - TileEntityRendererDispatcher.staticPlayerZ, ping);
                }
                renderPing(px, py, pz, renderEntity, ping);
            } else {
                ping.isOffscreen = true;
                translatePingCoordinates(px, py, pz, ping);
            }
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            for (PingWrapper ping : activePings) {
                if (!ping.isOffscreen) {
                    continue;
                }

                int width = mc.displayWidth;
                int height = mc.displayHeight;

                int x1 = -(width / 2) + 32;
                int y1 = -(height / 2) + 32;
                int x2 = (width / 2) - 32;
                int y2 = (height / 2) - 32;

                double pingX = ping.screenX;
                double pingY = ping.screenY;

                pingX -= width / 2;
                pingY -= height / 2;

                double angle = Math.atan2(pingY, pingX);
                angle += (Math.toRadians(90));
                double cos = Math.cos(angle);
                double sin = Math.sin(angle);
                double m = cos / sin;

                if (cos > 0){
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

                pingX += width / 2;
                pingY += height / 2;

                GL11.glPushMatrix();

                Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);

                Tessellator tessellator = Tessellator.instance;

                tessellator.setTranslation(pingX / 2, pingY / 2, 0);

                float min = -8;
                float max =  8;

                // Background
                tessellator.startDrawingQuads();
                tessellator.setColorOpaque_I(ping.color);
                tessellator.addVertexWithUV(min, max, 0, PingType.BACKGROUND.minU, PingType.BACKGROUND.maxV);
                tessellator.addVertexWithUV(max, max, 0, PingType.BACKGROUND.maxU, PingType.BACKGROUND.maxV);
                tessellator.addVertexWithUV(max, min, 0, PingType.BACKGROUND.maxU, PingType.BACKGROUND.minV);
                tessellator.addVertexWithUV(min, min, 0, PingType.BACKGROUND.minU, PingType.BACKGROUND.minV);
                tessellator.draw();

                // Icon
                tessellator.setColorOpaque_F(1, 1, 1);
                tessellator.startDrawingQuads();
                tessellator.addVertexWithUV(min, max, 0, ping.type.minU, ping.type.maxV);
                tessellator.addVertexWithUV(max, max, 0, ping.type.maxU, ping.type.maxV);
                tessellator.addVertexWithUV(max, min, 0, ping.type.maxU, ping.type.minV);
                tessellator.addVertexWithUV(min, min, 0, ping.type.minU, ping.type.minV);
                tessellator.draw();

                tessellator.setTranslation(0, 0, 0);

                GL11.glPopMatrix();
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        Iterator<PingWrapper> iterator = activePings.iterator();
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

    public void renderPing(double px, double py, double pz, Entity renderEntity, PingWrapper ping) {
        GL11.glPushMatrix();

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);

        GL11.glTranslated(px, py, pz);

        GL11.glRotatef(-renderEntity.rotationYaw, 0, 1, 0);
        GL11.glRotatef(renderEntity.rotationPitch, 1, 0, 0);
        GL11.glRotated(180, 0, 0, 1);

        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);

        Tessellator tessellator = Tessellator.instance;

        float min = -0.25F - (0.25F * (float)ping.animationTimer / 20F);
        float max =  0.25F + (0.25F * (float)ping.animationTimer / 20F);

        // Background
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(ping.color);
        tessellator.addVertexWithUV(min, max, 0, PingType.BACKGROUND.minU, PingType.BACKGROUND.maxV);
        tessellator.addVertexWithUV(max, max, 0, PingType.BACKGROUND.maxU, PingType.BACKGROUND.maxV);
        tessellator.addVertexWithUV(max, min, 0, PingType.BACKGROUND.maxU, PingType.BACKGROUND.minV);
        tessellator.addVertexWithUV(min, min, 0, PingType.BACKGROUND.minU, PingType.BACKGROUND.minV);
        tessellator.draw();

        // Icon
        tessellator.setColorOpaque_F(1, 1, 1);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(min, max, 0, ping.type.minU, ping.type.maxV);
        tessellator.addVertexWithUV(max, max, 0, ping.type.maxU, ping.type.maxV);
        tessellator.addVertexWithUV(max, min, 0, ping.type.maxU, ping.type.minV);
        tessellator.addVertexWithUV(min, min, 0, ping.type.minU, ping.type.minV);
        tessellator.draw();

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
    }

    public void renderPingOverlay(double x, double y, double z, PingWrapper ping) {
        Minecraft mc = Minecraft.getMinecraft();
        if (renderBlocks == null || renderBlocks.blockAccess != mc.theWorld) {
            renderBlocks = new RenderBlocks(mc.theWorld);
        }

        float padding = 0F + (0.20F * (float)ping.animationTimer / (float)20);
        float min = -padding;
        float max = 1 + padding;

        GL11.glPushMatrix();

        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);

        GL11.glDisable(GL11.GL_DEPTH_TEST);

        Tessellator tessellator = Tessellator.instance;
        tessellator.setTranslation(x, y, z);

        tessellator.startDrawingQuads();

        tessellator.setColorRGBA_I(ping.color, 122);

        IIcon icon = Blocks.stained_glass.getIcon(0, 0);

        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

        tessellator.setBrightness(Integer.MAX_VALUE);


        // TOP
        tessellator.addVertexWithUV(min, min, max, icon.getMinU(), icon.getMaxV());
        tessellator.addVertexWithUV(max, min, max, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(max, min, min, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(min, min, min, icon.getMinU(), icon.getMinV());

        // BOTTOM
        tessellator.addVertexWithUV(min, max, min, icon.getMinU(), icon.getMinV());
        tessellator.addVertexWithUV(max, max, min, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(max, max, max, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(min, max, max, icon.getMinU(), icon.getMaxV());

        // NORTH
        tessellator.addVertexWithUV(min, max, max, icon.getMinU(), icon.getMaxV());
        tessellator.addVertexWithUV(max, max, max, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(max, min, max, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(min, min, max, icon.getMinU(), icon.getMinV());

        // SOUTH
        tessellator.addVertexWithUV(min, min, min, icon.getMinU(), icon.getMinV());
        tessellator.addVertexWithUV(max, min, min, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(max, max, min, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(min, max, min, icon.getMinU(), icon.getMaxV());

        // EAST
        tessellator.addVertexWithUV(min, max, min, icon.getMinU(), icon.getMaxV());
        tessellator.addVertexWithUV(min, max, max, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(min, min, max, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(min, min, min, icon.getMinU(), icon.getMinV());

        // WEST
        tessellator.addVertexWithUV(max, min, min, icon.getMinU(), icon.getMinV());
        tessellator.addVertexWithUV(max, min, max, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(max, max, max, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV(max, max, min, icon.getMinU(), icon.getMaxV());

        tessellator.draw();

        tessellator.setTranslation(0, 0, 0);

        GL11.glEnable(GL11.GL_DEPTH_TEST);

        GL11.glDisable(GL11.GL_BLEND);

        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
    }

    private void translatePingCoordinates(double px, double py, double pz, PingWrapper ping) {
        FloatBuffer screenCoords = BufferUtils.createFloatBuffer(4);
        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
        FloatBuffer projection = BufferUtils.createFloatBuffer(16);

        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);


        if (GLU.gluProject((float)px, (float)py, (float)pz, modelview, projection, viewport, screenCoords)) {
            ping.screenX = screenCoords.get(0);
            ping.screenY = screenCoords.get(1);
            //TODO Rotation sometimes fucks this up
        }
    }
}
