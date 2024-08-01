package ru.liner.decorative.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import ru.liner.decorative.Decorative;

public class RedstoneLogicBlockRenderer implements ISimpleBlockRenderingHandler {
    @Override
    public void renderInventoryBlock(Block par1Block, int metadata, int modelID, RenderBlocks renderer) {
        metadata = 3;
        Tessellator tessellator = Tessellator.instance;
        par1Block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(par1Block);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(par1Block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(par1Block, 0, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.uvRotateTop = 2;
        renderer.renderFaceYPos(par1Block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(par1Block, 1, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(par1Block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(par1Block, 2, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(par1Block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(par1Block, 3, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(par1Block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(par1Block, 4, metadata));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(par1Block, 0.0, 0.0, 0.0, renderer.getBlockIconFromSideAndMetadata(par1Block, 5, metadata));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
        if (block.getRenderType() == Decorative.REDSTONE_LOGIC_BLOCK_RENDERER)
            return renderBlock(world, x, y, z, block, renderer);
        return false;
    }

    private boolean renderBlock(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer) {
        int blockMetadata = world.getBlockMetadata(x, y, z);
        int rotation = blockMetadata & 3;
        renderer.renderStandardBlock(block, x, y, z);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setColorOpaque_F(1,1,1);
        Icon icon = renderer.getBlockIconFromSideAndMetadata(block, 1, blockMetadata);
        double minU = icon.getMinU();
        double maxU = icon.getMaxU();
        double minV = icon.getMinV();
        double maxV = icon.getMaxV();
        double blockHeight = 1;
        double d5 = x + 1;
        double d6 = x + 1;
        double d7 = x;
        double d8 = x;
        double d9 = z;
        double d10 = z + 1;
        double d11 = z + 1;
        double d12 = z;
        double d13 = (double)y + blockHeight;
        if (rotation == 2) {
            d5 = d6 = x;
            d7 = d8 = x + 1;
            d9 = d12 = z + 1;
            d10 = d11 = z;
        } else if (rotation == 3) {
            d5 = d8 = x;
            d6 = d7 = x + 1;
            d9 = d10 = z;
            d11 = d12 = z + 1;
        } else if (rotation == 1) {
            d5 = d8 = x + 1;
            d6 = d7 = x;
            d9 = d10 = z + 1;
            d11 = d12 = z;
        }
        tessellator.addVertexWithUV(d8, d13, d12, minU, minV);
        tessellator.addVertexWithUV(d7, d13, d11, minU, maxV);
        tessellator.addVertexWithUV(d6, d13, d10, maxU, maxV);
        tessellator.addVertexWithUV(d5, d13, d9, maxU, minV);
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory() {
        return true;
    }

    @Override
    public int getRenderId() {
        return Decorative.REDSTONE_LOGIC_BLOCK_RENDERER;
    }

}