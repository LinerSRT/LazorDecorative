package ru.liner.decorative.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.blocks.list.BlockHayBale;
import ru.liner.decorative.items.*;

import java.util.ArrayList;
import java.util.List;

import static ru.liner.decorative.blocks.Blocks.*;

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
        registerStairs(stone);
        registerSlabs(stone);

        registerMetaMultiBlock(leaves);
        registerMetaMultiBlock(woodenLog);
        registerMetaMultiBlock(woodenDoor);
        registerMetaMultiBlock(planks);
        registerStairs(planks);
        registerSlabs(planks);


        registerMetaBlock(hayBale);
        registerMetaBlock(podzol);
    }

    public static <MetaBlock extends BaseMultiMetaBlock> void registerMetaMultiBlock(MetaBlock metaBlock){
        LanguageRegistry languageRegistry = LanguageRegistry.instance();
        languageRegistry.addStringLocalization(String.format("%s.name", metaBlock.getUnlocalizedName()), metaBlock.getBaseLocalizedName());
        for (int index = 0; index < metaBlock.getTypesCount(); index++) {
            String type = metaBlock.getTypeByMetadata(index);
            String localization = metaBlock.getLocalizationFor(type);
            languageRegistry.addStringLocalization(String.format("%s.%s.name", metaBlock.getUnlocalizedName(), type), localization);
        }
        GameRegistry.registerBlock(metaBlock, BaseMultiMetaItem.class, metaBlock.getUnlocalizedName());
    }
    public static <MetaBlock extends BaseMetaBlock> void registerMetaBlock(MetaBlock metaBlock){
        LanguageRegistry languageRegistry = LanguageRegistry.instance();
        languageRegistry.addStringLocalization(String.format("%s.name", metaBlock.getUnlocalizedName()), metaBlock.getBaseLocalizedName());
        GameRegistry.registerBlock(metaBlock, metaBlock.getUnlocalizedName());
    }

    public static <MetaBlock extends BaseMultiMetaBlock> List<BaseMultiMetaStairsBlock<MetaBlock>> registerStairs(MetaBlock metaBlock){
        List<BaseMultiMetaStairsBlock<MetaBlock>> stairsBlockList = new ArrayList<>();
        LanguageRegistry languageRegistry = LanguageRegistry.instance();
        for (int index = 0; index < metaBlock.getTypesCount(); index++) {
            BaseMultiMetaStairsBlock<MetaBlock> stairsBlock = new BaseMultiMetaStairsBlock<>(metaBlock, index);
            languageRegistry.addStringLocalization(String.format("%s.name", stairsBlock.getUnlocalizedName()), stairsBlock.getLocalizationFor(index));
            GameRegistry.registerBlock(stairsBlock, BaseMultiMetaStairsItem.class, stairsBlock.getUnlocalizedName());
            stairsBlockList.add(stairsBlock);
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
        }
        return slabBlockList;
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

}
