package ru.liner.decorative;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
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
import ru.liner.decorative.render.Renderers;
import ru.liner.decorative.world.Generator;

import java.util.logging.Logger;

@Mod(modid = DecorativeMod.MOD_ID, version = "1.0", name = "Lazor Decorative")
@NetworkMod
public class DecorativeMod {
    public static final String MOD_ID = "lazor_decorative";
    @Mod.Instance(MOD_ID)
    public static DecorativeMod INSTANCE;

    @SidedProxy(
            clientSide = "ru.liner.decorative.ClientProxy",
            serverSide = "ru.liner.decorative.ServerProxy"
    )
    public static CommonProxy proxy;

    public static Logger logger = Logger.getLogger(DecorativeMod.class.getSimpleName());

    public DecorativeMod() {
        logger.setParent(FMLLog.getLogger());
    }

    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @Mod.Init
    public void init(final FMLInitializationEvent e) {
        proxy.init(e);
    }
}
