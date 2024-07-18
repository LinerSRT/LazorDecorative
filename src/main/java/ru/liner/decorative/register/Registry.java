package ru.liner.decorative.register;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.liner.decorative.DecorativeMod;
import ru.liner.decorative.blocks.*;
import ru.liner.decorative.items.*;
import ru.liner.decorative.recipes.IProvideShapedRecipe;
import ru.liner.decorative.recipes.IProvideShapelessRecipe;
import ru.liner.decorative.recipes.ISmellable;
import ru.liner.decorative.recipes.RecipeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@SuppressWarnings({"unchecked", "rawtypes", "UnusedReturnValue"})
public class Registry {
    private static Registry instance;
    private final LanguageRegistry languageRegistry;
    private final List<Block> blockList;
    private final List<Item> itemList;
    private boolean wasBlocksRegistered;
    private boolean wasItemsRegistered;

    public static Registry getInstance() {
        return instance == null ? instance = new Registry() : instance;
    }

    private Registry() {
        this.blockList = new ArrayList<>();
        this.itemList = new ArrayList<>();
        this.languageRegistry = LanguageRegistry.instance();
    }



    public Registry add(Block block) {
        if (!wasBlocksRegistered) {
            this.blockList.add(block);
        } else {
            DecorativeMod.logger.log(Level.WARNING, String.format("Registry was registered specified blocks, block %s cannot be added after!", block.getClass().getSimpleName()));
        }
        return this;
    }

    public Registry add(Item item) {
        if (!wasItemsRegistered) {
            this.itemList.add(item);
        } else {
            DecorativeMod.logger.log(Level.WARNING, String.format("Registry was registered specified items, item %s cannot be added after!", item.getClass().getSimpleName()));
        }
        return this;
    }

    public <I extends Item> I item(int itemId) {
        for (Item item : itemList)
            if (item.itemID == itemId)
                return (I) item;
        return null;
    }

    public <I extends Item> I item(String itemId) {
        for (Item item : itemList)
            if (item.getUnlocalizedName().contains(itemId))
                return (I) item;
        return null;
    }

    public <I extends Item> I item(Class<I> itemClass) {
        for (Item item : itemList)
            if (itemClass.getSimpleName().equals(item.getClass().getSimpleName()))
                return (I) item;
        return null;
    }

    public <B extends Block> B block(int blockId) {
        for (Block block : blockList)
            if (block.blockID == blockId)
                return (B) block;
        return null;
    }

    public <B extends Block> B block(String blockId) {
        for (Block block : blockList)
            if (block.getUnlocalizedName().contains(blockId))
                return (B) block;
        return null;
    }

    public <B extends Block> B block(Class<B> blockClass) {
        for (Block block : blockList) {
            if (block.getClass().getSimpleName().equals(blockClass.getSimpleName()))
                return (B) block;
        }
        return null;
    }


    public Registry registerItems() {
        if (wasItemsRegistered) {
            DecorativeMod.logger.log(Level.WARNING, "You trying register items but it already was registered! Skipping...");
            return this;
        }
        for (Item item : itemList) {
            languageRegistry.addStringLocalization(String.format("item.%s", item.getUnlocalizedName()), getLocalizedNameFor(item));
            GameRegistry.registerItem(item, item.getUnlocalizedName());
            applyRecipes(item);
        }
        wasItemsRegistered = true;
        return this;
    }

    public Registry registerBlocks() {
        if (wasBlocksRegistered) {
            DecorativeMod.logger.log(Level.WARNING, "You trying register blocks but it already was registered! Skipping...");
            return this;
        }
        for (Block block : blockList) {
            languageRegistry.addStringLocalization(String.format("%s.name", block.getUnlocalizedName()), getLocalizedNameFor(block));
            DecorativeMod.logger.log(Level.INFO, String.format("Registering %s with id %s as %s", block.getUnlocalizedName(), block.blockID, getLocalizedNameFor(block)));
            if (block instanceof IMultiTexturedBlock) {
                IMultiTexturedBlock multiTexturedBlock = (IMultiTexturedBlock) block;
                languageRegistry.addStringLocalization(block.getUnlocalizedName() + ".name", multiTexturedBlock.getLocalization());
                for (int i = 0; i < multiTexturedBlock.getTypesCount(); i++)
                    LanguageRegistry.instance().addStringLocalization(String.format("%s.%s.name", block.getUnlocalizedName(), multiTexturedBlock.typeAt(i)), multiTexturedBlock.localizedAt(i));
                GameRegistry.registerBlock(block, BaseMultiItem.class, block.getUnlocalizedName());
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
                    FamilarityType[] types = ((IBlockFamily) block).getFamiliarityWith();
                    if (types != null) {
                        for (FamilarityType type : types) {
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
                if (block instanceof IItemProvider) {
                    GameRegistry.registerBlock(block, ((IItemProvider) block).getItemClass(), block.getUnlocalizedName());
                } else {
                    GameRegistry.registerBlock(block, block.getUnlocalizedName());
                }
                applyRecipes(block);
            }
        }
        wasBlocksRegistered = true;
        return this;
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
        if(object instanceof Item){
            return ((Item)object).getLocalizedName(new ItemStack((Item) object));
        }
        DecorativeMod.logger.log(Level.WARNING, String.format("Registry cannot find localization for %s", object.getClass().getSimpleName()));
        return "Unknown";
    }

    private void applyRecipes(Object o) {
        if (o instanceof IProvideShapedRecipe)
            RecipeUtils.addShapedRecipe((IProvideShapedRecipe) o);
        if (o instanceof IProvideShapelessRecipe)
            RecipeUtils.addShapelessRecipe((IProvideShapelessRecipe) o);
        if (o instanceof ISmellable)
            RecipeUtils.addSmellableRecipe((ISmellable) o);
        if (o instanceof IFuelHandler) {
            GameRegistry.registerFuelHandler((IFuelHandler) o);
        }
    }
}
