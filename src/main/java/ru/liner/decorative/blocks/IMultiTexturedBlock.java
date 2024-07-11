package ru.liner.decorative.blocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;

import java.util.List;

public interface IMultiTexturedBlock {
    String getUnlocalizedName();
    String typeAt(int metadata);
    String localizedAt(int metadata);
    int getTypesCount();
    int getBlockId();
    Icon getIcon(int side, int metadata);
    void getSubBlocks(int blockId, CreativeTabs creativeTabs, List<?> itemList);
}
