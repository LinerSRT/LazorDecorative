package ru.liner.decorative.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import ru.liner.decorative.blocks.BaseMultiMetaBlock;
import ru.liner.decorative.blocks.BaseMultiMetaFenceBlock;
import ru.liner.decorative.blocks.BaseMultiMetaLadderBlock;

import java.util.List;

public class BaseMultiMetaLadderItem<MetaBlock extends BaseMultiMetaBlock> extends ItemBlock {
    protected BaseMultiMetaLadderBlock<MetaBlock> metaBlock;
    public BaseMultiMetaLadderItem(MetaBlock block) {
        this(block.blockID, block);
    }
    public BaseMultiMetaLadderItem(int blockID, Block block) {
        super(blockID);
        this.metaBlock = (BaseMultiMetaLadderBlock<MetaBlock>) block;
        setHasSubtypes(metaBlock.getMetaBlock().hasTypes());
        setMaxDamage(0);
        setUnlocalizedName(metaBlock.getUnlocalizedName());
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
        return String.format("%s.name", getUnlocalizedName());
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
