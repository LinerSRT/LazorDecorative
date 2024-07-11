package ru.liner.decorative;

import com.google.common.collect.ArrayListMultimap;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.registry.BlockProxy;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import ru.liner.decorative.blocks.*;
import ru.liner.decorative.items.*;
import ru.liner.decorative.utils.Reflection;
import scala.collection.mutable.MultiMap;
import scala.util.parsing.combinator.testing.Str;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

public class Decorative {
    public static int STAINED_GLASS_PANE_RENDER_ID;
    public static BlockCarpet carpetBlock;
    public static BlockHayBale hayBaleBlock;
    public static BlockCoal coalBlock;
    public static BlockHardenedClay hardenedClayBlock;
    public static BlockStainedHardenedClay blockStainedHardenedClay;
    public static BlockDirtPodzol podzolBlock;
    public static BlockStainedGlass stainedGlassBlock;
    public static BlockStainedGlassPane stainedGlassPaneBlock;
    //public static BlockNewLog logBlock;

    public static BlockPlanks planksBlock;


    public static BlockNewStairs acaciaStairs;

    public static BlockLog logBlock;
    public static BlockSapling saplingBlock;
    public static BlockLeaves leavesBlock;



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
        STAINED_GLASS_PANE_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        carpetBlock = new BlockCarpet(171);
        hayBaleBlock =  new BlockHayBale(170);
        blockStainedHardenedClay =  new BlockStainedHardenedClay(169);
        hardenedClayBlock =  new BlockHardenedClay(172);
        coalBlock = new BlockCoal(173);
        podzolBlock = new BlockDirtPodzol(174);
        stainedGlassBlock = new BlockStainedGlass(175);
        stainedGlassPaneBlock = new BlockStainedGlassPane(176);

        logBlock = new BlockLog(162);
        leavesBlock = new BlockLeaves(164);


        planksBlock = new BlockPlanks(163);
        saplingBlock = new BlockSapling(165);
        acaciaStairs = (BlockNewStairs) new BlockNewStairs(166, planksBlock, 0).setUnlocalizedName("stairsWood");

        registerColoredBlock(carpetBlock, ItemCarpet.class,"Коврик", 0);
        registerBlock(hayBaleBlock, "Сноп сена");
        registerColoredBlock(blockStainedHardenedClay, ItemStainedClay.class,"Обожжёная глина", 1);
        registerColoredBlock(stainedGlassBlock, ItemStainedGlass.class,"Стекло", 2);
        registerColoredBlock(stainedGlassPaneBlock, ItemStainedGlassPane.class,"стеклянная панель", 1);
        registerBlock(hardenedClayBlock, "Обожжёная глина");
        registerBlock(coalBlock, "Блок угля");
        registerBlock(podzolBlock, "Подзол");


        registerMultiBlock(logBlock, "Дерево");
        registerMultiBlock(leavesBlock, "Листва");
        registerMultiBlock(saplingBlock, "Сажанец");
        registerMultiBlock(planksBlock, "Доски");





        registerBlock(acaciaStairs, "Хуета");
    }


    private static <B extends Block> void registerBlock(B block, String localizedName){
        LanguageRegistry.instance().addStringLocalization(block.getUnlocalizedName()+".name", localizedName);
        GameRegistry.registerBlock(block, block.getUnlocalizedName());
    }

    private static <B extends Block, I extends ItemBlock> void registerBlock(B block, Class<I> itemClass, String localizedName){
        LanguageRegistry.instance().addStringLocalization(block.getUnlocalizedName()+".name", localizedName);
        GameRegistry.registerBlock(block, itemClass, block.getUnlocalizedName());
    }

    private static <B extends IMultiTexturedBlock> void registerMultiBlock(B block, String localizedName){
        LanguageRegistry.instance().addStringLocalization(block.getUnlocalizedName()+".name", localizedName);
        for (int i = 0; i < block.getTypesCount(); i++)
            LanguageRegistry.instance().addStringLocalization(String.format("%s.%s.name", block.getUnlocalizedName(), block.typeAt(i)), block.localizedAt(i));
        GameRegistry.registerBlock((Block) block, BaseMultiItem.class, block.getUnlocalizedName());
    }



    private static <B extends Block, I extends ItemBlock> void registerMultiBlock(B block, Class<I> itemClass, String[] unlocalizedNames, String[] localizedNames){
        for (int i = 0; i < unlocalizedNames.length; i++) {
            LanguageRegistry.instance().addStringLocalization(String.format("%s.%s.name", block.getUnlocalizedName(), unlocalizedNames[i]), localizedNames[i]);
        }
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
