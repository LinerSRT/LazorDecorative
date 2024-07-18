package ru.liner.decorative.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import ru.liner.decorative.blocks.ILocalized;
import ru.liner.decorative.recipes.ISmellable;
import ru.liner.decorative.register.Registry;

public class ItemChorusFruit extends Item implements ILocalized, ISmellable {
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

    @Override
    public String getBaseLocalizedName() {
        return "Плод хоруса";
    }

    @Override
    public float getXp() {
        return 0;
    }

    @Override
    public ItemStack getSmeltingResult() {
        return new ItemStack(Registry.getInstance().item(ItemChorusFruitPopped.class));
    }

    @Override
    public int getItemId() {
        return itemID;
    }
}
