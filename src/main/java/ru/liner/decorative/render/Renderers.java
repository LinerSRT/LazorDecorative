package ru.liner.decorative.render;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import ru.liner.decorative.blocks.Blocks;
import ru.liner.decorative.render.block.CutoutBlockRenderer;
import ru.liner.decorative.render.block.DoublePlantRenderer;
import ru.liner.decorative.render.block.StainedGlassPaneRenderer;
import ru.liner.decorative.render.item.BannerItemRenderer;
import ru.liner.decorative.render.tileentity.TileEntityBannerRenderer;
import ru.liner.decorative.tile.TileEntityBanner;

public class Renderers {
    public static void init(){
        RenderingRegistry.registerBlockHandler(new StainedGlassPaneRenderer());
        RenderingRegistry.registerBlockHandler(new DoublePlantRenderer());
        RenderingRegistry.registerBlockHandler(new CutoutBlockRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBanner.class, new TileEntityBannerRenderer());
        MinecraftForgeClient.registerItemRenderer(Blocks.banner.blockID, new BannerItemRenderer());
    }
}
