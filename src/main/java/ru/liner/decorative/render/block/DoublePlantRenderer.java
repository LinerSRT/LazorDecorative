package ru.liner.decorative.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.BlockDoublePlant;

public class DoublePlantRenderer implements ISimpleBlockRenderingHandler {
    Tessellator tessellator;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {

    }

    @Override // cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
        if (block instanceof BlockDoublePlant)
            //return renderer.renderCrossedSquares(block, x, y, z);
            return renderBlockDoublePlant(world, renderer, (BlockDoublePlant) block, x, y, z);
        return false;
    }


    private boolean renderBlockDoublePlant(IBlockAccess world, RenderBlocks renderer, BlockDoublePlant block, int x, int y, int z) {
        int metadata;
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        int colorMultiplier = block.colorMultiplier(world, x, y, z);
        float f = ((colorMultiplier >> 16) & 255) / 255.0f;
        float f2 = ((colorMultiplier >> 8) & 255) / 255.0f;
        float f3 = (colorMultiplier & 255) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float f4 = ((f * 30.0f) + (f2 * 70.0f)) / 100.0f;
            float f5 = ((f * 30.0f) + (f3 * 70.0f)) / 100.0f;
            f = (((f * 30.0f) + (f2 * 59.0f)) + (f3 * 11.0f)) / 100.0f;
            f2 = f4;
            f3 = f5;
        }
        tessellator.setColorOpaque_F(f, f2, f3);
        long j = (x * 3129871L) ^ (z * 116129781L);
        long j2 = (j * j * 42317861) + (j * 11);
        double d = y;
        double d2 = x + (((((float) ((j2 >> 16) & 15)) / 15.0f) - 0.5d) * 0.3d);
        double d3 = z + (((((float) ((j2 >> 24) & 15)) / 15.0f) - 0.5d) * 0.3d);
        int blockMetadata = world.getBlockMetadata(x, y, z);
        boolean isTopIcon = block.isTopIcon(blockMetadata);
        if (isTopIcon) {
            if (world.getBlockId(x, y - 1, z) != block.blockID)
                return false;
            metadata = block.getMetadataExcludeType(world.getBlockMetadata(x, y - 1, z));
        } else {
            metadata = block.getMetadataExcludeType(blockMetadata);
        }
        drawCrossedSquares(renderer, block.getIcon(isTopIcon, metadata), d2, d, d3, 1.0f);
        if (isTopIcon && metadata == 5) {
            Icon iIcon = block.sunflowerIcons[0];
            double cos = Math.cos(j2 * 0.8d) * 3.141592653589793d * 0.1d;
            double cos2 = Math.cos(cos);
            double sin = Math.sin(cos);
            double minU = iIcon.getMinU();
            double minV = iIcon.getMinV();
            double maxU = iIcon.getMaxU();
            double maxV = iIcon.getMaxV();
            double d4 = (0.5d + (0.3d * cos2)) - (0.5d * sin);
            double d5 = 0.5d + (0.5d * cos2) + (0.3d * sin);
            double d6 = 0.5d + (0.3d * cos2) + (0.5d * sin);
            double d7 = 0.5d + ((-0.5d) * cos2) + (0.3d * sin);
            double d8 = 0.5d + ((-0.05d) * cos2) + (0.5d * sin);
            double d9 = 0.5d + ((-0.5d) * cos2) + ((-0.05d) * sin);
            double d10 = (0.5d + ((-0.05d) * cos2)) - (0.5d * sin);
            double d11 = 0.5d + (0.5d * cos2) + ((-0.05d) * sin);
            tessellator.addVertexWithUV(d2 + d8, d + 1.0d, d3 + d9, minU, maxV);
            tessellator.addVertexWithUV(d2 + d10, d + 1.0d, d3 + d11, maxU, maxV);
            tessellator.addVertexWithUV(d2 + d4, d + 0.0d, d3 + d5, maxU, minV);
            tessellator.addVertexWithUV(d2 + d6, d + 0.0d, d3 + d7, minU, minV);
            Icon iIcon2 = block.sunflowerIcons[1];
            double minU2 = iIcon2.getMinU();
            double minV2 = iIcon2.getMinV();
            double maxU2 = iIcon2.getMaxU();
            double maxV2 = iIcon2.getMaxV();
            tessellator.addVertexWithUV(d2 + d10, d + 1.0d, d3 + d11, minU2, maxV2);
            tessellator.addVertexWithUV(d2 + d8, d + 1.0d, d3 + d9, maxU2, maxV2);
            tessellator.addVertexWithUV(d2 + d6, d + 0.0d, d3 + d7, maxU2, minV2);
            tessellator.addVertexWithUV(d2 + d4, d + 0.0d, d3 + d5, minU2, minV2);
            return true;
        }
        return true;
    }

    public void drawCrossedSquares(RenderBlocks renderer,Icon iIcon, double d, double d2, double d3, float f) {
        Tessellator tessellator = Tessellator.instance;
        if (renderer.hasOverrideBlockTexture()) {
            iIcon = renderer.overrideBlockTexture;
        }
        double minU = iIcon.getMinU();
        double minV = iIcon.getMinV();
        double maxU = iIcon.getMaxU();
        double maxV = iIcon.getMaxV();
        double d4 = 0.45d * f;
        double d5 = (d + 0.5d) - d4;
        double d6 = d + 0.5d + d4;
        double d7 = (d3 + 0.5d) - d4;
        double d8 = d3 + 0.5d + d4;
        tessellator.addVertexWithUV(d5, d2 + f, d7, minU, minV);
        tessellator.addVertexWithUV(d5, d2 + 0.0d, d7, minU, maxV);
        tessellator.addVertexWithUV(d6, d2 + 0.0d, d8, maxU, maxV);
        tessellator.addVertexWithUV(d6, d2 + f, d8, maxU, minV);
        tessellator.addVertexWithUV(d6, d2 + f, d8, minU, minV);
        tessellator.addVertexWithUV(d6, d2 + 0.0d, d8, minU, maxV);
        tessellator.addVertexWithUV(d5, d2 + 0.0d, d7, maxU, maxV);
        tessellator.addVertexWithUV(d5, d2 + f, d7, maxU, minV);
        tessellator.addVertexWithUV(d5, d2 + f, d8, minU, minV);
        tessellator.addVertexWithUV(d5, d2 + 0.0d, d8, minU, maxV);
        tessellator.addVertexWithUV(d6, d2 + 0.0d, d7, maxU, maxV);
        tessellator.addVertexWithUV(d6, d2 + f, d7, maxU, minV);
        tessellator.addVertexWithUV(d6, d2 + f, d7, minU, minV);
        tessellator.addVertexWithUV(d6, d2 + 0.0d, d7, minU, maxV);
        tessellator.addVertexWithUV(d5, d2 + 0.0d, d8, maxU, maxV);
        tessellator.addVertexWithUV(d5, d2 + f, d8, maxU, minV);
    }


    @Override // cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
    public boolean shouldRender3DInInventory() {
        return false;
    }

    @Override // cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
    public int getRenderId() {
        return Decorative.DOUBLE_PLANT_RENDERER;
    }
}