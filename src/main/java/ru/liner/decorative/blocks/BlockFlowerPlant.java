package ru.liner.decorative.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import ru.liner.decorative.blocks.list.BlockDirtPodzol;
import ru.liner.decorative.register.Registry;

import java.util.Random;

public class BlockFlowerPlant extends BaseMultiBlockFlower {

    public BlockFlowerPlant(int blockId) {
        super(blockId, Material.plants);
        setTickRandomly(true);
        setHardness(0.0f);
        setCreativeTab(CreativeTabs.tabDecorations);
        setStepSound(Block.soundGrassFootstep);
        setTextureName("flower");
        registerType("allium", "Лук");
        registerType("blue_orchid", "Синяя орхидея");
        registerType("houstonia", "Голубой василек");
        registerType("oxeye_daisy", "Ромашка");
        registerType("paeonia", "Пион");
        registerType("tulip_orange", "Оранжевый тюльпан");
        registerType("tulip_pink", "Розовый тюльпан");
        registerType("tulip_red", "Красный тюльпан");
        registerType("tulip_white", "Белый тюльпан");
        setUnlocalizedName("flower");
    }

    @Override
    public String getLocalization() {
        return "Цветок";
    }


    @Override
    public int getRenderType() {
        return 1;
    }

    @Override
    public EnumPlantType getPlantType(World world, int x, int y, int z) {
        if (blockID == Registry.getInstance().block(BlockDoublePlant.class).blockID)
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
        return Registry.getInstance().block(BlockFlowerPlant.class).blockID;
    }

    @Override
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
        if (!par1World.isRemote) {
            dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(idDropped(par5, par1World.rand, par7), 1, damageDropped(par5)));
        }
    }

    @Override
    protected boolean canThisPlantGrowOnThisBlockID(int par1) {
        return super.canThisPlantGrowOnThisBlockID(par1) || Registry.getInstance().block(BlockDirtPodzol.class).blockID == par1;
    }

    @Override
    public int damageDropped(int metadata) {
        return getMetadataExcludeType(metadata);
    }

    @Override
    public int getDamageValue(World world, int i, int i2, int i3) {
        return getMetadataExcludeType(world.getBlockMetadata(i, i2, i3));
    }
}
