package ru.liner.decorative.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockStainedHardenedClay extends BlockColored {
    public BlockStainedHardenedClay(int itemId) {
        super(itemId);
        setHardness(1.25f);
        setResistance(7f);
        setStepSound(Block.soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("clayHardenedStained");
        setTextureName("hardened_clay_stained");
    }
}
