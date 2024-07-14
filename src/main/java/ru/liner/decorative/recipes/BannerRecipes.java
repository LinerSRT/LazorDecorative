package ru.liner.decorative.recipes;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import ru.liner.decorative.blocks.Blocks;
import ru.liner.decorative.tile.TileEntityBanner;
import ru.liner.decorative.utils.Colored;

public class BannerRecipes {
    public void addRecipes() {
        for (int i = 0; i < ItemDye.dyeColors.length; i++) {
            GameRegistry.addRecipe(
                    new ItemStack(Blocks.banner.blockID, 1, i),
                    "###",
                    "###",
                    " I ",
                    '#', new ItemStack(Block.cloth, 1, Colored.normalColorOrderFor(i)),
                    'I', Item.stick
            );
            for (int j = 0; j < ItemDye.dyeColors.length; j++) {
                if (i != j) {
                    GameRegistry.addShapelessRecipe(
                            new ItemStack(Blocks.banner, 1, j),
                            new ItemStack(Blocks.banner, 1, i),
                            new ItemStack(Item.dyePowder, 1, j)
                    );
                }
            }
        }
        GameRegistry.addRecipe(new BannerRemoveLastPatterRecipe());
        GameRegistry.addRecipe(new BannerRecipe());
    }

    public static class BannerRemoveLastPatterRecipe implements IRecipe {

        @Override
        public boolean matches(InventoryCrafting inventoryCrafting, World world) {
            boolean foundWaterBucket = false;
            boolean foundBanner = false;
            boolean foundOtherItem = false;
            for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
                ItemStack stack = inventoryCrafting.getStackInSlot(i);
                if (isWatterBucketStack(stack) && !foundWaterBucket)
                    foundWaterBucket = true;
                if (isBannerStack(stack) && !foundBanner)
                    foundBanner = true;
                if(stack != null) {
                    foundOtherItem = !isWatterBucketStack(stack) && !isBannerStack(stack);
                    if (foundOtherItem)
                        break;
                }
            }
            return foundBanner && foundWaterBucket && !foundOtherItem;
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
            ItemStack bannerStack = getBannerStack(inventoryCrafting);
            if (bannerStack != null) {
                removeLastBannerPattern(bannerStack);
                return bannerStack;
            }
            return null;
        }

        @Override
        public int getRecipeSize() {
            return 2;
        }

