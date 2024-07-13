package ru.liner.decorative.blocks.list;

import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class BlockRedSand extends BlockSand {
    public BlockRedSand(int blockID) {
        super(blockID, Material.sand);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("sand.red");
        setHardness(0.5f);
        setStepSound(soundSandFootstep);
    }

    @Override
    public void registerIcons(IconRegister register) {
        blockIcon = register.registerIcon("red_sand");
    }
}
