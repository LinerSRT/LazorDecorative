package ru.liner.decorative.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.IPlantable;
import ru.liner.decorative.Decorative;

public class BlockSapling extends BaseMultiBlockFlower implements IPlantable {
    public BlockSapling(int blockId) {
        super(blockId, Material.wood);
        registerType("acacia", "Сажанец акации");
        registerType("big_oak", "Сажанец большого дуба");
        setCreativeTab(CreativeTabs.tabDecorations);
        setHardness(0);
        setStepSound(Block.soundGrassFootstep);
        setTextureName("sapling");
        setUnlocalizedName("sapling");
        float f = 0.4F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        setTickRandomly(true);
    }
    @Override
    public boolean canThisPlantGrowOnThisBlockID(int par1) {
        return par1 == Block.grass.blockID || par1 == Block.dirt.blockID || par1 == Block.tilledField.blockID || par1 == Blocks.podzol.blockID;
    }
}