        @Override
        public ItemStack getRecipeOutput() {
            return null;
        }
    }

    public static class BannerPatternRecipe implements IRecipe {
        @Override
        public boolean matches(InventoryCrafting inventoryCrafting, World world) {
            boolean canApplyPattern;
            boolean findPowder = false;
            boolean findBanner = false;
            int bannerCount = 0;
            int powderCount = 0;
            for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
                ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
                if (isBannerStack(stackInSlot)) {
                    int patterns = getPatternCount(stackInSlot);
                    if (patterns >= 6)
                        return false;
                    findBanner = true;
                    bannerCount++;
                }
                if (isPowderStack(stackInSlot)) {
                    findPowder = true;
                    powderCount++;
                }
            }
            canApplyPattern = findPowder && findBanner && powderCount == 1 && bannerCount == 1;
            return canApplyPattern && getAssociatedType(inventoryCrafting) != null;
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
            TileEntityBanner.PatternType pattern = getAssociatedType(inventoryCrafting);
            ItemStack bannerStack = getBannerStack(inventoryCrafting);
            ItemStack powderStack = getPowderStack(inventoryCrafting);
            if (bannerStack != null && powderStack != null && pattern != null) {
                NBTTagCompound bannerData = bannerStack.hasTagCompound() ? bannerStack.getTagCompound() : new NBTTagCompound();
                NBTTagList patternData = bannerData.hasKey("patterns") ? bannerData.getTagList("patterns") : new NBTTagList();
                if (!bannerData.hasKey("patterns"))
                    bannerData.setTag("patterns", patternData);
                patternData.appendTag(new NBTTagString("name", String.format("%s_%s", pattern.getPatternID(), powderStack.getItemDamage())));
                bannerStack.setTagCompound(bannerData);
                return bannerStack;
            }
            return null;
        }

        @Override
        public int getRecipeSize() {
            return 3;
        }

        @Override
        public ItemStack getRecipeOutput() {
            return null;
        }

    }


    public static class BannerRecipe implements IRecipe {
        @Override
        public boolean matches(InventoryCrafting craftingInventory, World world) {
            boolean bannerFound = false;
            for (int i = 0; i < craftingInventory.getSizeInventory(); i++) {
                ItemStack currentItem = craftingInventory.getStackInSlot(i);
                if (currentItem != null && currentItem.itemID == Blocks.banner.blockID) {
                    if (bannerFound || getPatternCount(currentItem) >= 6) {
                        return false;
                    }
                    bannerFound = true;
                }
            }
            return bannerFound && getPattern(craftingInventory) != null;
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting craftingInventory) {
            ItemStack bannerItem = null;
            for (int i = 0; i < craftingInventory.getSizeInventory(); i++) {
                ItemStack currentItem = craftingInventory.getStackInSlot(i);
                if (currentItem != null && currentItem.itemID == Blocks.banner.blockID) {
                    bannerItem = currentItem.copy();
                    bannerItem.stackSize = 1;
                    break;
                }
            }

            TileEntityBanner.PatternType pattern = getPattern(craftingInventory);
            if (pattern != null) {
                int dyeColor = -1;
                for (int i = 0; i < craftingInventory.getSizeInventory(); i++) {
                    ItemStack currentItem = craftingInventory.getStackInSlot(i);
                    if (currentItem != null && currentItem.itemID == Item.dyePowder.itemID) {
                        dyeColor = currentItem.getItemDamage();
                        break;
                    }
                }
                if(bannerItem != null) {
                    bannerItem.setTagCompound(writeType(bannerItem, pattern, dyeColor));
                }
            }

            return bannerItem;
        }

        @Override
        public int getRecipeSize() {
            return 10;
        }

        @Override
        public ItemStack getRecipeOutput() {
            return null;
        }


        private TileEntityBanner.PatternType getPattern(InventoryCrafting craftingInventory) {
            for (TileEntityBanner.PatternType pattern : TileEntityBanner.PatternType.values()) {
                if (pattern.hasValidCrafting()) {
                    boolean isValid = true;
                    if (pattern.hasCraftingStack()) {
                        boolean patternItemFound = false;
                        boolean dyeItemFound = false;
                        for (int i = 0; i < craftingInventory.getSizeInventory(); i++) {
                            ItemStack currentItem = craftingInventory.getStackInSlot(i);
                            if (currentItem != null && currentItem.itemID != Blocks.banner.blockID) {
                                if (currentItem.itemID == Item.dyePowder.itemID) {
                                    if (dyeItemFound) {
                                        isValid = false;
                                        break;
                                    }
                                    dyeItemFound = true;
                                } else {
                                    if (patternItemFound || !currentItem.isItemEqual(pattern.getCraftingStack())) {
                                        isValid = false;
                                        break;
                                    }
                                    patternItemFound = true;
                                }
                            }
                        }
                        isValid = isValid && patternItemFound;
                    } else if (craftingInventory.getSizeInventory() == pattern.getCraftingLayers().length * pattern.getCraftingLayers()[0].length()) {
                        int previousDyeColor = -1;
                        for (int i = 0; i < craftingInventory.getSizeInventory(); i++) {
                            int row = i / 3;
                            int column = i % 3;
                            ItemStack currentItem = craftingInventory.getStackInSlot(i);
                            if (currentItem == null || currentItem.itemID == Blocks.banner.blockID) {
                                if (pattern.getCraftingLayers()[row].charAt(column) != ' ') {
                                    isValid = false;
                                    break;
                                }
                            } else {
                                if (currentItem.itemID != Item.dyePowder.itemID) {
                                    isValid = false;
                                    break;
                                }
                                if (previousDyeColor != -1 && previousDyeColor != currentItem.getItemDamage()) {
                                    isValid = false;
                                    break;
                                }
                                if (pattern.getCraftingLayers()[row].charAt(column) == ' ') {
                                    isValid = false;
                                    break;
                                }
                                previousDyeColor = currentItem.getItemDamage();
                            }
                        }
                    } else {
                        isValid = false;
                    }

                    if (isValid) {
                        return pattern;
                    }
                }
            }
            return null;
        }
    }

    private static TileEntityBanner.PatternType getAssociatedType(InventoryCrafting inventoryCrafting) {
        for (TileEntityBanner.PatternType type : TileEntityBanner.PatternType.values()) {
            if (type.hasValidCrafting()) {
                if (type.hasCraftingStack()) {
                    boolean foundBannerStack = false;
                    boolean foundRequiresStack = false;
                    for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
                        ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
                        if (isBannerStack(stackInSlot)) {
                            foundBannerStack = true;
                        }
                        if (type.isMatchingCraftingStack(stackInSlot)) {
                            foundRequiresStack = true;
                            break;
                        }
                    }
                    if (foundBannerStack && foundRequiresStack)
                        return type;
                }
            }
        }
        return null;
    }

    private static ItemStack getBannerStack(InventoryCrafting inventoryCrafting) {
        for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
            ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
            if (isBannerStack(stackInSlot))
                return stackInSlot.copy();
        }
        return null;
    }

    private static ItemStack getPowderStack(InventoryCrafting inventoryCrafting) {
        for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
            ItemStack stackInSlot = inventoryCrafting.getStackInSlot(i);
            if (isPowderStack(stackInSlot))
                return stackInSlot.copy();
        }
        return null;
    }


    public static boolean isBannerStack(ItemStack stack) {
        return stack != null && stack.itemID == Blocks.banner.blockID;
    }

    public static boolean isPowderStack(ItemStack stack) {
        return stack != null && stack.itemID == Item.dyePowder.itemID;
    }

    public static boolean isWatterBucketStack(ItemStack stack) {
        return stack != null && stack.itemID == Item.bucketWater.itemID;
    }


    public static NBTTagCompound writeType(ItemStack itemStack, TileEntityBanner.PatternType pattern, int color){
        NBTTagCompound nbtTagCompound = itemStack.hasTagCompound() ? itemStack.getTagCompound() : new NBTTagCompound();
        writeType(nbtTagCompound, pattern, color);
        return nbtTagCompound;
    }
    public static void writeType(NBTTagCompound tagCompound, TileEntityBanner.PatternType pattern, int color){
        NBTTagList patternData = tagCompound.hasKey("patterns") ? tagCompound.getTagList("patterns") : new NBTTagList();
        if (!tagCompound.hasKey("patterns"))
            tagCompound.setTag("patterns", patternData);
        patternData.appendTag(new NBTTagString("name", String.format("%s_%s", pattern.getPatternID(), color)));
    }

    public static void removeLastBannerPattern(ItemStack stack) {
        if (!stack.hasTagCompound())
            return;
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (!tagCompound.hasKey("patterns"))
            return;
        NBTTagList patternList = tagCompound.getTagList("patterns");
        if (patternList.tagCount() <= 0)
            return;
        patternList.removeTag(patternList.tagCount() - 1);
        if (patternList.tagCount() <= 0) {
            tagCompound.removeTag("patterns");
            if (tagCompound.hasNoTags())
                stack.setTagCompound(null);
        }
    }

    public static int getPatternCount(ItemStack stack) {
        if (!stack.hasTagCompound())
            return 0;
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound.hasKey("patterns"))
            return tagCompound.getTagList("patterns").tagCount();
        return 0;
    }

    public static void setPatterns(NBTTagCompound tagCompound, NBTTagList tagList) {
        if (tagList != null)
            tagCompound.setTag("patterns", tagList);
    }
}
