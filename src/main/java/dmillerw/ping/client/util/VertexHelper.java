package dmillerw.ping.client.util;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VertexHelper {

    public static void renderPosTexColor(IVertexBuilder builder, Matrix4f matrix4f, Matrix3f matrix3f, float x, float y, float z, float u, float v, float r, float g, float b, float a) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();
        mutablePos.setPos(x, y, z);
        World world = Minecraft.getInstance().world;
        if (world != null) {
            int lightMap = WorldRenderer.getCombinedLight(world, mutablePos);
            builder.pos(matrix4f, x, y, z).color(r, g, b, a).tex(u, v).lightmap(lightMap).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        }
    }

    public static void renderPosTexColor(IVertexBuilder builder, Matrix4f matrix4f, Matrix3f matrix3f, float x, float y, float u, float v, float r, float g, float b, float a) {
        builder.pos(matrix4f, x, y, 0.0F).color(r, g, b, a).tex(u, v).overlay(OverlayTexture.DEFAULT_LIGHT).lightmap(1).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    public static void renderPosTexColor(IVertexBuilder builder, Matrix4f matrix4f, Matrix3f matrix3f, float x, float y, float u, float v, int r, int g, int b, int a) {
        builder.pos(matrix4f, x, y, 0.0F).color(r, g, b, a).tex(u, v).overlay(OverlayTexture.DEFAULT_LIGHT).lightmap(1).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }
}