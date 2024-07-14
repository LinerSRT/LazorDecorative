package ru.liner.decorative.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;
import ru.liner.decorative.blocks.BaseMultiMetaLadderBlock;
import ru.liner.decorative.blocks.BaseMultiMetaWallBlock;

import java.util.List;

public class BaseMultiMetaWallItem<MetaBlock extends BaseMultiMetaBlock> extends ItemBlock {
    protected BaseMultiMetaWallBlock<MetaBlock> metaBlock;
    public BaseMultiMetaWallItem(MetaBlock block) {
        this(block.blockID, block);
    }
    public BaseMultiMetaWallItem(int blockID, Block block) {
        super(blockID);
        this.metaBlock = (BaseMultiMetaWallBlock<MetaBlock>) block;
        setHasSubtypes(metaBlock.getMetaBlock().hasTypes());
        setMaxDamage(0);
        setUnlocalizedName(String.format("wall.%s", metaBlock.getUnlocalizedName()));
    }



    @Override
    public Icon getIconFromDamage(int metadata) {
        return metaBlock.getIcon(2, metadata);
    }

    @Override
    public void getSubItems(int blockId, CreativeTabs creativeTabs, List itemList) {
        metaBlock.getSubBlocks(blockId, creativeTabs, itemList);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return String.format("tile.wall.%s.name", metaBlock.getMetaBlock().getTypeByMetadata(stack.getItemDamage()));
    }

    @Override
    public String getItemDisplayName(ItemStack itemStack) {
        return getLocalizedName(itemStack);
    }
    @Override
    public int getMetadata(int metadata) {
        return metadata;
    }

    public MetaBlock getMetaBlock() {
        return metaBlock.getMetaBlock();
    }
}
