package ru.liner.decorative.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.BlockCarpet;

import java.util.List;

public class ItemStainedClay extends ItemBlock {
    public ItemStainedClay(int carpetBlockId) {
        super(carpetBlockId);
        setMaxDamage(0);
        setHasSubtypes(true);
        setUnlocalizedName("clayHardenedStained");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage(int par1) {
        return Decorative.blockStainedHardenedClay.getIcon(2, BlockCarpet.getBlockFromDye(par1));
    }

    @Override
    public int getMetadata(int par1) {
        return par1;
    }

    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        Decorative.blockStainedHardenedClay.getSubBlocks(par1, par2CreativeTabs, par3List);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return String.format("%s.%s.name", super.getUnlocalizedName(), ItemDye.dyeColorNames[BlockCarpet.getBlockFromDye(itemStack.getItemDamage())]);
    }

    @Override
    public String getItemDisplayName(ItemStack par1ItemStack) {
        return getLocalizedName(par1ItemStack);
    }
}
