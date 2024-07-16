package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.liner.decorative.blocks.BaseMetaBlock;

public class BlockNetherRack extends BaseMetaBlock {
    public BlockNetherRack(int itemId) {
        super(itemId, Material.rock);
        setHardness(.4f);
        setStepSound(Block.soundStoneFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("netherrack");
        setTextureName("netherrack");
        setBaseLocalizedName("Незерак");
    }
}
