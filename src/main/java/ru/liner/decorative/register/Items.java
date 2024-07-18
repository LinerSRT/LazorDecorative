package ru.liner.decorative.register;

import ru.liner.decorative.items.ItemChorusFruit;
import ru.liner.decorative.items.ItemChorusFruitPopped;

public class Items {
    public static ItemChorusFruit chorusFruit = new ItemChorusFruit(1058);
    public static ItemChorusFruitPopped chorusFruitPopped = new ItemChorusFruitPopped(1059);

    public static void init() {
        Registry.getInstance()
                .add(chorusFruit)
                .add(chorusFruitPopped)
                .registerItems();
    }
}
