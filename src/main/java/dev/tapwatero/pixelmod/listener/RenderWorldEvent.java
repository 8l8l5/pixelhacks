package dev.tapwatero.pixelmod.listener;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.pixelmonmod.pixelmon.api.registries.PixelmonBlocks;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Comparator;
import java.util.Random;

public class RenderWorldEvent {

    Random random = new Random();

    public int getTileMultiplier(TileEntity tile) {
        int multipler = 2;

        if (tile.getBlockState().getBlock() == PixelmonBlocks.poke_chest) {
            multipler = 10;
        } else if (tile.getBlockState().getBlock() == PixelmonBlocks.ultra_chest) {
            multipler = 3;
        } else if (tile.getBlockState().getBlock() == PixelmonBlocks.master_chest) {
            multipler = 1;
        }
        return multipler;
    }


    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (Minecraft.getInstance().player == null) {
            return;
        }


        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        IVertexBuilder builder = buffer.getBuffer(RenderType.lines());
        MatrixStack stack = event.getMatrixStack();


        stack.pushPose();

        Vector3d cam = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        stack.translate(-cam.x, -cam.y, -cam.z);

        Matrix4f mat = stack.last().pose();


        for (Entity entity : PokemonTickEvent.hits) {
            PixelmonEntity pEntity = (PixelmonEntity) entity;

            int[] colour = new int[]{0, 255, 0, 255};
            if (pEntity.getPokemon().isLegendary()) {
                colour = new int[] {255,215,0, 255};
            }


            builder.vertex(mat, (float) Minecraft.getInstance().player.getX(), (float) ((float)  Minecraft.getInstance().player.getY() + 0.1), (float) Minecraft.getInstance().player.getZ()).color(colour[0], colour[1], colour[2], colour[3]).endVertex();
            builder.vertex(mat, (float) entity.getX(), (float) entity.getY(), (float) entity.getZ()).color(colour[0], colour[1], colour[2], colour[3]).endVertex();
        }


        stack.popPose();
        buffer.endBatch(RenderType.lines());
    }
}








