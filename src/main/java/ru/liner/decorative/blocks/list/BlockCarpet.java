package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemDye;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;

public class BlockCarpet extends BaseMultiMetaBlock {
    public BlockCarpet(int blockID) {
        super(blockID, Material.cloth);
        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
        setTickRandomly(true);
        setCreativeTab(CreativeTabs.tabDecorations);
        setBlockBoundsFromMeta(0);
        setHardness(0.1f);
        setStepSound(Block.soundClothFootstep);
        setLightOpacity(0);
        setUnlocalizedName("carpet");
        setBaseLocalizedName("Коврик");
        setTextureParent("cloth");
        for (int i = 0; i < ItemDye.dyeColorNames.length; i++) {
            registerType(ItemDye.dyeColorNames[i], String.format("%s %s", Decorative.colorNames[i], getBaseLocalizedName().toLowerCase()),  String.valueOf(15 - i));
        }
    }




    @Override
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return getIcon(side, blockAccess.getBlockMetadata(x, y, z));
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + (0 * 0.0625f), z + this.maxZ);
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
    public void setBlockBoundsForItemRender() {
        setBlockBoundsFromMeta(0);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
        setBlockBoundsFromMeta(blockAccess.getBlockMetadata(x, y, z));
    }

    protected void setBlockBoundsFromMeta(int blockMetadata) {
        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1 / 16.0f, 1.0f);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
    }
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int side) {
        checkCanStay(world, x, y, z);
    }

    private void checkCanStay(World world, int x, int y, int z) {
        if (!canBlockStay(world, x, y, z)) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        return !world.isAirBlock(x, y - 1, z);
    }
    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return side == 1 || super.shouldSideBeRendered(blockAccess, x, y, z, side);
    }
}
