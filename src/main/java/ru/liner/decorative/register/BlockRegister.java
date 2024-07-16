package ru.liner.decorative.register;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.*;
import ru.liner.decorative.blocks.list.BlockBanner;
import ru.liner.decorative.items.*;
import ru.liner.decorative.tile.TileEntityBanner;

import java.util.ArrayList;
import java.util.List;

import static ru.liner.decorative.register.Blocks.*;

public class BlockRegister {

    public static void init(){
        registerBlock(hardenedClay, "Обожжёная глина");
        registerBlock(coal, "Блок угля");
        registerMultiBlock(flowerDouble, "Цветок");
        registerMultiBlock(flower, "Цветок");
        registerMultiBlock(saplingBlock, "Сажанец");



        registerMetaMultiBlock(carpet);
        registerMetaMultiBlock(clayStained);
        registerMetaMultiBlock(glassStained);
        registerMetaMultiBlock(glassPaneStained);
        registerMetaMultiBlock(stone);
        registerMetaMultiBlock(prismarine);
        registerMetaMultiBlock(redSandStone);
        registerStairs(stone);
        registerSlabs(stone);
        registerStairs(prismarine);
        registerSlabs(prismarine);

        registerMetaMultiBlock(leaves);
        registerMetaMultiBlock(woodenLog);
        registerMetaMultiBlock(planks);
        registerStairs(planks);
        registerSlabs(planks);
        registerFence(planks);
        registerLadder(planks);
        registerWall(planks);




        registerMetaBlock(hayBale);
        registerMetaBlock(podzol);
        registerMetaBlock(endstoneBricks);
        registerMetaMultiBlock(purpur);
        registerStairs(purpur);
        registerSlabs(purpur);

        registerBanner(banner);
        GameRegistry.registerTileEntity(TileEntityBanner.class, TileEntityBanner.class.getName());

        registerBlock(sandRed, "Красный песок");

        registerMetaBlock(chorusFlower);
        registerMetaBlock(chorusPlant);
        registerMetaBlock(magma);
    }

    public static <MetaBlock extends BaseMultiMetaBlock> void registerMetaMultiBlock(MetaBlock metaBlock){
        LanguageRegistry.instance().addStringLocalization(String.format("%s.name", metaBlock.getUnlocalizedName()), metaBlock.getBaseLocalizedName());
        for (int index = 0; index < metaBlock.getTypesCount(); index++) {
            String type = metaBlock.getTypeByMetadata(index);
            String localization = metaBlock.getLocalizationFor(type);
            LanguageRegistry.instance().addStringLocalization(String.format("%s.%s.name", metaBlock.getUnlocalizedName(), type), localization);
        }
        GameRegistry.registerBlock(metaBlock, BaseMultiMetaItem.class, metaBlock.getUnlocalizedName());
    }
    public static <MetaBlock extends BaseMultiMetaBlock> void registerBanner(BlockBanner metaBlock){
        LanguageRegistry.instance().addStringLocalization(String.format("%s.name", metaBlock.getUnlocalizedName()), metaBlock.getBaseLocalizedName());
        for (int index = 0; index < metaBlock.getTypesCount(); index++) {
            String type = metaBlock.getTypeByMetadata(index);
            String localization = metaBlock.getLocalizationFor(type);
            LanguageRegistry.instance().addStringLocalization(String.format("%s.%s.name", metaBlock.getUnlocalizedName(), type), localization);
        }
        GameRegistry.registerBlock(metaBlock, BaseMultiMetaBannerItem.class, metaBlock.getUnlocalizedName());
    }

    public static <MetaBlock extends BaseMetaBlock> void registerMetaBlock(MetaBlock metaBlock){
        LanguageRegistry languageRegistry = LanguageRegistry.instance();
        languageRegistry.addStringLocalization(String.format("%s.name", metaBlock.getUnlocalizedName()), metaBlock.getBaseLocalizedName());
        GameRegistry.registerBlock(metaBlock, metaBlock.getUnlocalizedName());
    }
    public static <B extends Block & ILocalized> void registerMetaBlock(B metaBlock){
        LanguageRegistry languageRegistry = LanguageRegistry.instance();
        languageRegistry.addStringLocalization(String.format("%s.name", metaBlock.getUnlocalizedName()), metaBlock.getBaseLocalizedName());
        GameRegistry.registerBlock(metaBlock, metaBlock.getUnlocalizedName());
    }
    public static <MetaBlock extends BaseMetaBlock, ItemClass extends ItemBlock> void registerMetaBlock(MetaBlock metaBlock, Class<ItemClass> itemClass){
        LanguageRegistry languageRegistry = LanguageRegistry.instance();
        languageRegistry.addStringLocalization(String.format("%s.name", metaBlock.getUnlocalizedName()), metaBlock.getBaseLocalizedName());
        GameRegistry.registerBlock(metaBlock, itemClass, metaBlock.getUnlocalizedName());
    }


