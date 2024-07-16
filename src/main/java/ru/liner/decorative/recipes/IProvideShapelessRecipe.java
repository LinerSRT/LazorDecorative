package ru.liner.decorative.recipes;

import net.minecraft.item.ItemStack;

public interface IProvideShapelessRecipe {
    ItemStack getShapelessCraftResult();
    Object[] getRecipeIngredients();
}
