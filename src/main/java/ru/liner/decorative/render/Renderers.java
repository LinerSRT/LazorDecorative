package ru.liner.decorative.render;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class Renderers {
    public static void init(){
        RenderingRegistry.registerBlockHandler(new StainedGlassPaneRenderer());
        RenderingRegistry.registerBlockHandler(new DoublePlantRenderer());
    }
}
