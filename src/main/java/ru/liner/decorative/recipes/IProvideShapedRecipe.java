package ru.liner.decorative.recipes;

import net.minecraft.item.ItemStack;
import ru.liner.decorative.utils.Pair;

import java.util.List;

public interface IProvideShapedRecipe {
    ItemStack getCraftResult();
    String[] getCraftPattern();
    List<Pair<Character, ?>> getCraftDescription();
}