    public static <MetaBlock extends BaseMultiMetaBlock> List<BaseMultiMetaStairsBlock<MetaBlock>> registerStairs(MetaBlock metaBlock){
        List<BaseMultiMetaStairsBlock<MetaBlock>> stairsBlockList = new ArrayList<>();
        LanguageRegistry languageRegistry = LanguageRegistry.instance();
        for (int index = 0; index < metaBlock.getTypesCount(); index++) {
            BaseMultiMetaStairsBlock<MetaBlock> stairsBlock = new BaseMultiMetaStairsBlock<>(metaBlock, index);
            languageRegistry.addStringLocalization(String.format("%s.name", stairsBlock.getUnlocalizedName()), stairsBlock.getLocalizationFor(index));
            GameRegistry.registerBlock(stairsBlock, BaseMultiMetaStairsItem.class, stairsBlock.getUnlocalizedName());
            stairsBlockList.add(stairsBlock);
            GameRegistry.addRecipe(
                    new ItemStack(stairsBlock.blockID, 4, 0),
                    "#  ",
                    "## ",
                    "###",
                    '#', new ItemStack(metaBlock.blockID, 1, index)
            );
        }
        return stairsBlockList;
    }

    public static <MetaBlock extends BaseMultiMetaBlock> List<BaseMultiMetaSlabBlock<MetaBlock>> registerSlabs(MetaBlock metaBlock){
        List<BaseMultiMetaSlabBlock<MetaBlock>> slabBlockList = new ArrayList<>();
        LanguageRegistry languageRegistry = LanguageRegistry.instance();
        for (int index = 0; index < metaBlock.getTypesCount(); index++) {
            BaseMultiMetaSlabBlock<MetaBlock> slabBlock = new BaseMultiMetaSlabBlock<>(metaBlock, index);
            languageRegistry.addStringLocalization(String.format("%s.name", slabBlock.getUnlocalizedName()), slabBlock.getLocalizationFor(index));
            GameRegistry.registerBlock(slabBlock, BaseMultiMetaSlabItem.class, slabBlock.getUnlocalizedName());
            slabBlockList.add(slabBlock);
            GameRegistry.addRecipe(
                    new ItemStack(slabBlock.blockID, 6, 0),
                    "   ",
                            "###",
                            "   ",
                    '#', new ItemStack(metaBlock.blockID, 1, index)
            );

        }
        return slabBlockList;
    }
    public static <MetaBlock extends BaseMultiMetaBlock> List<BaseMultiMetaFenceBlock<MetaBlock>> registerFence(MetaBlock metaBlock){
        List<BaseMultiMetaFenceBlock<MetaBlock>> fenceBlockList = new ArrayList<>();
        LanguageRegistry languageRegistry = LanguageRegistry.instance();
        for (int index = 0; index < metaBlock.getTypesCount(); index++) {
            BaseMultiMetaFenceBlock<MetaBlock> fenceBlock = new BaseMultiMetaFenceBlock<>(metaBlock, index);
            languageRegistry.addStringLocalization(String.format("%s.name", fenceBlock.getUnlocalizedName()), fenceBlock.getLocalizationFor(index));
            GameRegistry.registerBlock(fenceBlock, BaseMultiMetaFenceItem.class, fenceBlock.getUnlocalizedName());
            fenceBlockList.add(fenceBlock);
            GameRegistry.addRecipe(
                    new ItemStack(fenceBlock.blockID, 6, 0),
                    "#I#",
                    "#I#",
                    "   ",
                    '#', new ItemStack(metaBlock.blockID, 1, index),
                    'I', new ItemStack(Item.stick.itemID, 1, index)
            );
        }
        return fenceBlockList;
    }
    public static <MetaBlock extends BaseMultiMetaBlock> List<BaseMultiMetaLadderBlock<MetaBlock>> registerLadder(MetaBlock metaBlock){
        List<BaseMultiMetaLadderBlock<MetaBlock>> ladderBlockList = new ArrayList<>();
        LanguageRegistry languageRegistry = LanguageRegistry.instance();
        for (int index = 0; index < metaBlock.getTypesCount(); index++) {
            BaseMultiMetaLadderBlock<MetaBlock> ladderBlock = new BaseMultiMetaLadderBlock<>(metaBlock, index);
            languageRegistry.addStringLocalization(String.format("%s.name", ladderBlock.getUnlocalizedName()), ladderBlock.getLocalizationFor(index));
            GameRegistry.registerBlock(ladderBlock, BaseMultiMetaLadderItem.class, ladderBlock.getUnlocalizedName());
            ladderBlockList.add(ladderBlock);
            GameRegistry.addRecipe(
                    new ItemStack(ladderBlock.blockID, 3, 0),
                    "I#I",
                    "I#I",
                    "I#I",
                    '#', new ItemStack(metaBlock.blockID, 1, index),
                    'I', new ItemStack(Item.stick.itemID, 1, index)
            );
        }
        return ladderBlockList;
    }
    public static <MetaBlock extends BaseMultiMetaBlock> List<BaseMultiMetaWallBlock<MetaBlock>> registerWall(MetaBlock metaBlock){
        List<BaseMultiMetaWallBlock<MetaBlock>> wallBlockList = new ArrayList<>();
        LanguageRegistry languageRegistry = LanguageRegistry.instance();
        for (int index = 0; index < metaBlock.getTypesCount(); index++) {
            BaseMultiMetaWallBlock<MetaBlock> wallBlock = new BaseMultiMetaWallBlock<>(metaBlock, index);
            languageRegistry.addStringLocalization(String.format("%s.name", wallBlock.getUnlocalizedName()), wallBlock.getLocalizationFor(index));
            GameRegistry.registerBlock(wallBlock, BaseMultiMetaWallItem.class, wallBlock.getUnlocalizedName());
            wallBlockList.add(wallBlock);
            GameRegistry.addRecipe(
                    new ItemStack(wallBlock.blockID, 6, 0),
                    "###",
                    "###",
                    "   ",
                    '#', new ItemStack(metaBlock.blockID, 1, index)
            );
        }
        return wallBlockList;
    }





