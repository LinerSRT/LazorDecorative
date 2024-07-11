package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;

public class BlockDirtPodzol extends BaseBlock {
    @SideOnly(Side.CLIENT)
    protected Icon topIcon;
    public BlockDirtPodzol(int itemId) {
        super(itemId, Material.ground);
        setTickRandomly(true);
        setStepSound(Block.soundGrassFootstep);
        setCreativeTab(CreativeTabs.tabBlock);
        setHardness(5f);
        setResistance(10f);
        setUnlocalizedName("blockDirtPodzol");
        setTextureName("dirt_podzol_side");
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        if(side == 1)
            return topIcon;
        return blockIcon;
    }

    @Override
    public void registerIcons(IconRegister register) {
        blockIcon = register.registerIcon(textureName);
        topIcon = register.registerIcon("dirt_podzol_top");
    }
}
