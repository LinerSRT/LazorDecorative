package ru.liner.decorative.recipes;

import net.minecraft.item.ItemStack;
import ru.liner.decorative.utils.Pair;

import java.util.List;

public interface IProvideShapelessRecipe {
    ItemStack getCraftResult();
    Object[] getRecipeIngredients();
}
