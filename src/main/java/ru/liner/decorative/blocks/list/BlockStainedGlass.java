package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemDye;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;

public class BlockStainedGlass extends BaseMultiMetaBlock {
    public BlockStainedGlass(int blockID) {
        super(blockID, Material.glass);
        setCreativeTab(CreativeTabs.tabDecorations);
        setHardness(0.3f);
        setStepSound(Block.soundGlassFootstep);
        setUnlocalizedName("glass.stained");
        setBaseLocalizedName("Стекло");
        setTextureParent("glass");
        for (int i = 0; i < ItemDye.dyeColorNames.length; i++) {
            registerType(ItemDye.dyeColorNames[i], String.format("%s %s", Decorative.colorNames[i + (ItemDye.dyeColorNames.length * 2)], getBaseLocalizedName().toLowerCase()), 2, ItemDye.dyeColorNames[i]);
        }
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
