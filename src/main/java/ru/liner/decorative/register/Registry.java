package ru.liner.decorative.register;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.liner.decorative.DecorativeMod;
import ru.liner.decorative.blocks.*;
import ru.liner.decorative.blocks.list.BlockBed;
import ru.liner.decorative.items.*;
import ru.liner.decorative.recipes.IProvideShapedRecipe;
import ru.liner.decorative.recipes.IProvideShapelessRecipe;
import ru.liner.decorative.recipes.ISmellable;
import ru.liner.decorative.recipes.RecipeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Registry {
    private static Registry instance;
    private LanguageRegistry languageRegistry;
    private List<Block> blockList;
    private List<Item> itemList;

    public static Registry getInstance() {
        return instance == null ? instance = new Registry() : instance;
    }

    private Registry() {
        this.blockList = new ArrayList<>();
        this.itemList = new ArrayList<>();
        this.languageRegistry = LanguageRegistry.instance();
    }

    private String getLocalizedNameFor(Object object) {
        if (object instanceof IMultiTexturedBlock) {
            return ((IMultiTexturedBlock) object).getLocalization();
        }
        if (object instanceof ILocalized) {
            return ((ILocalized) object).getBaseLocalizedName();
        }
        if (object instanceof Block) {
            return ((Block) object).getLocalizedName();
        }
        DecorativeMod.logger.log(Level.WARNING, String.format("Registry cannot find localization for %s", object.getClass().getSimpleName()));
        return "Unknown";
    }

    public Registry add(Block block) {
        this.blockList.add(block);
        return this;
    }

    public Registry registerBlocks() {
        for (Block block : blockList) {
            languageRegistry.addStringLocalization(String.format("%s.name", block.getUnlocalizedName()), getLocalizedNameFor(block));
            DecorativeMod.logger.log(Level.INFO, "Registering block: " + getLocalizedNameFor(block));
            if(block instanceof IMultiTexturedBlock){
                IMultiTexturedBlock multiTexturedBlock = (IMultiTexturedBlock) block;
                languageRegistry.addStringLocalization(block.getUnlocalizedName()+".name", multiTexturedBlock.getLocalization());
                for (int i = 0; i < multiTexturedBlock.getTypesCount(); i++)
                    LanguageRegistry.instance().addStringLocalization(String.format("%s.%s.name", block.getUnlocalizedName(), multiTexturedBlock.typeAt(i)), multiTexturedBlock.localizedAt(i));
                GameRegistry.registerBlock( block, BaseMultiItem.class, block.getUnlocalizedName());
                applyRecipes(block);
            } else if (block instanceof BaseMultiMetaBlock) {
                BaseMultiMetaBlock metaBlock = (BaseMultiMetaBlock) block;
                for (int i = 0; i < metaBlock.getTypesCount(); i++) {
                    String type = metaBlock.getTypeByMetadata(i);
                    String localization = metaBlock.getLocalizationFor(type);
                    languageRegistry.addStringLocalization(String.format("%s.%s.name", metaBlock.getUnlocalizedName(), type), localization);
                }
                GameRegistry.registerBlock(block, block instanceof IItemProvider ? ((IItemProvider) block).getItemClass() : BaseMultiMetaItem.class, block.getUnlocalizedName());
                applyRecipes(block);
                 if (block instanceof IBlockFamily) {
                    BlockFamilyTypes[] types = ((IBlockFamily) block).getFamiliarityWith();
                    if (types != null) {
                        for (BlockFamilyTypes type : types) {
                            switch (type) {
                                case STAIR:
                                    for (int i = 0; i < metaBlock.getTypesCount(); i++) {
                                        BaseMultiMetaStairsBlock stairsBlock = new BaseMultiMetaStairsBlock<>(metaBlock, i);
                                        languageRegistry.addStringLocalization(String.format("%s.name", stairsBlock.getUnlocalizedName()), stairsBlock.getLocalizationFor(i));
                                        GameRegistry.registerBlock(stairsBlock, BaseMultiMetaStairsItem.class, stairsBlock.getUnlocalizedName());
                                        GameRegistry.addRecipe(
                                                new ItemStack(stairsBlock.blockID, 4, 0),
                                                "#  ",
                                                "## ",
                                                "###",
                                                '#', new ItemStack(metaBlock.blockID, 1, i)
                                        );
                                    }
                                    break;
                                case SLAB:
                                    for (int i = 0; i < metaBlock.getTypesCount(); i++) {
                                        BaseMultiMetaSlabBlock slabBlock = new BaseMultiMetaSlabBlock<>(metaBlock, i);
                                        languageRegistry.addStringLocalization(String.format("%s.name", slabBlock.getUnlocalizedName()), slabBlock.getLocalizationFor(i));
                                        GameRegistry.registerBlock(slabBlock, BaseMultiMetaSlabItem.class, slabBlock.getUnlocalizedName());
                                        GameRegistry.addRecipe(
                                                new ItemStack(slabBlock.blockID, 6, 0),
                                                "   ",
                                                "###",
                                                "   ",
                                                '#', new ItemStack(metaBlock.blockID, 1, i)
                                        );
                                    }
                                    break;
                                case FENCE:
                                    for (int i = 0; i < metaBlock.getTypesCount(); i++) {
                                        BaseMultiMetaFenceBlock fenceBlock = new BaseMultiMetaFenceBlock<>(metaBlock, i);
                                        languageRegistry.addStringLocalization(String.format("%s.name", fenceBlock.getUnlocalizedName()), fenceBlock.getLocalizationFor(i));
                                        GameRegistry.registerBlock(fenceBlock, BaseMultiMetaFenceItem.class, fenceBlock.getUnlocalizedName());
                                        GameRegistry.addRecipe(
                                                new ItemStack(fenceBlock.blockID, 6, 0),
                                                "#I#",
                                                "#I#",
                                                "   ",
                                                '#', new ItemStack(metaBlock.blockID, 1, i),
                                                'I', new ItemStack(Item.stick.itemID, 1, i)
                                        );
                                        GameRegistry.addRecipe(
                                                new ItemStack(fenceBlock.blockID, 6, 0),
                                                "   ",
                                                "#I#",
                                                "#I#",
                                                '#', new ItemStack(metaBlock.blockID, 1, i),
                                                'I', new ItemStack(Item.stick.itemID, 1, i)
                                        );
                                    }
                                    break;
                                case LADDER:
                                    for (int i = 0; i < metaBlock.getTypesCount(); i++) {
                                        BaseMultiMetaLadderBlock ladderBlock = new BaseMultiMetaLadderBlock<>(metaBlock, i);
                                        languageRegistry.addStringLocalization(String.format("%s.name", ladderBlock.getUnlocalizedName()), ladderBlock.getLocalizationFor(i));
                                        GameRegistry.registerBlock(ladderBlock, BaseMultiMetaLadderItem.class, ladderBlock.getUnlocalizedName());
                                        GameRegistry.addRecipe(
                                                new ItemStack(ladderBlock.blockID, 3, 0),
                                                "I#I",
                                                "I#I",
                                                "I#I",
                                                '#', new ItemStack(metaBlock.blockID, 1, i),
                                                'I', new ItemStack(Item.stick.itemID, 1, i)
                                        );
                                    }
                                    break;
                                case WALL:
                                    for (int i = 0; i < metaBlock.getTypesCount(); i++) {
                                        BaseMultiMetaWallBlock wallBlock = new BaseMultiMetaWallBlock<>(metaBlock, i);
                                        languageRegistry.addStringLocalization(String.format("%s.name", wallBlock.getUnlocalizedName()), wallBlock.getLocalizationFor(i));
                                        GameRegistry.registerBlock(wallBlock, BaseMultiMetaWallItem.class, wallBlock.getUnlocalizedName());
                                        GameRegistry.addRecipe(
                                                new ItemStack(wallBlock.blockID, 6, 0),
                                                "###",
                                                "###",
                                                "   ",
                                                '#', new ItemStack(metaBlock.blockID, 1, i)
                                        );
                                        GameRegistry.addRecipe(
                                                new ItemStack(wallBlock.blockID, 6, 0),
                                                "   ",
                                                "###",
                                                "###",
                                                '#', new ItemStack(metaBlock.blockID, 1, i)
                                        );
                                    }
                                    break;
                            }
                        }
                    }
                }
            } else if (block instanceof BaseMetaBlock) {
                BaseMetaBlock metaBlock = (BaseMetaBlock) block;
                languageRegistry.addStringLocalization(String.format("%s.name", metaBlock.getUnlocalizedName()), metaBlock.getBaseLocalizedName());
                GameRegistry.registerBlock(metaBlock, metaBlock.getUnlocalizedName());
                applyRecipes(metaBlock);
            } else {
                languageRegistry.addStringLocalization(String.format("%s.name", block.getUnlocalizedName()), getLocalizedNameFor(block));
                if(block instanceof IItemProvider){
                    GameRegistry.registerBlock(block, ((IItemProvider)block).getItemClass(), block.getUnlocalizedName());
                } else {
                    GameRegistry.registerBlock(block, block.getUnlocalizedName());
                }
                applyRecipes(block);
            }
        }
        return this;
    }

    public Registry registerAll() {
        registerBlocks();
        return this;
    }


    private void applyRecipes(Block block) {
        if (block instanceof IProvideShapedRecipe)
            RecipeUtils.addShapedRecipe((IProvideShapedRecipe) block);
        if (block instanceof IProvideShapelessRecipe)
            RecipeUtils.addShapelessRecipe((IProvideShapelessRecipe) block);
        if (block instanceof ISmellable)
            RecipeUtils.addSmellableRecipe((ISmellable) block);
        if (block instanceof IFuelHandler) {
            GameRegistry.registerFuelHandler((IFuelHandler) block);
        }
    }
}
