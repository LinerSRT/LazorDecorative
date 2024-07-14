package ru.liner.decorative.utils;

import java.util.HashMap;

public class MaterialFormatter {
    private static final HashMap<String, String> endings = new HashMap<>();

    static {
        endings.put("acacia", "из акации");
        endings.put("big_oak", "из темного дуба");
        endings.put("granite", "из гранита");
        endings.put("diorite", "из диорита");
        endings.put("andesite", "из андезита");
        endings.put("granite_smooth", "из гладкого гранита");
        endings.put("diorite_smooth", "из гладкого диорита");
        endings.put("andesite_smooth", "из гладкого андезита");
        endings.put("prismarine", "из призмарина");
        endings.put("prismarine_dark", "из темного призмарина");
        endings.put("prismarine_bricks", "из призмариновых кирпичей");
    }

    public static String formatMaterial(Type type, String material){
        return String.format("%s %s", type.toString(), endings.containsKey(material) ? endings.get(material) : String.format("из %s", material));
    }

    public enum Type{
        STAIRS("Ступеньки"),
        SLAB("Полублок"),
        FENCE("Забор"),
        DOOR("Дверь"),
        LADDER("Лестница"),
        WALL("Стена");
        private final String type;
        Type(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }
}
