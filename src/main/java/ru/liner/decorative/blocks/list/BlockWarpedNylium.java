package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;

public class BlockWarpedNylium extends BaseMultiMetaBlock {
    public BlockWarpedNylium(int blockId) {
        super(blockId, Material.ground);
        setTickRandomly(true);
        setStepSound(Block.soundGrassFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setBaseLocalizedName("Искажённый нилий");
        setUnlocalizedName("warpedNylium");
        setTextureParent("warped");
        registerType("nylium", "Искажённый нилий", "nylium_side");
        registerTexture("nylium", "nylium", 1);
    }
}
