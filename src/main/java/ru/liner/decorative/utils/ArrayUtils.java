package ru.liner.decorative.utils;

import java.lang.reflect.Array;

public class ArrayUtils {
    public static <T> T[] concatenate(T[] one, T[] another) {
        int firstLength = one.length;
        int secondLength = another.length;
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(one.getClass().getComponentType(), firstLength + secondLength);
        System.arraycopy(one, 0, result, 0, firstLength);
        System.arraycopy(another, 0, result, firstLength, secondLength);
        return result;
    }
    public static Object[] concatenateRaw(Object[] one, Object[] another) {
        int firstLength = one.length;
        int secondLength = another.length;
        Object[] result = new Object[firstLength + secondLength];
        System.arraycopy(one, 0, result, 0, firstLength);
        System.arraycopy(another, 0, result, firstLength, secondLength);
        return result;
    }
}
