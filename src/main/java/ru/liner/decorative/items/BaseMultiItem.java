package ru.liner.decorative.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import ru.liner.decorative.blocks.BaseMultiBlock;
import ru.liner.decorative.blocks.IMultiTexturedBlock;

import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BaseMultiItem<B extends IMultiTexturedBlock> extends ItemBlock {
    protected B multiBlock;
    protected int iconSide;



    public BaseMultiItem(B multiBlock) {
        super(multiBlock.getBlockId());
        setHasSubtypes(true);
        setMaxDamage(0);
        setUnlocalizedName(multiBlock.getUnlocalizedName());
        this.multiBlock = multiBlock;
        this.iconSide = 2;
    }

    public BaseMultiItem(Block multiBlock) {
        super(multiBlock.blockID);
        setHasSubtypes(true);
        setMaxDamage(0);
        setUnlocalizedName(multiBlock.getUnlocalizedName());
        this.multiBlock = (B) multiBlock;
        this.iconSide = 2;
    }
    public BaseMultiItem(int blockID, Block multiBlock) {
        super(blockID);
        setHasSubtypes(true);
        setMaxDamage(0);
        setUnlocalizedName(multiBlock.getUnlocalizedName());
        this.multiBlock = (B) multiBlock;
        this.iconSide = 2;
    }

    public BaseMultiItem setMultiBlock(B multiBlock) {
        this.multiBlock = multiBlock;
        return this;
    }

    public BaseMultiItem setIconSide(int iconSide) {
        this.iconSide = iconSide;
        return this;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage(int metadata) {
        return multiBlock.getIcon(iconSide, metadata);
    }

    @Override
    public void getSubItems(int blockID, CreativeTabs creativeTabs, List itemList) {
        multiBlock.getSubBlocks(blockID, creativeTabs, itemList);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return String.format("%s.%s.name", super.getUnlocalizedName(), multiBlock.typeAt(itemStack.getItemDamage()));
    }

    @Override
    public String getItemDisplayName(ItemStack itemStack) {
        return getLocalizedName(itemStack);
    }

    @Override
    public int getMetadata(int metadata) {
        return metadata;
    }
}
