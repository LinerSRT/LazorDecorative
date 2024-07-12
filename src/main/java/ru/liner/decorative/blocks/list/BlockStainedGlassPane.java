package ru.liner.decorative.blocks.list;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemDye;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;

public class BlockStainedGlassPane extends BaseMultiMetaBlock {
    public BlockStainedGlassPane(int blockID) {
        super(blockID, Material.glass);
        setCreativeTab(CreativeTabs.tabDecorations);
        setHardness(0.3f);
        setStepSound(Block.soundGlassFootstep);
        setUnlocalizedName("glass_pane.stained");
        setBaseLocalizedName("Стеклянная панель");
        setTextureParent("glass");
        for (int i = 0; i < ItemDye.dyeColorNames.length; i++) {
            registerType(ItemDye.dyeColorNames[i], String.format("%s %s", Decorative.colorNames[i + ItemDye.dyeColorNames.length ], getBaseLocalizedName().toLowerCase()), 2, ItemDye.dyeColorNames[i]);
            registerTexture(ItemDye.dyeColorNames[i], 2, String.format("pane_top_%s", ItemDye.dyeColorNames[i]));
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

    @Override
    public Icon getIcon(int side, int metadata) {
        return super.getIcon(side, metadata);
    }

    public Icon getSideIcon(int side, int metadata) {
        String unlocalizedType = getTypeByMetadata(metadata);
        TextureData textureData = textureDataMap.get(unlocalizedType).get(1);
        String textureName = String.format("%s_%s", textureParent, textureData.textureName);
        //TODO should i need check side here?
        return textureMap.get(textureName);
    }

    public final boolean canThisPaneConnectToThisBlockID(int par1) {
        return Block.opaqueCubeLookup[par1] || par1 == this.blockID || par1 == Block.glass.blockID;
    }

    public boolean canPaneConnectTo(IBlockAccess blockAccess, int x, int y, int z, ForgeDirection direction) {
        return canThisPaneConnectToThisBlockID(blockAccess.getBlockId(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ)) || blockAccess.isBlockSolidOnSide(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ, direction.getOpposite(), false);
    }
}
