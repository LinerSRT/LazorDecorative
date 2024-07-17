package ru.liner.decorative.register;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.liner.decorative.tile.TileEntityBanner;
import ru.liner.decorative.tile.TileEntityBed;

public class TileEntities {
    public static void init(){
        GameRegistry.registerTileEntity(TileEntityBanner.class, TileEntityBanner.class.getName());
        GameRegistry.registerTileEntity(TileEntityBed.class, TileEntityBed.class.getName());
    }
}
