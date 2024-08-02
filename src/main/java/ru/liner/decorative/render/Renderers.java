package ru.liner.decorative.render;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.MinecraftForgeClient;
import ru.liner.decorative.BuildConfig;
import ru.liner.decorative.Decorative;
import ru.liner.decorative.register.Blocks;
import ru.liner.decorative.render.block.CutoutBlockRenderer;
import ru.liner.decorative.render.block.DoublePlantRenderer;
import ru.liner.decorative.render.block.RedstoneLogicBlockRenderer;
import ru.liner.decorative.render.block.StainedGlassPaneRenderer;
import ru.liner.decorative.render.item.BannerItemRenderer;
import ru.liner.decorative.render.tileentity.TileEntityBannerRenderer;
import ru.liner.decorative.render.tileentity.TileEntityBedRenderer;
import ru.liner.decorative.tile.TileEntityBanner;
import ru.liner.decorative.tile.TileEntityBed;

public class Renderers {
    public static void init(){
        RenderingRegistry.registerBlockHandler(new StainedGlassPaneRenderer());
        RenderingRegistry.registerBlockHandler(new DoublePlantRenderer());
        RenderingRegistry.registerBlockHandler(new CutoutBlockRenderer());
        RenderingRegistry.registerBlockHandler(new RedstoneLogicBlockRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBanner.class, new TileEntityBannerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBed.class, new TileEntityBedRenderer());
        MinecraftForgeClient.registerItemRenderer(Blocks.banner.blockID, new BannerItemRenderer());
        //MinecraftForgeClient.registerItemRenderer(Blocks.bed.blockID, new BedItemRenderer());
    }
}
