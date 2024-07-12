package ru.liner.decorative.blocks.list;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;

public class BlockStoneNew extends BaseMultiMetaBlock {
    public BlockStoneNew(int blockID) {
        super(blockID, Material.rock);
        setCreativeTab(CreativeTabs.tabBlock);
        setStepSound(BlockStoneNew.soundStoneFootstep);
        setBaseLocalizedName("Камень");
        setTextureParent("stone");
        setUnlocalizedName("stone");
        registerType("granite", "Гранит", 2, "granite");
        registerType("diorite", "Диорит", 2, "diorite");
        registerType("andesite", "Андезит", 2, "andesite");
        registerType("granite_smooth", "Гладкий гранит", 2, "granite_smooth");
        registerType("diorite_smooth", "Гладкий диорит", 2, "diorite_smooth");
        registerType("andesite_smooth", "Гладкий андезит", 2, "andesite_smooth");
    }
}
