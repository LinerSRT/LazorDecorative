package ru.liner.decorative;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import ru.liner.decorative.blocks.*;
import ru.liner.decorative.items.*;

public class Decorative {
    public static int STAINED_GLASS_PANE_RENDER_ID;
    public static int DOUBLE_PLANT_RENDERER;
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
    public static BlockLog logBlock;
    public static BlockSapling saplingBlock;
    public static BlockLeaves leavesBlock;
    public static BlockFlowerPlant flowerBlock;




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
    public static BlockDoublePlant doublePlantBlock;


    public static void init(){
        STAINED_GLASS_PANE_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        DOUBLE_PLANT_RENDERER = RenderingRegistry.getNextAvailableRenderId();
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
        doublePlantBlock = new BlockDoublePlant(179);
        flowerBlock = new BlockFlowerPlant(180);


        registerColoredBlock(carpetBlock, ItemCarpet.class,"Коврик", 0);
        registerBlock(hayBaleBlock, "Сноп сена");
        registerColoredBlock(blockStainedHardenedClay, ItemStainedClay.class,"Обожжёная глина", 1);
        registerColoredBlock(stainedGlassBlock, ItemStainedGlass.class,"Стекло", 2);
        registerColoredBlock(stainedGlassPaneBlock, ItemStainedGlassPane.class,"стеклянная панель", 1);
        registerBlock(hardenedClayBlock, "Обожжёная глина");
        registerBlock(coalBlock, "Блок угля");
        registerBlock(podzolBlock, "Подзол");
        registerMultiBlock(doublePlantBlock, "Цветок");
        registerMultiBlock(flowerBlock, "Цветок");


        registerMultiBlock(logBlock, "Дерево");
        registerMultiBlock(leavesBlock, "Листва");
        registerMultiBlock(saplingBlock, "Сажанец");
        registerMultiBlock(planksBlock, "Доски");
        registerStairs(166, planksBlock, 0);
        registerStairs(167, planksBlock, 1);
        registerSlab(177, planksBlock, 0);
        registerSlab(178, planksBlock, 1);
    }


    private static void registerStairs(int blockID, BaseMultiBlock block, int subType){
        registerMultiBlock((IMultiTexturedBlock) new BlockCustomStairs( blockID, block, subType).setUnlocalizedName("stairs."+block.typeAt(subType)), block.localizedAt(subType));
    }
    private static void registerSlab(int blockID, BaseMultiBlock block, int subType){
        registerMultiBlock((IMultiTexturedBlock) new BlockCustomSlab( blockID, block, subType).setUnlocalizedName("slabs."+block.typeAt(subType)), block.localizedAt(subType));
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
