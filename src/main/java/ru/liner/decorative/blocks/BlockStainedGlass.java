package ru.liner.decorative.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

public class BlockStainedGlass extends BlockColored {
    public BlockStainedGlass(int itemId) {
        super(itemId,Material.glass);
        setHardness(0.3f);
        setStepSound(Block.soundGlassFootstep);
        setCreativeTab(CreativeTabs.tabDecorations);
        setUnlocalizedName("stainedGlass");
        setTextureName("glass");
        //TODO In inventory and when player holding item of this block its rendered without transparency!!! FIXME!!!
    }


    @Override
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        blockIcon = register.registerIcon(textureName);
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int blockId = blockAccess.getBlockId(x, y, z);
        int blockMetadata = blockAccess.getBlockMetadata(x, y, z);
        Block block = Block.blocksList[blockId];
        if(block instanceof BlockGlass || block instanceof BlockStainedGlass){
            if (blockMetadata != blockAccess.getBlockMetadata(x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]))
                return true;
            if (block == this)
                return false;
        }
        return super.shouldSideBeRendered(blockAccess, x, y, z, side);
    }

}
