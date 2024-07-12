package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import ru.liner.decorative.blocks.BaseMetaRotatableBlock;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;
import ru.liner.decorative.blocks.Blocks;

import java.util.Random;

public class BlockHayBale extends BaseMetaRotatableBlock {
    public BlockHayBale(int blockID) {
        super(blockID, Material.grass);
        setCreativeTab(CreativeTabs.tabBlock);
        setStepSound(BlockHayBale.soundGrassFootstep);
        setBaseLocalizedName("Сноп сена");
        setUnlocalizedName("hayBlock");
        setHardness(0.5f);
        setSideTexture("hay_block_side");
        setTopTexture("hay_block_top");
    }
}
