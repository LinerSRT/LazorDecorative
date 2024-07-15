package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.BaseMetaBlock;
import ru.liner.decorative.blocks.Blocks;
import ru.liner.decorative.items.Items;
import ru.liner.decorative.render.block.CutoutBlockRenderer;
import ru.liner.decorative.utils.Vector3;

import java.util.List;
import java.util.Random;


public class BlockChorusPlant extends BaseMetaBlock implements CutoutBlockRenderer.ICutoutRenderer {
    private static final float BLOCK_SIZE = .36f;
    public BlockChorusPlant(int itemId) {
        super(itemId, Material.plants);
        setCreativeTab(CreativeTabs.tabDecorations);
        setHardness(0.4f);
        setBlockBounds(BLOCK_SIZE, 0f, BLOCK_SIZE, BLOCK_SIZE, 1 - BLOCK_SIZE, BLOCK_SIZE);
        setStepSound(soundWoodFootstep);
        setUnlocalizedName("flower.plant");
        setTextureName("chorus_plant");
        setBaseLocalizedName("Ветвь хоруса");
    }
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x + 0.1875, y + 0.1875, z + 0.1875, x + 0.8125, y + 0.8125, z + 0.8125);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
        setBlockBounds(0.1875F, 0.1875F, 0.1875F, 0.8125F, 0.8125F, 0.8125F);
        super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);

        if (isConnectedTo(world, x, y, z, -1, 0, 0)) {
            setBlockBounds(0.0F, 0.1875F, 0.1875F, 0.1875F, 0.8125F, 0.8125F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
        }

        if (isConnectedTo(world, x, y, z, 1, 0, 0)) {
            setBlockBounds(0.8125F, 0.1875F, 0.1875F, 1.0F, 0.8125F, 0.8125F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
        }

        if (isConnectedTo(world, x, y, z, 0, 1, 0)) {
            setBlockBounds(0.1875F, 0.8125F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
        }

        if (isConnectedTo(world, x, y, z, 0, -1, 0)) {
            setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.1875F, 0.8125F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
        }

        if (isConnectedTo(world, x, y, z, 0, 0, -1)) {
            setBlockBounds(0.1875F, 0.1875F, 0.0F, 0.8125F, 0.8125F, 0.1875F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
        }

        if (isConnectedTo(world, x, y, z, 0, 0, 1)) {
            setBlockBounds(0.1875F, 0.1875F, 0.8125F, 0.8125F, 0.8125F, 1.0F);
            super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
        }

        setBlockBounds(0.1875F, 0.1875F, 0.1875F, 0.8125F, 0.8125F, 0.8125F);
    }

    private boolean isConnectedTo(World world, int x, int y, int z, int offsetX, int offsetY, int offsetZ) {
        int blockID = world.getBlockId(x + offsetX, y + offsetY, z + offsetZ);
        return blockID == this.blockID || blockID == Blocks.chorusFlower.blockID || (offsetY == -1 && blockID == Block.whiteStone.blockID);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (!canStay(world, x, y, z)) {
            world.setBlockToAir(x, y, z);
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        }
    }

    private boolean canStay(World world, int x, int y, int z) {
        boolean isAirAbove = world.isAirBlock(x, y + 1, z);
        boolean isAirBelow = world.isAirBlock(x, y - 1, z);
        if(isAirBelow)
            return false;
        return world.getBlockId(x, y - 1, z) == this.blockID || world.getBlockId(x, y - 1, z) == Block.whiteStone.blockID;
    }

    @Override
    public int idDropped(int metadata, Random random, int fortune) {
        return Items.chorusFruit.itemID;
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(2);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        if (super.canPlaceBlockAt(world, x, y, z)) {
            return canStay(world, x, y, z);
        }
        return false;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborID) {
        if (!canStay(world, x, y, z)) {
            world.scheduleBlockUpdate(x,y,z, blockID, 1);
        }
    }




    @Override
    public int getRenderType() {
        return Decorative.CUTOUT_BLOCK_RENDERER;
    }

    @Override
    public boolean allowRenderConnection(int blockId) {
        return blockId == Blocks.chorusFlower.blockID || blockId == blockID || blockId == Block.whiteStone.blockID;
    }

    @Override
    public void setBlockBoundsFromRenderer(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {

    }

    @Override
    public double getCutoutSize(EnumFacing facing) {
        return 0.1875D;
    }
}
