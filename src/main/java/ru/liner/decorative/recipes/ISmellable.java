package ru.liner.decorative.recipes;

import net.minecraft.item.ItemStack;

public interface ISmellable {
    float getXp();
    ItemStack getSmeltingResult();
    int getItemId();
}
