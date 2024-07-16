package ru.liner.decorative.register;

import net.minecraft.item.Item;

public interface IItemProvider<I extends Item> {
    Class<I> getItemClass();
}
