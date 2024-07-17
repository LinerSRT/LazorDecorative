package ru.liner.decorative;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class Decorative {
    public static int STAINED_GLASS_PANE_RENDER_ID;
    public static int DOUBLE_PLANT_RENDERER;
    public static int CUTOUT_BLOCK_RENDERER;
    public static int BED_BLOCK_RENDERER;
    public static final String[] colorNames = new String[]{
            "Черный",
            "Красный",
            "Зеленый",
            "Коричневый",
            "Синий",
            "Фиолетовый",
            "Бирюзовый",
            "Светло-серый",
            "Серый",
            "Розовый",
            "Лаймовый",
            "Желтый",
            "Голубой",
            "Пурпурный",
            "Оранжевый",
            "Белый",
            "Черная",
            "Красная",
            "Зеленая",
            "Коричневая",
            "Синяя",
            "Фиолетовая",
            "Бирюзовая",
            "Светло-серая",
            "Серая",
            "Розовая",
            "Лаймовая",
            "Желтая",
            "Голубая",
            "Пурпурная",
            "Оранжевая",
            "Белая",
            "Черное",
            "Красное",
            "Зеленое",
            "Коричневое",
            "Синее",
            "Фиолетовое",
            "Бирюзовое",
            "Светло-серое",
            "Серое",
            "Розовое",
            "Лаймовое",
            "Желтое",
            "Голубое",
            "Пурпурное",
            "Оранжевое",
            "Белое"
    };

    public static void init() {
        STAINED_GLASS_PANE_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        DOUBLE_PLANT_RENDERER = RenderingRegistry.getNextAvailableRenderId();
        CUTOUT_BLOCK_RENDERER = RenderingRegistry.getNextAvailableRenderId();
        BED_BLOCK_RENDERER = RenderingRegistry.getNextAvailableRenderId();
    }
}
