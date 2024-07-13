package ru.liner.decorative.render;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import ru.liner.decorative.blocks.Blocks;
import ru.liner.decorative.tile.TileEntityBanner;

public class Renderers {
    public static void init(){
        RenderingRegistry.registerBlockHandler(new StainedGlassPaneRenderer());
        RenderingRegistry.registerBlockHandler(new DoublePlantRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBanner.class, new BannerBlockRenderer());
        MinecraftForgeClient.registerItemRenderer(Blocks.banner.blockID, new BannerItemRenderer());
    }
}
