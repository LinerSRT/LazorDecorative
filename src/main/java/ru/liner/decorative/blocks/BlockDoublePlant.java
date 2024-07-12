package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import ru.liner.decorative.Decorative;

import java.util.Random;

public class BlockDoublePlant extends BaseMultiBlockFlower {
    public Icon[] sunflowerIcons;

    public BlockDoublePlant(int blockId) {
        super(blockId, Material.plants);
        setTickRandomly(true);
        setHardness(0.0f);
        setCreativeTab(CreativeTabs.tabDecorations);
        setStepSound(Block.soundGrassFootstep);
        setBlockBounds(0.5f - 0.2f, 0.0f, 0.5f - 0.2f, 0.5f + 0.2f, 0.2f * 3.0f, 0.5f + 0.2f);
        setTextureName("double_plant");
        registerType("sunflower", "Подсолнух");
        registerType("syrinda", "Сирень");
        registerType("grass", "Двойная высокая трава");
        registerType("fern", "Раскидистый папоротник");
        registerType("rose", "Розовый куст");
        registerType("paeonia", "Пион");
        setUnlocalizedName("doublePlant");
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        if (isTopIcon(metadata))
            return topBottomIcons[getMetadataExcludeType(metadata) % types.size()];
        return iconTypes[getMetadataExcludeType(metadata) % types.size()];
    }

    @Override
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        sunflowerIcons = new Icon[2];
        sunflowerIcons[0] = register.registerIcon("double_plant_sunflower_front");
        sunflowerIcons[1] = register.registerIcon("double_plant_sunflower_back");
    }

    @Override
    public int getRenderType() {
        return Decorative.DOUBLE_PLANT_RENDERER;
    }

    @Override
    public EnumPlantType getPlantType(World world, int x, int y, int z) {
        if (blockID == Decorative.doublePlantBlock.blockID)
            return EnumPlantType.Plains;
        return super.getPlantType(world, x, y, z);
    }

    @Override
    public int getPlantID(World world, int x, int y, int z) {
        return blockID;
    }

    @Override
    public int getPlantMetadata(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return Decorative.doublePlantBlock.blockID;
    }

    @Override
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
        if (!par1World.isRemote) {
            dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(idDropped(par5, par1World.rand, par7), 1, damageDropped(par5)));
        }
    }


    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        double d0 = 0.5;
        double d1 = 1.0;
        return ColorizerGrass.getGrassColor(d0, d1);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(int metadata) {
        switch (getMetadataExcludeType(metadata)) {
            case 1:
            case 2:
                return ColorizerFoliage.getFoliageColorBasic();
            default:
                return super.getRenderColor(metadata);
        }
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {

        switch (getMetadataExcludeType(par1IBlockAccess.getBlockMetadata(par2, par3, par4))) {
            case 1:
            case 2:
                return par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeGrassColor();
            default:
                return super.colorMultiplier(par1IBlockAccess, par2, par3, par4);
        }


    }

    public boolean isTopIcon(int i) {
        return (i & 8) != 0;
    }


    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, int i, int i2, int i3) {
        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }


    public int getBottomMetadata(IBlockAccess iBlockAccess, int i, int i2, int i3) {
        int blockMetadata = iBlockAccess.getBlockMetadata(i, i2, i3);
        if (!isTopIcon(blockMetadata))
            return blockMetadata & 7;
        return iBlockAccess.getBlockMetadata(i, i2 - 1, i3) & 7;
    }


    @Override
    protected boolean canThisPlantGrowOnThisBlockID(int par1) {
        return super.canThisPlantGrowOnThisBlockID(par1) || par1 == blockID || Decorative.podzolBlock.blockID == par1;
    }


    @Override
    public int damageDropped(int metadata) {
        return getMetadataExcludeType(metadata);
    }

    public Icon getIcon(boolean top, int metadata) {
        if (top)
            return this.topBottomIcons[getMetadataExcludeType(metadata) % types.size()];
        return this.iconTypes[getMetadataExcludeType(metadata) % types.size()];
    }


    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        world.setBlock(x, y, z, blockID, metadata, 4);
        world.setBlock(x, y + 1, z, blockID, metadata | 8, 4);
        world.markBlockForRenderUpdate(x, y, z);
        world.markBlockForRenderUpdate(x, y + 1, z);
        return metadata;
    }


    @Override
    public void onBlockPlacedBy(World world, int i, int i2, int i3, EntityLiving entityLivingBase, ItemStack itemStack) {
        world.setBlock(i, i2 + 1, i3, blockID, 8 | (((MathHelper.floor_double(((entityLivingBase.rotationYaw * 4.0f) / 360.0f) + 0.5d) & 3) + 2) % 4), 4);
    }


    @Override // net.minecraft.block.Block
    /* renamed from: k */
    public int getDamageValue(World world, int i, int i2, int i3) {
        int func_72805_g = world.getBlockMetadata(i, i2, i3);
        if (isTopIcon(func_72805_g)) {
            return getMetadataExcludeType(world.getBlockMetadata(i, i2 - 1, i3));
        }
        return getMetadataExcludeType(func_72805_g);
    }


    private boolean dropItem(World world, int i, int i2, int i3, int i4, EntityPlayer entityPlayer) {
        int metadataExcludeType = getMetadataExcludeType(i4);
        if (metadataExcludeType == 3 || metadataExcludeType == 2) {
            entityPlayer.addStat(StatList.mineBlockStatArray[blockID], 1);
            int i5 = 1;
            if (metadataExcludeType == 3) {
                i5 = 2;
            }
            dropBlockAsItem(world, i, i2, i3, getPlantID(world, i, i2, i3), getPlantMetadata(world, i, i2, i3));
            return true;
        }
        return false;
    }

    @Override
    public void onBlockHarvested(World world, int i, int i2, int i3, int par5, EntityPlayer entityPlayer) {
        if (isTopIcon(par5)) {
            if (world.getBlockId(i, i2 - 1, i3) == blockID) {
                if (!entityPlayer.capabilities.isCreativeMode) {
                    int func_72805_g = world.getBlockMetadata(i, i2 - 1, i3);
                    int metadataExcludeType = getMetadataExcludeType(func_72805_g);
                    if (metadataExcludeType == 3 || metadataExcludeType == 2) {
                        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Item.shears) {
                            dropItem(world, i, i2, i3, func_72805_g, entityPlayer);
                        }
                        world.setBlockToAir(i, i2 - 1, i3);
                    } else {
                        world.destroyBlock(i, i2 - 1, i3, true);
                    }
                } else {
                    world.setBlockToAir(i, i2 - 1, i3);
                }
            }
        } else if (entityPlayer.capabilities.isCreativeMode && world.getBlockId(i, i2 + 1, i3) == blockID) {
            world.setBlockToAir(i, i2 + 1, i3);
        }
        super.onBlockHarvested(world, i, i2, i3, par5, entityPlayer);
    }
}
