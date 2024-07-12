package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.liner.decorative.blocks.BaseMetaBlock;

public class BlockCoal extends BaseMetaBlock {
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
