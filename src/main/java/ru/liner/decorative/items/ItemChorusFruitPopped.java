package ru.liner.decorative.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemChorusFruitPopped extends Item {
    public ItemChorusFruitPopped(int itemId) {
        super(itemId);
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("chorusFruitPopped");
    }

    @Override
    public void registerIcons(IconRegister register) {
        itemIcon = register.registerIcon("chorus_fruit_popped");
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
