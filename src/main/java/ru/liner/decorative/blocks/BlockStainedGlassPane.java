package ru.liner.decorative.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemDye;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import ru.liner.decorative.Decorative;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class BlockStainedGlassPane extends BlockColoredPane {
    public BlockStainedGlassPane(int itemId) {
        super(itemId,Material.glass);
        setHardness(0.3f);
        setStepSound(Block.soundGlassFootstep);
        setCreativeTab(CreativeTabs.tabDecorations);
        setUnlocalizedName("thinStainedGlass");
        setTextureName("glass");
        //TODO In inventory and when player holding item of this block its rendered without transparency!!! FIXME!!!
        //TODO In inventory colors for panel are fucked up :(
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
    public int getRenderType() {
        return Decorative.STAINED_GLASS_PANE_RENDER_ID;
    }


    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int blockId = blockAccess.getBlockId(x, y, z);
        int blockMetadata = blockAccess.getBlockMetadata(x, y, z);
        Block block = Block.blocksList[blockId];
        if(block instanceof BlockGlass || block instanceof BlockStainedGlassPane){
            if (blockMetadata != blockAccess.getBlockMetadata(x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side]))
                return true;
            if (block == this)
                return false;
        }
        return super.shouldSideBeRendered(blockAccess, x, y, z, side);
    }

}
