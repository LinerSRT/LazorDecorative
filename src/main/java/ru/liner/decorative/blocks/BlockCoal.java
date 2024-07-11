package ru.liner.decorative.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockCoal extends BaseBlock {
    public BlockCoal(int itemId) {
        super(itemId, Material.rock);
        setHardness(5f);
        setResistance(10f);
        setStepSound(Block.soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("blockCoal");
        setTextureName("coal_block");
    }
}
