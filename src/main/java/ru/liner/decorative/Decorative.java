package ru.liner.decorative;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import ru.liner.decorative.blocks.*;
import ru.liner.decorative.items.ItemCarpet;
import ru.liner.decorative.items.ItemStainedClay;
import scala.util.parsing.combinator.testing.Str;

import java.util.HashMap;

public class Decorative {
    public static BlockCarpet carpetBlock;
    public static BlockHayBale hayBaleBlock;
    public static BlockCoal coalBlock;
    public static BlockHardenedClay hardenedClayBlock;
    public static BlockStainedHardenedClay blockStainedHardenedClay;

    private static final String[] colorNames = new String[]{
            "Черный",
            "Красный",
            "Зеленый",
            "Коричневый",
            "Синий",
            "Фиолетовый",
            "Бирюзовый",
            "Светло-серый",
            "Серый",
            "Розовый",
            "Лаймовый",
            "Желтый",
            "Голубой",
            "Пурпурный",
            "Оранжевый",
            "Белый",
            "Черная",
            "Красная",
            "Зеленая",
            "Коричневая",
            "Синяя",
            "Фиолетовая",
            "Бирюзовая",
            "Светло-серая",
            "Серая",
            "Розовая",
            "Лаймовая",
            "Желтая",
            "Голубая",
            "Пурпурная",
            "Оранжевая",
            "Белая",
            "Черное",
            "Красное",
            "Зеленое",
            "Коричневое",
            "Синее",
            "Фиолетовое",
            "Бирюзовое",
            "Светло-серое",
            "Серое",
            "Розовое",
            "Лаймовое",
            "Желтое",
            "Голубое",
            "Пурпурное",
            "Оранжевое",
            "Белое"
    };



    public static void init(){
        carpetBlock = new BlockCarpet(171);
        hayBaleBlock =  new BlockHayBale(170);
        blockStainedHardenedClay =  new BlockStainedHardenedClay(169);
        hardenedClayBlock =  new BlockHardenedClay(172);
        coalBlock = new BlockCoal(173);
        registerColoredBlock(carpetBlock, ItemCarpet.class,"Коврик", 0);
        registerBlock(hayBaleBlock, "Сноп сена");
        registerColoredBlock(blockStainedHardenedClay, ItemStainedClay.class,"Обожжёная глина", 1);
        registerBlock(hardenedClayBlock, "Обожжёная глина");
        registerBlock(coalBlock, "Блок угля");
    }


    private static <B extends Block> void registerBlock(B block, String localizedName){
        LanguageRegistry.instance().addStringLocalization(block.getUnlocalizedName()+".name", localizedName);
        GameRegistry.registerBlock(block, block.getUnlocalizedName());
    }

    private static <B extends Block, I extends ItemBlock> void registerBlock(B block, Class<I> itemClass, String localizedName){
        LanguageRegistry.instance().addStringLocalization(block.getUnlocalizedName()+".name", localizedName);
        GameRegistry.registerBlock(block, itemClass, block.getUnlocalizedName());
    }

    private static <B extends Block, I extends ItemBlock> void registerColoredBlock(B block, Class<I> itemClass, String localizedName, int gender){
        for (int i = 0; i < 16; i++) {
            String key = String.format("%s.%s.name", block.getUnlocalizedName(), ItemDye.dyeColorNames[i]);
            String translate = String.format("%s %s", colorNames[i + (ItemDye.dyeColorNames.length * gender)], localizedName.toLowerCase());
            LanguageRegistry.instance().addStringLocalization(key, translate);
        }
       registerBlock(block, itemClass, localizedName);
    }
}
