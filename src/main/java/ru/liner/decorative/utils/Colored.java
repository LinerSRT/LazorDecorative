package ru.liner.decorative.utils;

public class Colored {
    public static int normalColorOrderFor(int index){
        return 15 - (index & 15);
    }
}
