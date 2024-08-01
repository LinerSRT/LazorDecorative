package ru.liner.decorative.blocks.list;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;
import ru.liner.decorative.items.BaseMultiMetaBannerItem;
import ru.liner.decorative.register.Blocks;
import ru.liner.decorative.register.IItemProvider;
import ru.liner.decorative.tile.TileEntityBanner;

import java.util.ArrayList;
import java.util.Random;

public class BlockBanner extends BaseMultiMetaBlock implements ITileEntityProvider, IItemProvider<BaseMultiMetaBannerItem> {
    public BlockBanner(int itemId) {
        super(itemId, Material.wood);
        setBlockBounds(0.5f - 0.25f, 0.0f, 0.5f - 0.25f, 0.5f + 0.25f, 1.0f, 0.5f + 0.25f);
        setCreativeTab(CreativeTabs.tabDecorations);
        setBaseLocalizedName("Флаг");
        setTextureParent("banner");
        setUnlocalizedName("banner");
        for (int i = 0; i < ItemDye.dyeColorNames.length; i++) {
            registerType(ItemDye.dyeColorNames[i], String.format("%s %s", Decorative.colorNames[i], getBaseLocalizedName().toLowerCase()), "base");
        }
    }


    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
        TileEntity e = blockAccess.getBlockTileEntity(x, y, z);
        if (e instanceof TileEntityBanner) {
            TileEntityBanner banner = (TileEntityBanner) e;
            if (banner.isStandBanner()) {
                setBlockBounds(0.5f - 0.25f, 0.0f, 0.5f - 0.25f, 0.5f + 0.25f, 1.0f, 0.5f + 0.25f);
            } else {
                switch (banner.getRotation()) {
                    case 0:
                        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.78125f, 0.125f);
                        break;
                    case 1:
                        setBlockBounds(1.0f - 0.125f, 0.0f, 0.0f, 1.0f, 0.78125f, 1.0f);
                        break;
                    case 2:
                        setBlockBounds(0.0f, 0.0f, 1.0f - 0.125f, 1.0f, 0.78125f, 1.0f);
                        break;
                    case 3:
                        setBlockBounds(0.0f, 0.0f, 0.0f, 0.125f, 0.78125f, 1.0f);
                        break;
                }
            }
        }
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        return world.getBlockMaterial(x, y - 1, z).isSolid();
    }


    private void checkCanStay(World world, int x, int y, int z) {
        if (!canBlockStay(world, x, y, z)) {
            dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 1);
            world.setBlockToAir(x, y, z);
        }
    }

    public int getRenderType() {
        return -1;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack itemStack) {
        double angle = Math.atan2(entity.posZ - z - 0.5, entity.posX - x - 0.5) * (180 / Math.PI);
        int direction = (MathHelper.floor_double((angle + 22.5) / 45.0)) & 7;
        world.setBlockMetadataWithNotify(x, y, z, direction, 2);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityBanner();
    }


    
    @Override
    public int idPicked(World par1World, int par2, int par3, int par4) {
        return Blocks.banner.blockID;
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return Blocks.banner.blockID;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int side) {
        checkCanStay(world, x, y, z);
    }

    @Override
    public int getDamageValue(World par1World, int par2, int par3, int par4) {
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        return tileentity instanceof TileEntityBanner ? ((TileEntityBanner) tileentity).getColor() : super.getDamageValue(par1World, par2, par3, par4);
    }

    public int damageDropped(int par1) {
        return par1;
    }

    public void onBlockHarvested(World world, int x, int y, int z, int metadata, EntityPlayer player) {
        if (player.capabilities.isCreativeMode) {
            metadata |= 8;
            world.setBlockMetadataWithNotify(x, y, z, metadata, 4);
        }
        this.dropBlockAsItem(world, x, y, z, metadata, 0);
        super.onBlockHarvested(world, x, y, z, metadata, player);
    }



    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        TileEntity e = world.getBlockTileEntity(x, y, z);
        ItemStack droppedStack = new ItemStack(Blocks.banner.blockID, 1, getDamageValue(world, x, y, z));
        if(e instanceof TileEntityBanner){
            TileEntityBanner banner = (TileEntityBanner) e;
            banner.writeToStack(droppedStack);
        }
        drops.add(droppedStack);
        return drops;
    }

    @Override
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5) {
        ForgeDirection dir = ForgeDirection.getOrientation(par5);
        return (dir == ForgeDirection.DOWN && par1World.isBlockSolidOnSide(par2, par3 + 1, par4, ForgeDirection.DOWN)) || (dir == ForgeDirection.UP && par1World.isBlockSolidOnSide(par2, par3 - 1, par4, ForgeDirection.UP)) || ((dir == ForgeDirection.NORTH && par1World.isBlockSolidOnSide(par2, par3, par4 + 1, ForgeDirection.NORTH)) || ((dir == ForgeDirection.SOUTH && par1World.isBlockSolidOnSide(par2, par3, par4 - 1, ForgeDirection.SOUTH)) || ((dir == ForgeDirection.WEST && par1World.isBlockSolidOnSide(par2 + 1, par3, par4, ForgeDirection.WEST)) || (dir == ForgeDirection.EAST && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, ForgeDirection.EAST)))));
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return par1World.isBlockSolidOnSide(par2 - 1, par3, par4, ForgeDirection.EAST) || par1World.isBlockSolidOnSide(par2 + 1, par3, par4, ForgeDirection.WEST) || par1World.isBlockSolidOnSide(par2, par3, par4 - 1, ForgeDirection.SOUTH) || par1World.isBlockSolidOnSide(par2, par3, par4 + 1, ForgeDirection.NORTH) || par1World.isBlockSolidOnSide(par2, par3 - 1, par4, ForgeDirection.UP) || par1World.isBlockSolidOnSide(par2, par3 + 1, par4, ForgeDirection.DOWN);
    }


    @Override
    public Class<BaseMultiMetaBannerItem> getItemClass() {
        return BaseMultiMetaBannerItem.class;
    }
}
