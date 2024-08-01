package ru.liner.decorative.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.list.BlockChorusFlower;
import ru.liner.decorative.utils.Vector3;

public class CutoutBlockRenderer implements ISimpleBlockRenderingHandler {
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
        if (block.getRenderType() == Decorative.CUTOUT_BLOCK_RENDERER && block instanceof ICutoutRenderer)
            return renderBlock(world, x, y, z, block, renderer);
        return false;
    }

    private void applyBounds(EnumFacing facing, RenderBlocks renderer) {
        switch (facing) {
            case DOWN:
                break;
            case UP:
                break;
            case NORTH:
                renderer.setRenderBounds(0.25, 0.25, 0, 0.75, 0.75, 1);
                break;
            case SOUTH:
                break;
            case EAST:
                break;
            case WEST:
                break;
        }
    }

    private boolean renderBlock(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer) {
        Vector3 blockPosition = new Vector3(x, y, z);

        boolean connectedToTop = wasConnected(world,blockPosition, EnumFacing.UP, (ICutoutRenderer) block);
        boolean connectedToBottom = wasConnected(world,blockPosition, EnumFacing.DOWN, (ICutoutRenderer) block);
        double topCutout = connectedToTop ? 0 : ((ICutoutRenderer) block).getCutoutSize(EnumFacing.UP);
        double downCutout = connectedToBottom ? 0 : ((ICutoutRenderer) block).getCutoutSize(EnumFacing.DOWN);
        if(connectedToTop && connectedToBottom)
            topCutout = 0;
        double eastCutout = wasConnected(world,blockPosition, EnumFacing.EAST, (ICutoutRenderer) block) ? 0 : ((ICutoutRenderer) block).getCutoutSize(EnumFacing.EAST);
        double westCutout = wasConnected(world,blockPosition, EnumFacing.WEST, (ICutoutRenderer) block) ? 0 : ((ICutoutRenderer) block).getCutoutSize(EnumFacing.WEST);
        double northCutout = wasConnected(world,blockPosition, EnumFacing.NORTH, (ICutoutRenderer) block) ? 0 : ((ICutoutRenderer) block).getCutoutSize(EnumFacing.NORTH);
        double southCutout = wasConnected(world,blockPosition, EnumFacing.SOUTH, (ICutoutRenderer) block) ? 0 : ((ICutoutRenderer) block).getCutoutSize(EnumFacing.SOUTH);
        ((ICutoutRenderer) block).setBlockBoundsFromRenderer(.3, .3, .3, .7,.7,.7);
        if(block instanceof BlockChorusFlower){
            renderer.setRenderBounds(.1, .1, northCutout, .9, .9, 1 - southCutout);
            renderer.renderStandardBlock(block, x, y, z);
            renderer.setRenderBounds(eastCutout, .1, .1, 1 - westCutout, .9, .9);
            renderer.renderStandardBlock(block, x, y, z);
            renderer.setRenderBounds(.1, downCutout, .1, .9, 1 - topCutout, .9);
            renderer.renderStandardBlock(block, x, y, z);
        } else {
            renderer.setRenderBounds(.3, .3, northCutout, .7, .7, 1 - southCutout);
            renderer.renderStandardBlock(block, x, y, z);
            renderer.setRenderBounds(eastCutout, .3, .3, 1 - westCutout, .7, .7);
            renderer.renderStandardBlock(block, x, y, z);
            renderer.setRenderBounds(.3, downCutout, .3, .7, 1 - topCutout, .7);
            renderer.renderStandardBlock(block, x, y, z);
        }


        return true;
    }

    private boolean wasConnected(IBlockAccess blockAccess, Vector3 position, EnumFacing facing, ICutoutRenderer cutoutRenderer) {
        return cutoutRenderer.allowRenderConnection(position.offset(facing).blockId(blockAccess));
    }

    @Override
    public boolean shouldRender3DInInventory() {
        return false;
    }

    @Override
    public int getRenderId() {
        return Decorative.CUTOUT_BLOCK_RENDERER;
    }


    public interface ICutoutRenderer {
        boolean allowRenderConnection(int blockId);

        void setBlockBoundsFromRenderer(double minX, double minY, double minZ, double maxX, double maxY, double maxZ);

        double getCutoutSize(EnumFacing facing);
    }
}