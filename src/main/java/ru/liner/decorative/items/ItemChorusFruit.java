package ru.liner.decorative.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import scala.util.parsing.combinator.testing.Str;

public class ItemChorusFruit extends Item {
    public ItemChorusFruit(int itemId) {
        super(itemId);
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("chorusFruit");
    }

    @Override
    public void registerIcons(IconRegister register) {
        itemIcon = register.registerIcon("chorus_fruit");
    }


    @Override
    public String getLocalizedName(ItemStack stack) {
        return StatCollector.translateToLocal(String.format("item.%s", getUnlocalizedName()));
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
        return getLocalizedName(stack);
    }
}