    private static <B extends Block, I extends ItemBlock> void registerColoredBlock(B block, Class<I> itemClass, String localizedName, int gender){
        for (int i = 0; i < 16; i++) {
            String key = String.format("%s.%s.name", block.getUnlocalizedName(), ItemDye.dyeColorNames[i]);
            String translate = String.format("%s %s", Decorative.colorNames[i + (ItemDye.dyeColorNames.length * gender)], localizedName.toLowerCase());
            LanguageRegistry.instance().addStringLocalization(key, translate);
        }
        registerBlock(block, itemClass, localizedName);
    }

    private static <B extends Block, I extends ItemBlock> void registerBlock(B block, Class<I> itemClass, String localizedName){
        LanguageRegistry.instance().addStringLocalization(block.getUnlocalizedName()+".name", localizedName);
        GameRegistry.registerBlock(block, itemClass, block.getUnlocalizedName());
    }

    private static <B extends Block> void registerBlock(B block, String localizedName){
        LanguageRegistry.instance().addStringLocalization(block.getUnlocalizedName()+".name", localizedName);
        GameRegistry.registerBlock(block, block.getUnlocalizedName());
    }

    private static <B extends IMultiTexturedBlock> void registerMultiBlock(B block, String localizedName){
        LanguageRegistry.instance().addStringLocalization(block.getUnlocalizedName()+".name", localizedName);
        for (int i = 0; i < block.getTypesCount(); i++)
            LanguageRegistry.instance().addStringLocalization(String.format("%s.%s.name", block.getUnlocalizedName(), block.typeAt(i)), block.localizedAt(i));
        GameRegistry.registerBlock((Block) block, BaseMultiItem.class, block.getUnlocalizedName());
    }


    public static int nextAvailableId(int startFrom){
        for (int i = startFrom; i < Block.blocksList.length; i++) {
            if(Block.blocksList[i] == null)
                return i;
        }
        return 0;
    }
}
