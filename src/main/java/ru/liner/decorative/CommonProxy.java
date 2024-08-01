package ru.liner.decorative;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import ru.liner.decorative.blocks.IUseBonemail;
import ru.liner.decorative.enity.Entities;
import ru.liner.decorative.recipes.BannerRecipes;
import ru.liner.decorative.register.BlockRegister;
import ru.liner.decorative.register.DamageSources;
import ru.liner.decorative.register.Items;
import ru.liner.decorative.register.TileEntities;
import ru.liner.decorative.world.Generator;

public class CommonProxy {
    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent e){
        DamageSources.init();
        TileEntities.init();
        Decorative.init();
        BlockRegister.init();
        Items.init();
        Entities.init();
        BannerRecipes.addRecipes();
        Generator.init();
    }

    @Mod.Init
    public void init(final FMLInitializationEvent e){
        MinecraftForge.EVENT_BUS.register(this);
    }

    @ForgeSubscribe
    public void onApplyBoneMail(BonemealEvent event) {
        int blockId = event.world.getBlockId(event.X, event.Y, event.Z);
        Block block = Block.blocksList[blockId];
        if (block instanceof IUseBonemail) {
            event.setResult(Event.Result.ALLOW);
            ((IUseBonemail) block).applyBonemail(event.world, event.X, event.Y, event.Z);
        }
    }
}
