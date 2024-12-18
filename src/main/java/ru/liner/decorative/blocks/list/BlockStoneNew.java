package ru.liner.decorative.blocks.list;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;
import ru.liner.decorative.blocks.FamilarityType;
import ru.liner.decorative.blocks.IBlockFamily;

public class BlockStoneNew extends BaseMultiMetaBlock implements IBlockFamily {
    public BlockStoneNew(int blockID) {
        super(blockID, Material.rock);
        setCreativeTab(CreativeTabs.tabBlock);
        setStepSound(BlockStoneNew.soundStoneFootstep);
        setBaseLocalizedName("Камень");
        setTextureParent("stone");
        setUnlocalizedName("stone");
        registerType("granite", "Гранит", "granite");
        registerType("diorite", "Диорит",  "diorite");
        registerType("andesite", "Андезит",  "andesite");
        registerType("granite_smooth", "Гладкий гранит",  "granite_smooth");
        registerType("diorite_smooth", "Гладкий диорит",  "diorite_smooth");
        registerType("andesite_smooth", "Гладкий андезит",  "andesite_smooth");
    }

    @Override
    public FamilarityType[] getFamiliarityWith() {
        return new FamilarityType[]{
                FamilarityType.STAIR,
                FamilarityType.SLAB
        };
    }
}
