package ru.liner.decorative.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

@SuppressWarnings("unchecked")
public class BlockCarpet extends Block {
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

    public BlockCarpet(int blockId) {
        super(blockId, Material.cloth);
        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
        setTickRandomly(true);
        setCreativeTab(CreativeTabs.tabDecorations);
        setBlockBoundsFromMeta(0);
        setHardness(0.1f);
        setStepSound(Block.soundClothFootstep);
        setLightOpacity(0);
        setUnlocalizedName("woolCarpet");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int metadata) {
        return iconArray[metadata % iconArray.length];
    }


    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.iconArray = new Icon[16];
        for (int i = 0; i < this.iconArray.length; ++i) {
            this.iconArray[i] = iconRegister.registerIcon("cloth_" + i);
        }
    }

    @Override
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        return getIcon(par5, par1IBlockAccess.getBlockMetadata(par2, par3, par4));
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

    @Override
    public int damageDropped(int i) {
        return i;
    }

    @Override
    public void getSubBlocks(int itemId, CreativeTabs creativeTabs, List itemList) {
        for (int i = 0; i < 16; i++)
            itemList.add(new ItemStack(itemId, 1, i));
    }


    public static int getBlockFromDye(int i) {
        return (~i) & 15;
    }

    public static int getDyeFromBlock(int i) {
        return (~i) & 15;
    }

}
