package ru.liner.decorative.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import ru.liner.decorative.blocks.ILocalized;
import ru.liner.decorative.blocks.list.BlockPurpur;
import ru.liner.decorative.recipes.IProvideShapedRecipe;
import ru.liner.decorative.register.Registry;
import ru.liner.decorative.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class ItemChorusFruitPopped extends Item implements ILocalized, IProvideShapedRecipe {
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

    @Override
    public String getBaseLocalizedName() {
        return "Приготовленный плод хоруса";
    }
    @Override
    public ItemStack getCraftResult() {
        return new ItemStack(Registry.getInstance().block(BlockPurpur.class).blockID, 1, 0);
    }

    @Override
    public String[] getCraftPattern() {
        return new String[]{
                "###",
                "###",
                "###"
        };
    }

    @Override
    public List<Pair<Character, ?>> getCraftDescription() {
        List<Pair<Character, ?>> descriptionList = new ArrayList<>();
        descriptionList.add(new Pair<Character, Object>('#', new ItemStack(this)));
        return descriptionList;
    }
}
