package ru.liner.decorative.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;

public class BlockHayBale extends BlockRotatedPillar {
    public BlockHayBale(int blockId) {
        super(blockId, Material.grass);
        setCreativeTab(CreativeTabs.tabBlock);
        setHardness(0.5f);
        setStepSound(Block.soundGrassFootstep);
        setUnlocalizedName("hayBlock");
    }

    @Override
    public Icon getSideIcon(int side, int metadata) {
        return blockIcon;
    }

    @Override
    public void registerIcons(IconRegister register) {
        topBottomIcon = register.registerIcon("hay_block_top");
        blockIcon = register.registerIcon("hay_block_side");
    }
}
