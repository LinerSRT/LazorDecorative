package ru.liner.decorative.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import ru.liner.decorative.utils.Pair;
import ru.liner.decorative.utils.Reflection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeUtils {
    public static ShapedRecipes addShapedRecipe(IProvideShapedRecipe provider) {
        StringBuilder pattern = new StringBuilder();
        int width = 0;
        int height;
        String[] patternArray = provider.getCraftPattern();
        height = patternArray.length;
        for (String row : patternArray) {
            pattern.append(row);
            width = row.length();
        }
        Map<Character, ItemStack> itemMapping = new HashMap<>();
        for (Pair<Character, ?> item : provider.getCraftDescription()) {
            Character key = item.key;
            Object value = item.value;
            ItemStack itemStack = null;
            if (value instanceof Item) {
                itemStack = new ItemStack((Item) value);
            } else if (value instanceof Block) {
                itemStack = new ItemStack((Block) value, 1, 32767);
            } else if (value instanceof ItemStack) {
                itemStack = (ItemStack) value;
            }
            itemMapping.put(key, itemStack);
        }
        ItemStack[] itemStacks = new ItemStack[width * height];
        for (int i = 0; i < width * height; i++) {
            char character = pattern.charAt(i);
            itemStacks[i] = itemMapping.containsKey(character) ? itemMapping.get(character).copy() : null;
        }
        ShapedRecipes shapedRecipe = new ShapedRecipes(width, height, itemStacks, provider.getCraftResult());
        Reflection.getSafety(CraftingManager.getInstance(), "recipes", new ArrayList<>()).add(shapedRecipe);
        return shapedRecipe;
    }
}
