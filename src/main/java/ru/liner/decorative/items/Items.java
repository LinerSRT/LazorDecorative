package ru.liner.decorative.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.liner.decorative.blocks.Blocks;
import ru.liner.decorative.utils.Colored;

public class Items {
    public static ItemChorusFruit chorusFruit = new ItemChorusFruit(1058);
    public static ItemChorusFruitPopped chorusFruitPopped = new ItemChorusFruitPopped(1059);


    public static void init(){
        registerItem(chorusFruit, "Плод хоруса");
        registerItem(chorusFruitPopped, "Приготовленный плод хоруса");
        GameRegistry.addSmelting(chorusFruit.itemID, new ItemStack(chorusFruitPopped), 0);
        GameRegistry.addRecipe(
                new ItemStack(Blocks.purpur.blockID, 1, 0),
                "###",
                        "###",
                        "###",
                '#', new ItemStack(chorusFruitPopped)
        );
        GameRegistry.addRecipe(
                new ItemStack(Blocks.purpur.blockID, 8, 1),
                "###",
                        "# #",
                        "###",
                '#', new ItemStack(Blocks.purpur, 1, 0)
        );
    }


    private static void registerItem(Item item, String localizedName){
        LanguageRegistry.instance().addStringLocalization(String.format("item.%s", item.getUnlocalizedName()), localizedName);
        GameRegistry.registerItem(item, item.getUnlocalizedName());
    }
}
