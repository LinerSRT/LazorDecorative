package ru.liner.decorative.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class MetaBlockHardenedClay extends BaseMetaBlock {
    public MetaBlockHardenedClay(int itemId) {
        super(itemId, Material.rock);
        setHardness(1.25f);
        setResistance(7f);
        setStepSound(Block.soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("clayHardened");
        setTextureName("hardened_clay");
    }
}
